package S2.controller;

import S2.DBAccess.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * Controller for the add appointment form
 */

public class AddAppointmentController implements Initializable {
    public TextField customerIdField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public ComboBox dayBox;
    public ComboBox monthBox;
    public ComboBox yearBox;
    public ComboBox startHourBox;
    public ComboBox startMinuteBox;
    public ComboBox endHourBox;
    public ComboBox endMinuteBox;
    public Button saveButton;
    public Button exitButton;
    public ComboBox contactBox;
    public TextField userIdField;

    /**
     * Initializes the add appointment form
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactBox.setItems(DBContacts.getAllContacts());
        yearBox.getItems().addAll("2021", "2022");
        monthBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        startHourBox.getItems().addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        startMinuteBox.getItems().addAll("00", "15", "30", "45");
        endHourBox.getItems().addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        endMinuteBox.getItems().addAll("00", "15", "30", "45");
    }

    /**
     * Saves the appointment to the database
     * @param actionEvent save button clicked
     */

    public void saveClicked(ActionEvent actionEvent) {
        try{
            String title = titleField.getText();
            String description = descriptionField.getText();
            String location = locationField.getText();
            String type = typeField.getText();
            int customerId = Integer.parseInt(customerIdField.getText());
            if(!DBCustomer.customerIdExists(customerId)){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Customer does not exist.");
                error.showAndWait();
                return;
            }
            ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Etc/UTC"));
            Timestamp createDate = Timestamp.valueOf(zdt.toLocalDateTime());
            String createdBy = LoginController.user;
            Timestamp lastUpdate = Timestamp.valueOf(zdt.toLocalDateTime());
            String lastUpdatedBy = LoginController.user;
            Timestamp start = Timestamp.valueOf(yearBox.getValue() + "-" + monthBox.getValue() + "-" + dayBox.getValue() + " " + startHourBox.getValue() + ":" + startMinuteBox.getValue() + ":00");
            LocalDateTime startLDT = start.toLocalDateTime();
            ZonedDateTime startCheck = startLDT.atZone(ZoneId.systemDefault());
            startCheck = startCheck.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalTime checkStart;
            if(startCheck.getHour() < 10 && startCheck.getMinute() == 0){
                checkStart = LocalTime.parse("0" + startCheck.getHour() + ":00:00");
            }
            else if(startCheck.getMinute() == 0){
                checkStart = LocalTime.parse(startCheck.getHour() + ":00:00");
            }
            else if(startCheck.getHour() < 10){
                checkStart = LocalTime.parse("0" + startCheck.getHour() + ":" + startCheck.getMinute() + ":00");
            }
            else{
                checkStart = LocalTime.parse(startCheck.getHour() + ":" + startCheck.getMinute() + ":00");
            }
            if(!checkTime(checkStart)){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Appointment time is outside of business hours.");
                error.showAndWait();
                return;
            }
            startCheck = startCheck.withZoneSameInstant(ZoneId.of("Etc/UTC"));
            start = Timestamp.valueOf(startCheck.toLocalDateTime());
            Timestamp end = Timestamp.valueOf(yearBox.getValue() + "-" + monthBox.getValue() + "-" + dayBox.getValue() + " " + endHourBox.getValue() + ":" + endMinuteBox.getValue() + ":00");
            LocalDateTime endLDT = end.toLocalDateTime();
            ZonedDateTime endCheck = endLDT.atZone(ZoneId.systemDefault());
            endCheck = endCheck.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalTime checkEnd;
            if(endCheck.getHour() < 10 && endCheck.getMinute() == 0){
                checkEnd = LocalTime.parse("0" + endCheck.getHour() + ":00:00");
            }
            else if(endCheck.getMinute() == 0){
                checkEnd = LocalTime.parse(endCheck.getHour() + ":00:00");
            }
            else if(endCheck.getHour() < 10){
                checkEnd = LocalTime.parse("0" + endCheck.getHour() + ":" + endCheck.getMinute() + ":00");
            }
            else{
                checkEnd = LocalTime.parse(endCheck.getHour() + ":" + endCheck.getMinute() + ":00");
            }
            if(!checkTime(checkEnd)){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Appointment time is outside of business hours.");
                error.showAndWait();
                return;
            }
            endCheck = endCheck.withZoneSameInstant(ZoneId.of("Etc/UTC"));
            end = Timestamp.valueOf(endCheck.toLocalDateTime());
            if(endCheck.isBefore(startCheck) || endCheck.isEqual(startCheck)){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Appointment end time must be after appointment start time.");
                error.showAndWait();
                return;
            }
            if(!DBAppointment.checkForOverlap(customerId, startCheck, endCheck)){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Appointment times overlap.");
                error.showAndWait();
                return;
            }
            int userId = Integer.parseInt(userIdField.getText());
            int contactId = DBContacts.findContactId(String.valueOf(contactBox.getValue()));

            if(DBAppointment.addAppointment(title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId)){
                Parent parent = FXMLLoader.load(getClass().getResource("../view/calendar.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
       }catch(Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Invalid input");
            error.showAndWait();
       }
    }

    /**
     * Checks that the desired time is within business hours
     * @param check time to check
     * @return true if time is within business hours. False if not.
     */

    public boolean checkTime(LocalTime check){
        LocalTime open = LocalTime.parse("08:00:00");
        LocalTime close = LocalTime.parse("22:00:00");
        boolean timeValid = false;
        boolean equal = check.equals(open) || check.equals(close);
        boolean checkTime = check.isAfter(open) && check.isBefore(close);
        if(equal || checkTime){
            timeValid = true;
        }
        return timeValid;
    }

    /**
     * Exits the form back to the calendar view
     * @param actionEvent Exit button clicked
     * @throws IOException Catches error if loaded resource is null
     */

    public void exitClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/calendar.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Changes the possible days of the month based on the month selected
     * @param actionEvent monthBox value changed
     */

    public void monthClicked(ActionEvent actionEvent) {
        if(monthBox.getValue() == "2"){
            dayBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28");
        }
        else if(monthBox.getValue() == "9" || monthBox.getValue() == "4" || monthBox.getValue() == "6" || monthBox.getValue() == "11"){
            dayBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30");
        }
        else{
            dayBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
        }
    }
}
