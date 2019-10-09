import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.*;

public class JavaFXSkel extends Application {

    Label response;

    public static void main(String[] args) {

        System.out.println("Launching JavaFX Application");

        launch(args);
    }

    @Override
    public void init(){
        System.out.println("Inside the init() method");
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Inside the start() method.");

        primaryStage.setTitle("Finance Management 1.0");

        // vertical and horizontal gaps of 10
        FlowPane rootNode = new FlowPane(10,10);
        // centers our root node, making all the child nodes flow from the center of our window
        rootNode.setAlignment(Pos.CENTER);

        // creates a new scene (window of 1300 by 700)
        Scene myScene = new Scene(rootNode, 1300, 700);

        primaryStage.setScene(myScene);

        response = new Label("Push a Button.");

        Button btnAlpha = new Button("Alpha");
        Button btnBeta = new Button("Beta");

        // Handle the action events for the Alpha button.
        btnAlpha.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                response.setText("Alpha was pressed.");
            }
        });

        // Handle the action events for the Beta button.
        btnBeta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                response.setText("Beta was pressed.");
            }
        });

        rootNode.getChildren().addAll(btnAlpha, btnBeta, response);

        primaryStage.show();
    }

    @Override
    public void stop(){
        System.out.println("Inside the stop() method.");
    }
}
