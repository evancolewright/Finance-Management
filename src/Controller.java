/*
    Johnathan R. Burgess
    December 5th, 2019, 3:28pm ET
 */
import javafx.application.Platform;
import FXML.Account;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import FXML.Transaction;

/*
    This is our controller.
 */

public class Controller {

    //=================================================================================================================
    // Class Attributes
    //=================================================================================================================
    // Our new account object
    Account newAccount = new Account(1, "", 0, 1.5);

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

    //=================================================================================================================


    //=================================================================================================================
    // Class Methods
    //=================================================================================================================
    @FXML
    public void handleFileCloseAction() {
        Platform.exit();
    }

    @FXML
    public void handleSubmitTransactionAction() {

        try {

            //by default data stored in a text field on the gui is stored as a string, so we're converting to a double
            double amount = Double.parseDouble(new_transaction_amount_field.getText());

            if (new_transaction_type_choice.getSelectionModel().getSelectedItem().toString().equals("Deposit")) {
                Transaction transaction = newAccount.deposit(amount, new_transaction_description_field.getText());
                ObservableList<TransactionView> data = tableView.getItems();
                data.add(new TransactionView(String.valueOf(transaction.getType()), String.valueOf(transaction.getDate()),
                        String.valueOf(transaction.getAmount()), String.valueOf(transaction.getDescription()),
                        String.valueOf(transaction.getBalance())));
            } else if (new_transaction_type_choice.getSelectionModel().getSelectedItem().toString().equals("Withdraw")) {

                // checks to make sure the user has enough money in their account.
                if (newAccount.getBalance() >= amount) {
                    Transaction transaction = newAccount.withdraw(amount, new_transaction_description_field.getText());
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
    @FXML
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