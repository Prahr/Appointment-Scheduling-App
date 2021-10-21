package S2.controller;
import S2.DBAccess.DBAppointment;
import S2.DBAccess.DBCustomer;
import S2.model.Appointment;
import S2.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the calendar view
 */

public class CalendarController implements Initializable {
    public TableColumn title;
    public TableColumn description;
    public TableColumn location;
    public TableColumn contact;
    public TableColumn type;
    public TableColumn start;
    public TableColumn end;
    public TableColumn customerId;
    public Button addAppointmentButton;
    public Button modifyAppointmentButton;
    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button logoutButton;
    public RadioButton viewByWeek;
    public ToggleGroup viewGroup;
    public RadioButton viewByMonth;
    public RadioButton viewAllButton;
    public TableView customerTable;
    public TableColumn customerTableID;
    public TableColumn customerName;
    public TableColumn address;
    public TableColumn state;
    public TableColumn country;
    public TableColumn zipCode;
    public TableColumn phoneNumber;
    public TableView appointmentTable;
    public TableColumn appointmentId;
    private static Customer modCustomer;
    private static Appointment modAppointment;
    public Button reportsButton;
    public Button contactReportsButton;

    /**
     * Initializes the customer and appointment table views with all customers and appointments in the database
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(DBCustomer.getAllCustomers());
        appointmentTable.setItems(DBAppointment.getAllAppointments());

        //customerTableID.setCellValueFactory(cellData -> cellData.getValue().getCustomerId());
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        zipCode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
    }

    /**
     * Loads the add appointment form
     * @param actionEvent add appointment button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void addAppointmentClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the modify appointment form and makes sure that an appointment is selected
     * @param actionEvent modify appointment button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void modifyAppointmentClicked(ActionEvent actionEvent) throws IOException {
        modAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        if(modAppointment != null){
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyAppointment.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("An appointment must be selected");
            error.showAndWait();
        }
    }

    /**
     * Returns the selected appointment for use in the modify appointment form
     * @return selected appointment
     */

    public static Appointment selectedAppointment(){
        return modAppointment;
    }

    /**
     * Returns the selected customer for use in the modify customer form
     * @return selected customer
     */

    public static Customer selectedCustomer(){
        return modCustomer;
    }

    /**
     * Loads the add customer form
     * @param actionEvent add customer button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void addCustomerClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the modify customer form and confirms that a customer is selected
     * @param actionEvent modify customer button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void modifyCustomerClicked(ActionEvent actionEvent) throws IOException {
        modCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if(modCustomer != null){
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyCustomer.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("A customer must be selected");
            error.showAndWait();
        }
    }

    /**
     * Populates the appointment table with appointments in the current week
     * @param actionEvent view weekly radio button selected
     */

    public void viewByWeekClicked(ActionEvent actionEvent) {
        appointmentTable.setItems(DBAppointment.getWeekAppointments());

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
    }

    /**
     * Populates the appointment table with appointments in the current month
     * @param actionEvent view monthly radio button selected
     */

    public void viewByMonthClicked(ActionEvent actionEvent) {
        appointmentTable.setItems(DBAppointment.getMonthAppointments());

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
    }

    /**
     * Logs out the user and returns them to the log in form
     * @param actionEvent Logout button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void logoutClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates the appointment table with all appointments in the database
     * @param actionEvent view all radio button selected
     */

    public void viewAllClicked(ActionEvent actionEvent) {
        appointmentTable.setItems(DBAppointment.getAllAppointments());

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
    }

    /**
     * Loads the reports form
     * @param actionEvent reports button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void reportsClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/Reports.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the contacts reports form
     * @param actionEvent contact reports button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void contactsClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ContactSchedule.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
