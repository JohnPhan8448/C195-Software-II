package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//TODO add/modify/delete users

/**
 * handles database usage with user table.
 */
public class DBUsers {

    public static int userID;

    private static ObservableList<Integer> userIDList = FXCollections.observableArrayList();

    /**
     * verify user input for login information.
     * @param user user to look for userName
     * @param password checks for password
     * @return true valid username/password or false invalid.
     */
    public static boolean loginVerification(String user, String password) {
        try {
            String verifyStatement = "SELECT * FROM users WHERE User_Name = ? AND Password = ?;";
            Connection conn = DBConnection.getConnection();
            DBQuery.setPreparedStatement(conn, verifyStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, user);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userID = rs.getInt("User_ID");
                return true;
            }
            else
                return false;
        }
        catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * when called returns userID
     * @return userID
     */
    public static int getUserID(){
        return userID;
    }

    /**
     * when called gets user name
     * @return userName
     * @throws SQLException
     */
    public static String getUser() throws SQLException {
        String selectStatement = "SELECT * FROM users WHERE User_ID = ?;";
        Connection conn = DBConnection.getConnection();
        DBQuery.setPreparedStatement(conn,selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1,userID);
        ps.execute();

        ResultSet rs = ps.getResultSet();
        rs.next();
        return rs.getString("User_Name");
    }

    /**
     * when called returns user id list
     * @return ObservableList of users id
     * @throws SQLException
     */
    public static ObservableList<Integer> getUserIDList() throws SQLException{
        userIDList.clear();
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM users";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.execute();
        ResultSet rs = ps.getResultSet();
        while(rs.next()){
            userIDList.add(rs.getInt("User_ID"));
        }
        return userIDList;
    }

}
