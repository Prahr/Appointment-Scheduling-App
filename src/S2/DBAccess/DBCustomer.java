package S2.DBAccess;

import S2.Database.DBConnection;
import S2.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Class that accesses the customer table in the database
 */

public class DBCustomer {

    /**
     * Gets all customers in the database
     * @return all customers in the database in an ObservableList of Customer objects
     */

    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zipCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");
                String state = DBDivisions.findDivision(divisionId);
                String country = DBCountries.findCountry(DBDivisions.findCountryId(divisionId));
                Customer C = new Customer(customerId, customerName, address, zipCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, state, country);
                customerList.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * Checks that a given customer ID exists in the database
     * @param id customer ID to check
     * @return true if the customer exists. False if not.
     */

    public static boolean customerIdExists(int id) {
        boolean exists = false;
        ObservableList<String> customerIdList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Customer_ID FROM customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                customerIdList.add(String.valueOf(customerId));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(customerIdList.contains(String.valueOf(id))){
            exists = true;
        }
        return exists;
    }

    /**
     * Adds a customer to the database
     * @param customerName customer name
     * @param address customer address
     * @param zipCode customer postal code
     * @param phoneNumber customer phone number
     * @param createDate date and time the customer was created
     * @param createdBy user creating the customer
     * @param lastUpdate date and time the customer was last updated
     * @param lastUpdatedBy user who last updated the customer
     * @param divisionId divisionID associated with the customer
     * @return true if customer successfully added. False if not.
     */

    public static boolean addCustomer(String customerName, String address, String zipCode, String phoneNumber, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId){
        boolean added = false;

        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES ('" + customerName + "', '" + address + "', '" + zipCode + "', '" + phoneNumber + "', '" + createDate + "', '" + createdBy + "', '" + lastUpdate + "', '" + lastUpdatedBy + "', " + divisionId + ");";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            added = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return added;
    }

    /**
     * Updates an existing customer in the database
     * @param customerName customer name
     * @param address customer address
     * @param zipCode customer postal code
     * @param phoneNumber customer phone number
     * @param lastUpdate date and time of the last update to the customer
     * @param lastUpdatedBy user who last updated the customer
     * @param divisionId division ID associated with the customer
     * @param customerId customer ID
     * @return true if customer successfully updated. False if not.
     */

    public static boolean updateCustomer(String customerName, String address, String zipCode, String phoneNumber, Timestamp lastUpdate, String lastUpdatedBy, int divisionId, int customerId){
        boolean updated = false;

        try {
            String sql = "UPDATE customers SET Customer_Name = '" + customerName + "', Address = '" + address + "', Postal_Code = '" + zipCode + "', Phone = '" + phoneNumber + "', Last_Update = '" + lastUpdate + "', Last_Updated_By = '" + lastUpdatedBy + "', Division_ID = " + divisionId + " WHERE Customer_ID = " + customerId;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            updated = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }

    /**
     * Deleted a customer from the database
     * @param id customer ID
     */

    public static void deleteCustomer(int id){
        try{
            String sql = "DELETE FROM customers WHERE Customer_ID = " + id;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets all customer IDs in the database
     * @return ObservableList of strings that contain all customer IDs
     */

    public static ObservableList<String> getAllCustomerIds() {
        ObservableList<String> customerIdList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Customer_ID FROM customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                customerIdList.add(String.valueOf(customerId));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerIdList;
    }
}
