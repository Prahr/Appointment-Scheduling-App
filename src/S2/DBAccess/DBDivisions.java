package S2.DBAccess;

import S2.Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that accesses the division table of the database
 */

public class DBDivisions {

    /**
     * Finds division name from division ID
     * @param id division ID
     * @return division name
     */

    public static String findDivision(int id){
        String result = null;
        try{
            String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = " + id;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = rs.getString("Division");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * Finds the division ID associated with a division name
     * @param division division name
     * @return division ID
     */

    public static int findDivisionId(String division){
        int result = 0;
        try{
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = '" + division + "'";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = rs.getInt("Division_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * Gets the country ID associated with a division ID
     * @param id division ID
     * @return country ID
     */

    public static int findCountryId(int id){
        int result = 0;
        try{
            String sql = "SELECT COUNTRY_ID FROM first_level_divisions WHERE Division_ID = " + id;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = rs.getInt("COUNTRY_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * Gets a list of all division names in the database by country ID
     * @param id country ID
     * @return ObservableList of all division names associated with a country ID
     */

    public static ObservableList<String> populateDivisions(int id) {
        ObservableList<String> divisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division FROM first_level_divisions WHERE COUNTRY_ID = " + id;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                divisionList.add(rs.getString("Division"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionList;
    }
}
