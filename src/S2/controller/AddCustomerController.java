package S2.controller;

import S2.DBAccess.DBCustomer;
import S2.DBAccess.DBDivisions;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * Controller for the add customer form
 */

public class AddCustomerController implements Initializable {
    public ComboBox countryBox;
    public ComboBox stateBox;
    public TextField customerNameField;
    public TextField addressField;
    public TextField zipCodeField;
    public TextField phoneField;
    public Button saveButton;
    public Button exitButton;

    /**
     * Initializes the add customer form
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.getItems().addAll(
                "USA",
                "UK",
                "Canada"
        );
    }

    /**
     * Saves the customer to the database
     * @param actionEvent save button clicked
     */

    public void saveClicked(ActionEvent actionEvent) {
        try{
            String customerName = customerNameField.getText();
            String address = addressField.getText();
            String zipCode = zipCodeField.getText();
            String phoneNumber = phoneField.getText();
            int divisionId = DBDivisions.findDivisionId(stateBox.getValue().toString());
            ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Etc/UTC"));
            Timestamp createDate = Timestamp.valueOf(zdt.toLocalDateTime());
            String createdBy = LoginController.user;
            Timestamp lastUpdate = Timestamp.valueOf(zdt.toLocalDateTime());
            String lastUpdatedBy = LoginController.user;

            if(DBCustomer.addCustomer(customerName, address, zipCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId)){
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
     * Changes the first level divisions that can be picked based on the country selected
     * @param actionEvent countryBox value changed
     */

    public void countryClicked(ActionEvent actionEvent) {
        if(countryBox.getValue() == "USA"){
            stateBox.getItems().clear();
            stateBox.setItems(DBDivisions.populateDivisions(1));
        }
        else if(countryBox.getValue() == "UK"){
            stateBox.getItems().clear();
            stateBox.setItems(DBDivisions.populateDivisions(2));
        }
        else if(countryBox.getValue() == "Canada"){
            stateBox.getItems().clear();
            stateBox.setItems(DBDivisions.populateDivisions(3));
        }
    }

}
