package S2.controller;

import S2.DBAccess.DBAppointment;
import S2.DBAccess.DBUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for the log in form
 */

public class LoginController implements Initializable {
    public Button logInButton;
    public Button exitButton;
    public Label userLabel;
    public Label passwordLabel;
    public TextField userField;
    public TextField passwordField;
    public Label locationLabel;
    public static String user = null;
    ResourceBundle fr = ResourceBundle.getBundle("S2/frProp_fr", Locale.getDefault());

    /**
     * Initializes the log in form and checks for the users locale
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zoneId = ZoneId.systemDefault();
        locationLabel.setText("Your time zone is " + zoneId);
        if(Locale.getDefault().getLanguage().equals("fr")){
            userLabel.setText(fr.getString("user"));
            passwordLabel.setText(fr.getString("password"));
            logInButton.setText(fr.getString("login"));
            exitButton.setText(fr.getString("exit"));
            locationLabel.setText(fr.getString("time") + " " + zoneId);
        }
    }

    /**
     * Validates the user log in and logs all activity to the log file.
     * @param actionEvent login button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void logInClicked (ActionEvent actionEvent) throws IOException {
        File logFile = new File("login_activity.txt");
        logFile.createNewFile();
        FileWriter myWriter = new FileWriter(logFile, true);
        user=userField.getText();
        ZonedDateTime now = ZonedDateTime.now();
        if(DBUsers.logInValidate(userField.getText(), passwordField.getText())){
            int userId = DBUsers.findUserId(userField.getText());
            myWriter.write("Log in successful at " + now + " for user: " + user + "\n");
            myWriter.close();
            if(!DBAppointment.checkForAppointments(now, userId)){
                if(Locale.getDefault().getLanguage().equals("fr")){
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText(fr.getString("appointmentMessage"));
                    a.showAndWait();
                }else {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setContentText("There are no appointments within 15 minutes");
                    a.showAndWait();
                }
            }
            Parent parent = FXMLLoader.load(getClass().getResource("../view/calendar.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else{
            myWriter.write("Log in failed at " + now + " for user: " + user + "\n");
            myWriter.close();
            if(Locale.getDefault().getLanguage().equals("fr")){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle(fr.getString("error"));
                error.setHeaderText(fr.getString("errorMessage"));
                error.showAndWait();
            }else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Invalid username or password");
                error.showAndWait();
            }
        }
    }

    /**
     * Exits the program
     * @param actionEvent Exit button clicked
     */

    public void exitClicked(ActionEvent actionEvent) {
        System.exit(0);
    }
}
