package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * this class creates prepared statements to be used
 */
public class DBQuery {

    private static PreparedStatement statement;

    //create statement objects
    public static void setPreparedStatement(Connection conn,String sqlStatement) throws SQLException
    {
        statement = conn.prepareStatement(sqlStatement);
    }

    //returns statement object
    public static PreparedStatement getPreparedStatement()
    {
        return statement;
    }
}
