package S2.DBAccess;

import S2.Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that accesses the contacts table of the database
 */

public class DBContacts {

    /**
     * Finds contact name based on the given contact ID
     * @param id contact ID
     * @return contact name from contact ID
     */

    public static String findContact(int id){
        String result = null;
        try{
            String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = " + id;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = rs.getString("Contact_Name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * Gets all contact names
     * @return ObservableList of strings containing all contact names
     */

    public static ObservableList<String> getAllContacts() {
        ObservableList<String> contactsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Contact_Name FROM contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                contactsList.add(rs.getString("Contact_Name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactsList;
    }

    /**
     * Gets the contact ID from contact name
     * @param contact contact name
     * @return contact ID from contact name
     */

    public static int findContactId(String contact) {
        int result = 0;
        try {
            String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + contact + "'";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getInt("Contact_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
