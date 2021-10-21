package S2.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that creates the database connection
 */

public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ08SBQ";
    private static final String jdbcURL = protocol + vendorName + ipAddress +dbName;
    private static final String MYSQLJBCDriver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "U08SBQ";
    private static final String password = "53689377578";
    private static Connection conn = null;

    /**
     * Starts the database connection
     * @return the database connection
     */

    public static Connection startConnection(){
        try{
            Class.forName(MYSQLJBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful");
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Gets the database connection
     * @return the database connection
     */

    public static Connection getConnection(){
        return conn;
    }

    /**
     * Closes the database connection when the program closes
     */

    public static void closeConnection(){
        try{
            conn.close();
        }catch(Exception e){

        }
    }
}
