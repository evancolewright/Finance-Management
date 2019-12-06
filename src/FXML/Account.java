package FXML;

import java.util.ArrayList;
import java.util.Date;

public class Account {

    private int id;
    private String name;
    private double balance;
    private double annualInterestRate;
    private Date dateCreated;
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Account(){
        this.id = 0;
        this.balance = 0;
        this.annualInterestRate = 0;
        this.dateCreated = new Date();
    }

    public Account(int id, double balance){
        this.id = id;
        this.balance = balance;
        this.annualInterestRate = 0;
        this.dateCreated = new Date();
    }

    public Account(int id, String name, double balance){
        this(id, balance);
        this.name = name;
        this.annualInterestRate = 0;
        this.dateCreated = new Date();
    }

    public Account(int id, String name, double balance, double annualInterestRate){
        this(id, name, balance);
        this.annualInterestRate = annualInterestRate;
        this.dateCreated = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    Date getDateCreated() {
        return dateCreated;
    }

    private double getMonthlyInterestRate(){
        return ((this.annualInterestRate/100)/12);
    }

    public double getMonthlyInterest(){
        return this.balance*this.getMonthlyInterestRate();
    }

    public Transaction withdraw(double amount, String description){
        this.balance -= amount;
        Transaction transaction = new Transaction('W', amount, this.balance, description);
        transactions.add(transaction);
        return transaction;
    }

    public Transaction deposit(double amount, String description){
        this.balance += amount;
        Transaction transaction = new Transaction('D', amount, this.balance, description);
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public String toString() {
        String output = "=================================Account===============================\n" +
                "Account Holder..............: " + name + "\n" +
                "Balance.....................: $" + balance + "\n" +
                "Annual Interest Rate........: " + annualInterestRate + "%\n" +
                "==============================Transactions============================\n";
        for(Transaction transaction : transactions){
            output += "-------------------------------------------------------------------------\n";
            output += transaction.getType() + ".\t"+"$"+transaction.getAmount()+"\t"+transaction.getDescription()+"\n";
            output += "Remaining Account Balance: $" + transaction.getBalance() + "\tDate: "+transaction.getDate()+"\n";
            output += "-------------------------------------------------------------------------\n";
        }

        return output;
    }
}
