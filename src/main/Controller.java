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

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String CONNECTION = "jdbc:mysql://localhost/finance_management";

    //=================================================================================================================
    // Class Attributes
    //=================================================================================================================
    // Our new account object
    Account new_account = new Account(1, "", 0, 1.5);

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
        String username = login_username_field.getText();
        String query = "SELECT username, passwd FROM accounts WHERE username LIKE '"+username+"';";
        try{
            Connection con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println(st);
            System.out.println(rs);
            if(rs.next()){
                String password = rs.getString("passwd").toString();
                if(password.equals(login_password_field.getText())){
                    LoginSuccess();
                    Stage primaryStage  = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                }else{
                    LoginFailure();
                }
            }
        } catch (SQLException | IOException exception) {
            System.out.println(exception.toString());
        }
    }

    public void LoginSuccess(){
        System.out.println("Login success!");
    }
    public void LoginFailure(){
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
    public void handleSubmitTransactionAction(){

        try {

            //by default data stored in a text field on the gui is stored as a string, so we're converting to a double
            double amount = Double.parseDouble(new_transaction_amount_field.getText());

            if (new_transaction_type_choice.getSelectionModel().getSelectedItem().toString().equals("Deposit")) {

                Transaction transaction = new_account.deposit(amount, new_transaction_description_field.getText());
                ObservableList<TransactionView> viewable_data = tableView.getItems();
                viewable_data.add(new TransactionView(String.valueOf(transaction.getType()), String.valueOf(transaction.getDate()),
                        String.valueOf(transaction.getAmount()), String.valueOf(transaction.getDescription()),
                        String.valueOf(transaction.getBalance())));

            } else if (new_transaction_type_choice.getSelectionModel().getSelectedItem().toString().equals("Withdraw")) {

                // checks to make sure the user has enough money in their account.
                if (new_account.getBalance() >= amount) {
                    Transaction transaction = new_account.withdraw(amount, new_transaction_description_field.getText());
                    ObservableList<TransactionView> data = tableView.getItems();
                    data.add(new TransactionView(String.valueOf(transaction.getType()), String.valueOf(transaction.getDate()),
                            String.valueOf(transaction.getAmount()), String.valueOf(transaction.getDescription()),
                            String.valueOf(transaction.getBalance())));
                } else {

                    // if they don't display this error message:
                    // calls my private method alert passing it two arguments
                    alert(Alert.AlertType.ERROR, "There isn't enough money in your account to complete this transaction!");
                }
            }
        } catch (NullPointerException exception) {
            alert(Alert.AlertType.ERROR, "Please enter a valid number in the amount box, make sure something is in description," +
                    "and make sure you have selected either deposit or withdraw for this transaction. Thank you!");
        } catch (NumberFormatException exception) {
            alert(Alert.AlertType.ERROR, "Please make sure you entered a valid number in the amount field!");
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

    private void alert(Alert.AlertType alertType, String alertText) {
        Alert errorAlert = new Alert(alertType);
        errorAlert.setContentText(alertText);
        errorAlert.showAndWait();
    }
}