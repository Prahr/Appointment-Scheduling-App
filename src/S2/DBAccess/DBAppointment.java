package S2.DBAccess;

import S2.Database.DBConnection;
import S2.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that accesses the appointment table of the database
 */

public class DBAppointment {

    /**
     * Gets all appointments in the database
     * @return all appointments in the database in an ObservableList of Appointment objects
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contact = DBContacts.findContact(contactId);
                Appointment A = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contact);
                appointmentList.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Adds an appointment to the database
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start date and time
     * @param end appointment end date and time
     * @param createDate date and time appointment was created
     * @param createdBy user that created the appointment
     * @param lastUpdate date and time of the last update to the appointment
     * @param lastUpdatedBy user that last updated the appointment
     * @param customerId customer ID associated with the appointment
     * @param userId user ID that created the appointment
     * @param contactId contact ID associated with the appointment
     * @return true if adding was successful. False if not.
     */

    public static boolean addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId){
        boolean added = false;

        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES ('" + title + "', '" + description + "', '" + location + "', '" + type + "', '" + start + "', '" + end + "', '"  + createDate + "', '" + createdBy + "', '" + lastUpdate + "', '" + lastUpdatedBy + "', " + customerId + ", " + userId + ", " + contactId + ");";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            added = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return added;
    }

    /**
     * Deletes all appointments associated with a customer ID
     * @param id customer ID
     */

    public static void deleteAppointmentByCustomer(int id){
        try{
            String sql = "DELETE FROM appointments WHERE Customer_ID = " + id;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes an appointment by appointment ID
     * @param id appointment ID
     */

    public static void deleteAppointment(int id){
        try{
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + id;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Checks for overlapping times when scheduling appointments
     * @param customerId customer ID
     * @param start start time desired
     * @param end end time desired
     * @return true if no overlap. False is there is overlap.
     */

    public static boolean checkForOverlap(int customerId, ZonedDateTime start, ZonedDateTime end){
        try {
            String sql = "SELECT Start, End FROM appointments WHERE Customer_ID = " + customerId;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp startToCheck = rs.getTimestamp("Start");
                Timestamp endToCheck = rs.getTimestamp("End");
                LocalDateTime startLDT = startToCheck.toLocalDateTime();
                LocalDateTime endLDT = endToCheck.toLocalDateTime();
                ZonedDateTime startCheck = startLDT.atZone(ZoneId.systemDefault());
                startCheck = startCheck.withZoneSameInstant(ZoneId.of("Etc/UTC"));
                ZonedDateTime endCheck = endLDT.atZone(ZoneId.systemDefault());
                endCheck = endCheck.withZoneSameInstant(ZoneId.of("Etc/UTC"));
                if(start.isBefore(endCheck) && startCheck.isBefore(end)){
                    return false;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    /**
     * Checks for overlapping times when modifying appointment times
     * @param customerId customer ID
     * @param start start time desired
     * @param end end time desired
     * @param appointmentId appointment ID of appointment to be modified
     * @return true if no overlap. False if there is overlap.
     */

    public static boolean checkForOverlapModify(int customerId, ZonedDateTime start, ZonedDateTime end, int appointmentId){
        try {
            String sql = "SELECT Start, End FROM appointments WHERE Customer_ID = " + customerId + " AND NOT Appointment_ID = " + appointmentId + ";";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp startToCheck = rs.getTimestamp("Start");
                Timestamp endToCheck = rs.getTimestamp("End");
                LocalDateTime startLDT = startToCheck.toLocalDateTime();
                LocalDateTime endLDT = endToCheck.toLocalDateTime();
                ZonedDateTime startCheck = startLDT.atZone(ZoneId.systemDefault());
                startCheck = startCheck.withZoneSameInstant(ZoneId.of("Etc/UTC"));
                ZonedDateTime endCheck = endLDT.atZone(ZoneId.systemDefault());
                endCheck = endCheck.withZoneSameInstant(ZoneId.of("Etc/UTC"));
                if(start.isBefore(endCheck) && startCheck.isBefore(end)){
                    return false;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    /**
     * Checks for appointments for the user within 15 minutes of log in
     * @param logInTime time the user is logging in
     * @param userId user ID to check
     * @return true if there is an appointment within 15 minutes. False if not.
     */

    public static boolean checkForAppointments(ZonedDateTime logInTime, int userId){
        boolean appointmentSoon = false;
        ResourceBundle fr = ResourceBundle.getBundle("S2/frProp_fr", Locale.getDefault());
        try {
            String sql = "SELECT Appointment_ID, Start, User_ID FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                int checkUserId = rs.getInt("User_ID");
                Timestamp startToCheck = rs.getTimestamp("Start");
                LocalDateTime startLDT = startToCheck.toLocalDateTime();
                ZonedDateTime startCheck = startLDT.atZone(ZoneId.systemDefault());
                if(Duration.between(logInTime, startCheck).toMinutes() <= 15 && Duration.between(logInTime, startCheck).toMinutes() >= 0 && checkUserId == userId){
                    appointmentSoon = true;
                    if(Locale.getDefault().getLanguage().equals("fr")){
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText(fr.getString("appointmentSoon") +"\n" + fr.getString("appointmentID") + appointmentId + "\n" + fr.getString("timeAppointment") + startToCheck);
                        a.showAndWait();
                    }else {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("There is an appointment within 15 minutes.\nAppointment ID: " + appointmentId + "\nTime: " + startToCheck);
                        a.showAndWait();
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentSoon;
    }

    /**
     * Gets all appointments in the current week
     * @return ObservableList of Appointment objects with all appointments in the current week
     */

    public static ObservableList<Appointment> getWeekAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        WeekFields wf = WeekFields.of(DayOfWeek.MONDAY, 1);
        TemporalField weekOfYear = wf.weekOfYear();
        LocalDateTime now = LocalDateTime.now();

        try {
            String sql = "SELECT * FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contact = DBContacts.findContact(contactId);

                LocalDateTime checkWeek = start.toLocalDateTime();
                if(checkWeek.get(weekOfYear) == now.get(weekOfYear) && checkWeek.getYear() == now.getYear()){
                    Appointment A = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contact);
                    appointmentList.add(A);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Gets all appointments in the current month
     * @return ObservableList of Appointment objects with all appointments in the current month
     */

    public static ObservableList<Appointment> getMonthAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();

        try {
            String sql = "SELECT * FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contact = DBContacts.findContact(contactId);

                LocalDateTime checkMonth = start.toLocalDateTime();
                if(checkMonth.getMonth() == now.getMonth() && checkMonth.getYear() == now.getYear()){
                    Appointment A = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contact);
                    appointmentList.add(A);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Updates appointment information and saves to the database
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start date and time
     * @param end appointment end date and time
     * @param lastUpdate date and time of the last appointment update
     * @param lastUpdatedBy user who last updated the appointment
     * @param customerId customer ID associated with the appointment
     * @param userId user ID of the user updating the appointment
     * @param contactId Contact ID associated with the appointment
     * @param appointmentId appointment ID
     * @return true if appointment successfully updated. False if not.
     */

    public static boolean updateAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId, int appointmentId){
        boolean updated = false;

        try {
            String sql = "UPDATE appointments SET Title = '" + title + "', Description = '" + description + "', Location = '" + location + "', Type = '" + type + "', Start = '" + start + "', End = '" + end + "', Last_Update = '" + lastUpdate + "', Last_Updated_By = '" + lastUpdatedBy + "', Customer_ID = " + customerId +  ", User_ID = " + userId + ", Contact_ID = " + contactId + " WHERE Appointment_ID = " + appointmentId;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            updated = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }

    /**
     * Gets all appointment types in the indicated month
     * @param month month to check
     * @param year year to check
     * @return ObservableList of Strings containing all appointment types in the indicated month, with no duplicates
     */

    public static ObservableList<String> getTypesByMonth(int month, int year) {
        ObservableList<String> typeList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Type, Start FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp start = rs.getTimestamp("Start");
                LocalDateTime startMonth = start.toLocalDateTime();
                int startMonthValue = startMonth.getMonthValue();
                int startYearValue = startMonth.getYear();
                String type = rs.getString("Type");
                if(month == startMonthValue && startYearValue == year){
                    if(typeList.contains(type)){
                        break;
                    }
                    typeList.add(type);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return typeList;
    }

    /**
     * Gets the number of an appointment type in the indicated month
     * @param typeToCount The appointment type to count
     * @param month month to check
     * @param year year to check
     * @return count of an appointment type
     */

    public static int getTypeNumber(String typeToCount, int month, int year) {
        int count = 0;
        try {
            String sql = "SELECT Type, Start FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp start = rs.getTimestamp("Start");
                LocalDateTime startMonth = start.toLocalDateTime();
                int startMonthValue = startMonth.getMonthValue();
                int startYearValue = startMonth.getYear();
                String type = rs.getString("Type");
                if(month == startMonthValue && startYearValue == year && type.equals(typeToCount)){
                    count++;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    /**
     * Gets the number of appointments associated with a certain customer
     * @param customerIdToCheck customer ID to check
     * @return count of appointments associated with the customer
     */

    public static int getCustomerNumber(int customerIdToCheck) {
        int count = 0;
        try {
            String sql = "SELECT Customer_ID FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                if(customerId == customerIdToCheck){
                    count++;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    /**
     * Gets all appointments associated with a certain contact
     * @param contactName contact to check
     * @return ObservableList of Appointment objects containing all appointments associated with the contact
     */

    public static ObservableList<Appointment> getAppointmentsByContact(String contactName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID = " + DBContacts.findContactId(contactName);

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment A = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName);
                appointmentList.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }
}

