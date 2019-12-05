package FXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;

public class Main extends Application {
    @Test
    public void testMethod(){
        Account newAccount = new Account(1122, "George", 1000, 1.5);
        newAccount.deposit(30, "PC Checkup");
        newAccount.deposit(40, "Phone Battery Replacement");
        newAccount.deposit(50, "PC Virus Removal");
        newAccount.withdraw(5, "16oz RedBull");
        newAccount.withdraw(4, "2 McDonald's Cheeseburgers");
        newAccount.withdraw(2, "Starbucks Black Coffee");
        System.out.println(newAccount.toString());
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root=FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root, 1100, 720);
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
