import FXML.Account;
import FXML.Transaction;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class Controller {

    // Our new account object
    static Account current_account = null;
    //=================================================================================================================
    // Class Attributes
    //=================================================================================================================
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String CONNECTION = "jdbc:mysql://localhost/finance_management";
    @FXML
    private TextField new_transaction_amount_field;
    @FXML
    private TextArea new_transaction_description_field;
    @FXML
    private ChoiceBox new_transaction_type_choice;
    @FXML
    private TableView<TransactionView> tableView;

    private ObservableList<TransactionView> viewable_data = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox alterDataChoice;
    @FXML
    private TextArea editDescriptionArea;

    //==============================================================
    // LOGIN FORM WIDGETS
    //==============================================================
    @FXML
    private TextField login_username_field;
    @FXML
    private PasswordField login_password_field;
    @FXML
    private Label login_error_label;
    @FXML
    private Button login_btn;
    //==============================================================

    //=================================================================================================================
    @FXML
    public void handleLoginSubmit() throws SQLException {
        final String username = login_username_field.getText();
        final String query = "SELECT username, passwd, accountID FROM accounts WHERE (username) LIKE (?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String password = rs.getString("passwd").toString();
                if (password.equals(login_password_field.getText())) {
                    Stage primaryStage = new Stage();
                    primaryStage.close();
                    Parent root = FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    int accountID = rs.getInt("accountID");
                    current_account = new Account(accountID, rs.getString("username"), 0, 1.5);
                    this.loadBalance(current_account.getId());
                } else {
                    LoginFailure();
                }
            }
        } catch (SQLException | IOException exception) {
            System.out.println(exception.toString());
        } finally {
            //assure connection & statement closure regardless of exception
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void LoginSuccess(String username) throws IOException, SQLException {
        System.out.println("Login success! Welcome, " + username + "!");
    }

    public void LoginFailure() {
        System.out.println("Login Failure!");
    }


    //=================================================================================================================
    // Class Methods
    //=================================================================================================================
    @FXML
    public void handleFileCloseAction() {
        Platform.exit();
    }

    @FXML
    public void handleSubmitTransactionAction() throws SQLException {
        Connection current_db_connection = null;
        PreparedStatement statement = null;
        int id = current_account.getId();
        try {
            //by default data stored in a text field on the gui is stored as a string, so we're converting to a double
            double amount = Double.parseDouble(new_transaction_amount_field.getText());
            current_db_connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            String query = "INSERT INTO transactions (accountID, date, type, amount, description, balance)" +
                    " values (?, ?, ?, ?, ?, ?)";

            // PreparedStatement is going to save us creating a whole new statement dependent on transaction type.
            // Set our initial query variables that we know up to this point.
            statement = current_db_connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
            statement.setDouble(4, amount);
            statement.setString(5, new_transaction_description_field.getText());


            if (new_transaction_type_choice.getSelectionModel().getSelectedItem().toString().equals("Deposit")) {
                Transaction transaction = current_account.deposit(amount, new_transaction_description_field.getText());
                viewable_data.add(new TransactionView(String.valueOf(transaction.getType()), String.valueOf(transaction.getDate()),
                        String.valueOf(transaction.getAmount()), String.valueOf(transaction.getDescription()),
                        String.valueOf(transaction.getBalance())));
                statement.setString(3, String.valueOf(transaction.getType()));
            }
            if (new_transaction_type_choice.getSelectionModel().getSelectedItem().toString().equals("Withdraw")) {
                // checks to make sure the user has enough money in their account.
                if (current_account.getBalance() >= amount) {
                    Transaction transaction = current_account.withdraw(amount, new_transaction_description_field.getText());
                    viewable_data.add(new TransactionView(String.valueOf(transaction.getType()), String.valueOf(transaction.getDate()),
                            String.valueOf(transaction.getAmount()), String.valueOf(transaction.getDescription()),
                            String.valueOf(transaction.getBalance())));
                    statement.setString(3, String.valueOf(transaction.getType()));

                } else {
                    // if they don't display this error message:
                    // calls my private method alert passing it two arguments
                    alert(Alert.AlertType.ERROR, "There isn't enough money in your account to complete this transaction!");
                }
            }
            statement.setDouble(6,current_account.getBalance());
            // execute our statement
            statement.execute();
            this.updateBalance(current_account.getId(), current_account.getBalance());

        } catch (NullPointerException exception) {
            alert(Alert.AlertType.ERROR, "Please enter a valid number in the amount box, make sure something is in description," +
                    "and make sure you have selected either deposit or withdraw for this transaction. Thank you!");
        } catch (NumberFormatException exception) {
            alert(Alert.AlertType.ERROR, "Please make sure you entered a valid number in the amount field!");
        } catch (SQLException e) {
            //TODO
            System.out.print(e);
            alert(Alert.AlertType.ERROR, "Database Error!");
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (current_db_connection != null) {
                current_db_connection.close();
            }
        }
        tableView.setItems(viewable_data);
    }

    private void loadBalance(int accountID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = connection.prepareStatement("SELECT balance FROM accounts WHERE accountID = " + current_account.getId());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            current_account.setBalance(resultSet.getDouble("balance"));
        } catch (SQLException ex) {
            System.out.print(ex);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    private void updateBalance(int accountID, double balance) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = connection.prepareStatement("UPDATE accounts SET balance = " + balance + " WHERE accountID = " + accountID);
            statement.execute();
        } catch (SQLException e) {
            System.out.print(e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @FXML
    private void populateTableOnLoad() throws SQLException{
        PreparedStatement statement = null;
        Connection current_db_connection = null;
        try{
            current_db_connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = current_db_connection.prepareStatement("SELECT type, date, amount, description, balance FROM transactions" +
                    " WHERE accountID LIKE " + current_account.getId()+" ORDER BY transactionID;");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                this.viewable_data.add(new TransactionView(resultSet.getString(1),
                        String.valueOf(resultSet.getDate(2)),
                        String.valueOf(resultSet.getDouble(3)),
                        resultSet.getString(4),
                        String.valueOf(resultSet.getDouble(5))));
            }
            this.tableView.setItems(this.viewable_data);
        } catch (Exception e) {
            System.out.println(e + " in populateTableOnLoad() method");
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (current_db_connection != null) {
                current_db_connection.close();
            }
        }
    }

    private void alert(Alert.AlertType alertType, String alertText) {
        Alert errorAlert = new Alert(alertType);
        errorAlert.setContentText(alertText);
        errorAlert.showAndWait();
    }
}