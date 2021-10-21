package S2.controller;

import S2.DBAccess.DBAppointment;
import S2.DBAccess.DBContacts;
import S2.model.Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the contacts schedule form
 */

public class ContactScheduleController implements Initializable {
    public TableColumn appointmentId;
    public TableColumn <Appointment, String> title;
    public TableColumn <Appointment, String> type;
    public TableColumn <Appointment, String> description;
    public TableView <Appointment> appointmentTable;
    public TableColumn <Appointment, String> start;
    public TableColumn <Appointment, String> end;
    public TableColumn customerId;
    public Button exitButton;
    public ComboBox contactBox;

    /**
     * Initializes the contacts schedule form
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactBox.setItems(DBContacts.getAllContacts());
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
     * Populates the appointments table view with all associated appointments based on the selected contact.
     * Lambda expressions are used to populate the table view for any string values. This avoids some downsides of using PropertyValueFactory, such as reflection.
     * @param actionEvent contactBox value changed
     */

    public void contactClicked(ActionEvent actionEvent) {
        appointmentTable.setItems(DBAppointment.getAppointmentsByContact(String.valueOf(contactBox.getValue())));

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getTitle()));
        description.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getDescription()));
        type.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getType()));
        start.setCellValueFactory(appointment -> new SimpleStringProperty(String.valueOf(appointment.getValue().getStart())));
        end.setCellValueFactory(appointment -> new SimpleStringProperty(String.valueOf(appointment.getValue().getEnd())));
    }
}
