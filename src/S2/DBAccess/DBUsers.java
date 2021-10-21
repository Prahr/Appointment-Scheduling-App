package S2.DBAccess;

import S2.Database.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that accesses the user table of the database
 */

public class DBUsers {

    /**
     * Gets the user ID associated with a user name
     * @param user user name
     * @return user ID
     */

    public static int findUserId(String user){
        int result = 0;
        try{
            String sql = "SELECT User_ID FROM users WHERE User_Name = '" + user + "'";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = rs.getInt("User_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    /**
     * Validates user log ins. Confirms that given usernames and passwords match
     * @param user username
     * @param password password
     * @return true if both username and password match. False if not.
     */

    public static boolean logInValidate(String user, String password){
        boolean result = false;
        try{
            String sql = "SELECT User_Name, Password FROM users WHERE User_Name = '" + user + "'";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(user.equals(rs.getString("User_Name")) && password.equals(rs.getString("Password"))){
                    result = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }
}
