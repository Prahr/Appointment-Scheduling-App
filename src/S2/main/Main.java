package S2.main;

import S2.Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Ryan Wachter
 * Student ID: 003936554
 */



public class Main extends Application {

    /**
     * Loads the stage and sets the initial scene.
     * @param primaryStage Creates the primary stage to load scenes onto
     * @throws Exception Catches error if resource loaded is null
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
