package utils;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;


//TODO add/modify/delete users

/**
 * handles database usage with in regards to customer information
 */
public class DBCustomers {

    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private static ObservableList<String> countryList = FXCollections.observableArrayList();
    private static ObservableList<String> firstLevelDivisionList = FXCollections.observableArrayList();

    /**
     * when called adds customer to database
     * @param name name
     * @param address address
     * @param firstDivision first level division
     * @param postalCode postal code
     * @param phoneNumber phone number
     * @throws SQLException
     */
    public static void addCustomer(String name, String address, String firstDivision, String postalCode, String phoneNumber) throws SQLException{

        //connection and prepared statement set up
        Connection conn = DBConnection.getConnection();
        String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?);";
        DBQuery.setPreparedStatement(conn, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        int divisionID = getDivisionID(firstDivision);
        String userName = DBUsers.getUser();

        ps.setString(1,name);
        ps.setString(2, address);
        ps.setString(3,postalCode);
        ps.setString(4,phoneNumber);
        ps.setString(5,userName);
        ps.setString(6,userName);
        ps.setInt(7,divisionID);

        ps.execute();

        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " row(s) effected");
        }
        else {
            System.out.println("no change");
        }
    }

    /**
     * when called updates customer information in database
     * @param customerID customerID
     * @param name name
     * @param address address
     * @param firstDivision first level division
     * @param postalCode postal code
     * @param phoneNumber phone number
     * @throws SQLException
     */
    public static void updateCustomer(int customerID, String name, String address, String firstDivision, String postalCode, String phoneNumber) throws SQLException{

        //connection and prepared statement set up
        Connection conn = DBConnection.getConnection();
        String insertStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?;";
        DBQuery.setPreparedStatement(conn, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        int divisionID = getDivisionID(firstDivision);
        String userName = DBUsers.getUser();

        ps.setString(1,name);
        ps.setString(2, address);
        ps.setString(3,postalCode);
        ps.setString(4,phoneNumber);
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(6,userName);
        ps.setInt(7,divisionID);
        ps.setInt(8, customerID);

        ps.execute();

        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " row(s) effected");
        }
        else {
            System.out.println("no change");
        }
    }

    /**
     * when called delete customer from database
     * @param customerID customerID to locate customer
     * @throws SQLException
     */
    public static void deleteCustomer(int customerID) throws SQLException{

        //connection and prepared statement set up
        Connection conn = DBConnection.getConnection();
        String deleteStatement = "DELETE from customers WHERE Customer_ID = ?;";
        DBQuery.setPreparedStatement(conn, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1,customerID);
        ps.execute();

        if (ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " row(s) effected");
        } else {
            System.out.println("no change");
        }
    }

    /**
     *
     * @param customerId
     * @return
     * @throws SQLException
     */
    public static String getCustomerName(int customerId) throws SQLException{
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM customers WHERE Customer_ID = ?;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1, customerId);
        ps.execute();
        ResultSet rs = ps.getResultSet();

        rs.next();
        return rs.getString("Customer_Name");
    }

    public static int getCustomerID(String customerName) throws SQLException{
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM customers WHERE Customer_Name = ?;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, customerName);
        ps.execute();
        ResultSet rs = ps.getResultSet();

        rs.next();
        return rs.getInt("Customer_ID");
    }


    /**
     * when called gets divisionID from database
     * @param firstLevelDivision takes division_name and gets the ID
     * @return divisionID
     * @throws SQLException
     */
    public static int getDivisionID(String firstLevelDivision) throws SQLException{
        //connection and prepared statement set up
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM first_level_divisions WHERE Division = ?;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, firstLevelDivision);
        ps.execute();
        ResultSet rs = ps.getResultSet();

        rs.next();
        return rs.getInt("Division_ID");
    }

    /**
     * when called gets division Name from database
     * @param divisionID divisionID to search for division name
     * @return division name
     * @throws SQLException
     */
    public static String getDivisionName(int divisionID) throws SQLException{
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM first_level_divisions WHERE Division_ID = ?;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1, divisionID);
        ps.execute();
        ResultSet rs = ps.getResultSet();

        rs.next();
        return rs.getString("Division");
    }

    /**
     * when called gets countryID from database
     * @param firstLevelDivisionID used to search for countryID
     * @return countryID
     * @throws SQLException
     */
    public static int getCountryID(int firstLevelDivisionID) throws SQLException{
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM first_level_divisions WHERE Division_ID = " + firstLevelDivisionID + ";";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.execute();
        ResultSet rs = ps.getResultSet();
        rs.next();
        return rs.getInt("COUNTRY_ID");
    }

    /**
     * when called gets countryID from database
     * @param countryName to search for countryID
     * @return countryID
     * @throws SQLException
     */
    public static int getCountryID(String countryName) throws SQLException{
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM countries WHERE Country = ?;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1,countryName);

        ps.execute();
        ResultSet rs = ps.getResultSet();
        rs.next();
        return rs.getInt("Country_ID");
    }


    /**
     * when called gets country name database
     * @param countryID used to get country name
     * @return country name
     * @throws SQLException
     */
    public static String getCountryName(int countryID) throws SQLException{
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM countries WHERE COUNTRY_ID = " + countryID + ";";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();
        rs.next();
        return rs.getString("Country");
    }

    /**
     * when called gets creates customer list from customer information in database
     * @return ObservableList of customers
     * @throws SQLException
     */
    public static ObservableList<Customer> getCustomerList() throws SQLException{
        customerList.clear();
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM customers;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();

        //creates customer and adds to list
        while(rs.next())
        {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String state = getDivisionName(rs.getInt("Division_ID"));
            String postalCode = rs.getString("Postal_Code");
            String country = getCountryName(getCountryID(rs.getInt("Division_ID")));
            String phoneNumber = rs.getString("Phone");
            Customer customer = new Customer(customerID,customerName,address,state,postalCode,country,phoneNumber);
            customerList.add(customer);
        }
        return customerList;
    }

    /**
     * when called creates first level division list from database
     * @param country used to create the first level division list based on selected country
     * @return ObservableList of first level division
     * @throws SQLException
     */
    public static ObservableList<String> getFirstLevelDivisionList(String country) throws SQLException{
        int countryID = getCountryID(country);
        firstLevelDivisionList.clear();
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ?";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1,countryID);

        ps.execute();
        ResultSet rs = ps.getResultSet();

        while(rs.next()){
            firstLevelDivisionList.add(rs.getString("Division"));
        }
        return firstLevelDivisionList;

    }

    /**
     * when called creates countryList for add/update customers
     * @return returns customer ObservableList
     * @throws SQLException
     */
    public static ObservableList<String> getCountryList() throws SQLException{

        //connection and prepared statement set up
        countryList.clear();
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM countries;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.execute();
        ResultSet rs = ps.getResultSet();

        //fills list with countries
        while(rs.next()){
            countryList.add(rs.getString("Country"));
        }
        return countryList;
    }
}
