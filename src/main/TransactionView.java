package main;

import javafx.beans.property.SimpleStringProperty;

public class TransactionView {

    private SimpleStringProperty type;
    private SimpleStringProperty date;
    private SimpleStringProperty amount;
    private SimpleStringProperty description;
    private SimpleStringProperty balance;

    public TransactionView(String type, String  date, String  amount,
                           String  description, String  balance) {
        this.type = new SimpleStringProperty(type);
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleStringProperty(amount);
        this.description = new SimpleStringProperty(description);
        this.balance = new SimpleStringProperty(balance);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getBalance() {
        return balance.get();
    }

    public SimpleStringProperty balanceProperty() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance.set(balance);
    }
}
