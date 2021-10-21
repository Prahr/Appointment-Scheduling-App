package S2.controller;

import S2.DBAccess.DBAppointment;
import S2.DBAccess.DBContacts;
import S2.DBAccess.DBCustomer;
import S2.model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the modify appointment form
 */

public class ModifyAppointmentController implements Initializable {
    public TextField customerIdField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public ComboBox contactBox;
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
    public TextField appointmentIdField;
    public Button deleteButton;
    private final Appointment modAppointment = CalendarController.selectedAppointment();
    private final LocalDateTime startDateHolder = modAppointment.getStart().toLocalDateTime();
    private final LocalDateTime endDateHolder = modAppointment.getEnd().toLocalDateTime();
    private final int appointmentId = modAppointment.getAppointmentId();
    public TextField userIdField;

    /**
     * Initializes the modify appointment form with the values of the appointment selected by the user
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
        if(monthBox.getValue() == "2"){
            dayBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28");
        }
        else if(monthBox.getValue() == "9" || monthBox.getValue() == "4" || monthBox.getValue() == "6" || monthBox.getValue() == "11"){
            dayBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30");
        }
        else{
            dayBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
        }
        customerIdField.setText(String.valueOf(modAppointment.getCustomerId()));
        titleField.setText(modAppointment.getTitle());
        descriptionField.setText(modAppointment.getDescription());
        locationField.setText(modAppointment.getLocation());
        contactBox.getSelectionModel().select(modAppointment.getContact());
        typeField.setText(modAppointment.getType());
        monthBox.getSelectionModel().select(String.valueOf(startDateHolder.getMonthValue()));
        dayBox.getSelectionModel().select(String.valueOf(startDateHolder.getDayOfMonth()));
        yearBox.getSelectionModel().select(String.valueOf(startDateHolder.getYear()));
        startHourBox.getSelectionModel().select(String.valueOf(startDateHolder.getHour()));
        userIdField.setText(String.valueOf(modAppointment.getUserId()));
        if(startDateHolder.getMinute() == 0){
            startMinuteBox.getSelectionModel().select("00");
        }else {
            startMinuteBox.getSelectionModel().select(String.valueOf(startDateHolder.getMinute()));
        }
        endHourBox.getSelectionModel().select(String.valueOf(endDateHolder.getHour()));
        if(endDateHolder.getMinute() == 0){
            endMinuteBox.getSelectionModel().select("00");
        }else {
            endMinuteBox.getSelectionModel().select(String.valueOf(endDateHolder.getMinute()));
        }
        appointmentIdField.setText(String.valueOf(appointmentId));
    }

    /**
     * Updates the modified appointment in the database
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
            Timestamp lastUpdate = Timestamp.valueOf(zdt.toLocalDateTime());
            String lastUpdatedBy = LoginController.user;
            Timestamp start = Timestamp.valueOf(yearBox.getValue() + "-" + monthBox.getValue() + "-" + dayBox.getValue() + " " + startHourBox.getValue() + ":" + startMinuteBox.getValue() + ":00");
            LocalDateTime startLDT = start.toLocalDateTime();
            ZonedDateTime startCheck = startLDT.atZone(ZoneId.systemDefault());
            startCheck = startCheck.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalTime checkStart;
            if(startCheck.getHour() < 10 && startCheck.getMinute() == 0){
                checkStart = LocalTime.parse("0" + startCheck.getHour() + ":0" + startCheck.getMinute() + ":00");
            }
            else if(startCheck.getMinute() == 0){
                checkStart = LocalTime.parse(startCheck.getHour() + ":0" + startCheck.getMinute() + ":00");
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
            if(!DBAppointment.checkForOverlapModify(customerId, startCheck, endCheck, appointmentId)){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Appointment times overlap.");
                error.showAndWait();
                return;
            }
            int userId = Integer.parseInt(userIdField.getText());
            int contactId = DBContacts.findContactId(String.valueOf(contactBox.getValue()));

            if(DBAppointment.updateAppointment(title, description, location, type, start, end, lastUpdate, lastUpdatedBy, customerId, userId, contactId, appointmentId)){
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
     * Deletes the selected appointment from the database and returns the user to the calendar view
     * @param actionEvent delete button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void deleteClicked(ActionEvent actionEvent) throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm");
        confirm.setContentText("Are you sure you want to delete appointment " + appointmentId + " of type " + modAppointment.getType() +"?");
        Optional<ButtonType> result = confirm.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            DBAppointment.deleteAppointment(modAppointment.getAppointmentId());
            Alert deleted = new Alert(Alert.AlertType.INFORMATION);
            deleted.setContentText("Appointment "+ appointmentId + " of type " + modAppointment.getType() + " was deleted.");
            deleted.showAndWait();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/calendar.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
