import javafx.application.Platform;
import FXML.Account;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import FXML.Transaction;

public class guiController {

    // Our new account object
    Account newAccount = new Account(1, "", 0, 1.5);

    @FXML
    public MenuItem fileCloseItem;
    @FXML
    public TextField new_transaction_amount_field;
    @FXML
    public TextArea new_transaction_description_field;
    @FXML
    public ChoiceBox new_transaction_type_choice;
    @FXML
    public TableView<TransactionView> tableView;

    @FXML
    public void handleFileCloseAction() {
        Platform.exit();
    }

    @FXML
    public void handleSubmitTransactionAction(){

        try {

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
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setContentText("There isn't enough money in your account to complete this transaction!");
                    errorAlert.showAndWait();
                }
            }
        }catch(NullPointerException exception){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Please enter a valid number in the amount box, make sure something is in description," +
                    "and make sure you have selected either deposit or withdraw for this transaction. Thank you!");
            errorAlert.showAndWait();
        }catch(NumberFormatException exception){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Please make sure you entered a valid number in the amount field!");
            errorAlert.showAndWait();
        }
    }
}