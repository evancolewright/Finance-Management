package FXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    @Test
    public void testMethod() {
        Account newAccount = new Account(1122, "George", 1000, 1.5);
        newAccount.deposit(30, "PC Checkup");
        newAccount.deposit(40, "Phone Battery Replacement");
        newAccount.deposit(50, "PC Virus Removal");
        newAccount.withdraw(5, "16oz RedBull");
        newAccount.withdraw(4, "2 McDonald's Cheeseburgers");
        newAccount.withdraw(2, "Starbucks Black Coffee");
        System.out.println(newAccount.toString());
    }

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String CONNECTION = "jdbc:mysql://localhost/finance_management";

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            System.out.println("Connected to Database!");
        } catch (SQLException exception) {
            System.out.println(exception.toString());
        } finally {
            if (con != null) {
                con.close();
            }
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root, 1100, 720);
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
