import FXML.Account;
import FXML.Transaction;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/*
    This is our controller.
 */

public class Controller {

    //=================================================================================================================
    // Class Attributes
    //=================================================================================================================
    // Our new account object
    static Account new_account = new Account(1, "", 0, 1.5);
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
                    LoginSuccess(rs.getString("username"));
                    new_account.setId(rs.getInt("accountID"));
                    this.loadBalance(new_account.getId());
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
        Connection connection = null;
        PreparedStatement statement = null;
        int id = new_account.getId();
        try {
            //by default data stored in a text field on the gui is stored as a string, so we're converting to a double
            double amount = Double.parseDouble(new_transaction_amount_field.getText());
            ObservableList<TransactionView> viewable_data = tableView.getItems();

            connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            String query = "INSERT INTO transactions (accountID, date, type, amount, description)" +
                    " values (?, ?, ?, ?, ?)";

            // PreparedStatement is going to save us creating a whole new statement dependent on transaction type.
            // Set our initial query variables that we know up to this point.
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
            statement.setDouble(4, amount);
            statement.setString(5, new_transaction_description_field.getText());


            if (new_transaction_type_choice.getSelectionModel().getSelectedItem().toString().equals("Deposit")) {
                Transaction transaction = new_account.deposit(amount, new_transaction_description_field.getText());
                viewable_data.add(new TransactionView(String.valueOf(transaction.getType()), String.valueOf(transaction.getDate()),
                        String.valueOf(transaction.getAmount()), String.valueOf(transaction.getDescription()),
                        String.valueOf(transaction.getBalance())));
                statement.setString(3, String.valueOf(transaction.getType()));
            }
            if (new_transaction_type_choice.getSelectionModel().getSelectedItem().toString().equals("Withdraw")) {
                // checks to make sure the user has enough money in their account.
                if (new_account.getBalance() >= amount) {
                    Transaction transaction = new_account.withdraw(amount, new_transaction_description_field.getText());
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
            // execute our statement
            statement.execute();
            this.updateBalance(new_account.getId(), new_account.getBalance());

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
            if (connection != null) {
                connection.close();
            }
        }
    }
/*
    @main.FXML
    public void handleAlterDataAction(){
        try {

            if (alterDataChoice.getSelectionModel().getSelectedItem().toString().equals("Edit Description")) {
                if(editDescriptionArea.getText().equals("")){
                    System.out.println("New Description!");
                }else{
                    alert(Alert.AlertType.ERROR,
                            "Please enter some data in the text box for the new description!");
                }
            } else if (alterDataChoice.getSelectionModel().getSelectedItem().toString().equals("Delete Record")) {
                System.out.println("Delete Record");
            }
        }

        catch(NullPointerException exception){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Make sure to select an option!");
        }
    }
    //================================================================================================================
*/

    private void loadBalance(int accountID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = connection.prepareStatement("SELECT balance FROM accounts WHERE accountID = " + new_account.getId());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            new_account.setBalance(resultSet.getDouble("balance"));
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

    private void alert(Alert.AlertType alertType, String alertText) {
        Alert errorAlert = new Alert(alertType);
        errorAlert.setContentText(alertText);
        errorAlert.showAndWait();
    }
}