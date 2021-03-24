package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * this class controls connection to database
 */
public class DBConnection {

    //Variable Declarations
    private static final String protocol = "jdbc";
    private static final String vendorName = ":MySQL:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ07dLx";

    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    //Driver and connection reference
    private static final String MYSQLJDBCDRiver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;

    //User name
    private static final String username = "U07dLx";

    //password
    private static final String password = "53688997945";


    //opens connection
    public static Connection startConnection(){
        try {
            Class.forName(MYSQLJDBCDRiver);
            conn = DriverManager.getConnection(jdbcURL,username,password);
            System.out.println("Connection Successful");
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
        }

        return conn;
    }

    public static Connection getConnection(){
        return conn;
    }

    //close connection
    public static void closeConnection(){
        try{
            conn.close();
            System.out.println("Connection closed");
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
        }
    }


}
