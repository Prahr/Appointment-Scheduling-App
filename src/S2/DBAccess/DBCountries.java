package S2.DBAccess;

import S2.Database.DBConnection;
import java.sql.*;

/**
 * Class that accesses the countries table of the database
 */

public class DBCountries {

    /**
     * Finds the country associated with a given division ID
     * @param id division ID
     * @return country associated with the given division ID
     */

    public static String findCountry(int id){
        String result = null;
        try{
            String sql = "SELECT Country FROM countries WHERE Country_ID = " + id;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = rs.getString("Country");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }
}
