package S2.controller;

import S2.DBAccess.DBAppointment;
import S2.DBAccess.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the Reports form
 */

public class ReportsController implements Initializable {
    public ComboBox monthBox;
    public ComboBox typeBox;
    public ComboBox yearBox;
    public Label appointmentNumber;
    public Button appointmentsButton;
    public Button exitButton;
    public ComboBox customerBox;
    public Button appointmentCountButton;
    public Label appointmentNumberLabel;
    private int year = 0;
    private int monthValue = 0;
    private int customerId = 0;

    /**
     * Initializes the reports form.
     * Lambda expression is used to give the exit button an onAction function. using a lambda expression removes an unnecessary method from being created and lets it be contained in the initialize method.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yearBox.getItems().addAll("2021", "2022");
        monthBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        customerBox.setItems(DBCustomer.getAllCustomerIds());
        exitButton.setOnAction((event) -> {
            Parent parent = null;
            try {
                parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/calendar.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert parent != null;
            Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                });
    }

    /**
     * Changes the types of appointment available to select based on the month selected
     * @param actionEvent monthBox value changed
     */

    public void monthClicked(ActionEvent actionEvent) {
        String month = String.valueOf(monthBox.getValue());
        switch(month){
            case "January": monthValue = 1;
            break;
            case "February": monthValue = 2;
            break;
            case "March": monthValue = 3;
            break;
            case "April": monthValue = 4;
            break;
            case "May": monthValue = 5;
            break;
            case "June": monthValue = 6;
            break;
            case "July": monthValue = 7;
            break;
            case "August": monthValue = 8;
            break;
            case "September": monthValue = 9;
            break;
            case "October": monthValue = 10;
            break;
            case "November": monthValue = 11;
            break;
            case "December": monthValue = 12;
            break;
        }
        typeBox.setItems(DBAppointment.getTypesByMonth(monthValue, year));
    }

    /**
     * Changes the types of appointment available to select based on the year selected
     * @param actionEvent yearBox value changed
     */

    public void yearClicked(ActionEvent actionEvent) {
        year = Integer.parseInt(String.valueOf(yearBox.getValue()));
        typeBox.setItems(DBAppointment.getTypesByMonth(monthValue, year));
    }

    /**
     * Gets the number appointments of the selected type in the selected month and year
     * @param actionEvent get appointments button clicked
     */

    public void appointmentsClicked(ActionEvent actionEvent) {
        String type = String.valueOf(typeBox.getValue());
        if(type == null || monthValue == 0 || year == 0){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("A year, month, and type must all be selected.");
            error.showAndWait();
            return;
        }
        appointmentNumber.setText(String.valueOf(DBAppointment.getTypeNumber(type, monthValue, year)));
    }

    /**
     * Saves the value of the customer ID selected
     * @param actionEvent customerBox value changed
     */

    public void customerClicked(ActionEvent actionEvent) {
        customerId = Integer.parseInt(String.valueOf(customerBox.getValue()));
    }

    /**
     * Gets the number of appointments a customer is associated with
     * @param actionEvent get appointments button clicked
     */

    public void countClicked(ActionEvent actionEvent) {
        appointmentNumberLabel.setText(String.valueOf(DBAppointment.getCustomerNumber(customerId)));
    }
}
