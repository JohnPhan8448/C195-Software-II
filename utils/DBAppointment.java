package utils;

import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;


/**
 * this class controls handling with the database information on appointments
 */
public class DBAppointment {

    //observable list declarations
    private static final ObservableList<Appointment> appointmentByContactsList = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> weeklyAppointmentList = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> monthlyAppointmentList = FXCollections.observableArrayList();
    private static final ObservableList<Contact> contactList = FXCollections.observableArrayList();
    private static final ObservableList<String> contactNameList = FXCollections.observableArrayList();
    private static final ObservableList<Integer> customerIDList = FXCollections.observableArrayList();

    //date time formatter
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

    /**
     * when called creates new appointment in database
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param start start Date and Time
     * @param end end Date and Time
     * @param customerID customerID
     * @param userID userID
     * @param contactID contactID
     * @throws SQLException
     */
    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {
        //connection to DB and creating prepared statement
        Connection conn = DBConnection.getConnection();
        String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
        DBQuery.setPreparedStatement(conn, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        String createdBy = DBUsers.getUser();
        Timestamp startTime = Timestamp.valueOf(start);
        Timestamp endTime = Timestamp.valueOf(end);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startTime);
        ps.setTimestamp(6, endTime);
        ps.setString(7, createdBy);
        ps.setString(8, createdBy);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);
        ps.setInt(11, contactID);

        ps.execute();

        if (ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " row(s) effected");
        } else {
            System.out.println("no change");
        }
    }

    /**
     * when called updates appointment in database
     * @param appointmentID appointmentID
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param start start Date and Time
     * @param end end Date and Time
     * @param customerID customerID
     * @param userID userID
     * @param contactID contactID
     * @throws SQLException
     */
    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {
        //Connection to DB and setting up prepared statement
        Connection conn = DBConnection.getConnection();
        String updateStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?,  Contact_ID = ? WHERE  Appointment_ID = ?;";
        DBQuery.setPreparedStatement(conn, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        String lastUpdatedBy = DBUsers.getUser();
        Timestamp startTime = Timestamp.valueOf(start);
        Timestamp endTime = Timestamp.valueOf(end);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startTime);
        ps.setTimestamp(6, endTime);
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);
        ps.setInt(11, contactID);
        ps.setInt(12, appointmentID);

        ps.execute();

        if (ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " row(s) effected");
        } else {
            System.out.println("no change");
        }
    }

    /**
     * when called deletes appointment in database
     * @param appointmentID appointmentID to locate in database
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentID) throws SQLException {
        //connection and set up of prepared statement
        Connection conn = DBConnection.getConnection();
        String deleteStatement = "DELETE from appointments WHERE Appointment_ID = ?;";

        DBQuery.setPreparedStatement(conn, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, appointmentID);
        ps.execute();

        if (ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " row(s) effected");
        } else {
            System.out.println("no change");
        }

    }

    /**
     * when called creates a list of contactNames from database
     * @return list of contact names;
     * @throws SQLException
     */
    public static ObservableList<String> getContactNameList() throws SQLException{
        contactNameList.clear();
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM contacts";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.execute();
        ResultSet rs = ps.getResultSet();

        while(rs.next()){
            contactNameList.add(rs.getString("Contact_Name"));
        }
        return contactNameList;
    }

    /**
     * when called creates customerID list to be used in ComboBox
     * @return returns observable list for ComboBox
     * @throws SQLException
     */
    public static ObservableList<Integer> getCustomerIDList() throws SQLException {
        //connection setup and prepared statement
        customerIDList.clear();
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM customers";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.execute();
        ResultSet rs = ps.getResultSet();

        //fills in ObservableList with CustomerID
        while (rs.next()) {
            customerIDList.add(rs.getInt("Customer_ID"));
        }
        return customerIDList;
    }

    /**
     * when called gets contactID using contactName
     * @param contactName contactName
     * @return contactID
     * @throws SQLException
     */
    public static int getContactID(String contactName) throws SQLException{
        contactList.clear();
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM contacts WHERE Contact_Name = ?";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1,contactName);

        ps.execute();
        ResultSet rs = ps.getResultSet();
        rs.next();
        return rs.getInt("Contact_ID");
    }

    /**
     * when called gets contactName using contactID
     * @param contactId
     * @return contactName
     * @throws SQLException
     */
    public static String getContactName(int contactId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM contacts WHERE Contact_ID = ?";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, contactId);

        ps.execute();
        ResultSet rs = ps.getResultSet();
        rs.next();
        return rs.getString("Contact_Name");
    }

    /**
     * when called checks to see if customer has any appointments
     * @param customerID customerID used to check for any appointments
     * @return true if has appointments. else false
     * @throws SQLException
     */
    public static boolean appointmentCheck(int customerID) throws SQLException {
        //connection and prepared statement setup
        String verifyStatement = "SELECT * FROM appointments WHERE Customer_ID = ?;";
        Connection conn = DBConnection.getConnection();
        DBQuery.setPreparedStatement(conn, verifyStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();

        //checks if any appointments
        return rs.next();
    }

    /**
     * when called creates appointment list
     * @return list of appointments
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentList(String i) throws SQLException {
        //connection and prepared statement setup
        appointmentList.clear();
        weeklyAppointmentList.clear();
        monthlyAppointmentList.clear();
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM appointments;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();

        //creates an appointment and adds to ObservableList
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            String contactName = getContactName(contactId);

            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            ZoneId gmtZoneId = ZoneId.of("UTC");
            LocalDateTime startDT = start.toLocalDateTime();
            LocalDateTime endDT = end.toLocalDateTime();
            LocalDateTime sevenDT = LocalDateTime.now().plusDays(7);
            LocalDateTime localDT = LocalDateTime.now();


            ZonedDateTime startZDT = ZonedDateTime.of(startDT,gmtZoneId);
            ZonedDateTime startZDTtoGMT =startZDT.withZoneSameInstant(localZoneId);
            LocalDateTime startDTinGMT = LocalDateTime.of(startZDTtoGMT.toLocalDate(),startZDTtoGMT.toLocalTime());
            String startDTs = startDTinGMT.format(formatter);


            ZonedDateTime endZDT = ZonedDateTime.of(endDT,gmtZoneId);
            ZonedDateTime endZDTtoGMT =endZDT.withZoneSameInstant(localZoneId);
            LocalDateTime endDTinGMT = LocalDateTime.of(endZDTtoGMT.toLocalDate(),endZDTtoGMT.toLocalTime());
            String endDTs = endDTinGMT.format(formatter);

            Appointment appointment = new Appointment(appointmentId, title, description, location, type, startDTs, endDTs, customerId, userId, contactId, contactName);
            appointmentList.add(appointment);
            //checks if within 7 days and after current time
            if (startDT.isBefore(sevenDT) && startDT.isAfter(localDT)) {
                weeklyAppointmentList.add(appointment);
            }
            //checks if same month.
            if (startDT.getMonth().equals(localDT.getMonth())){
                monthlyAppointmentList.add(appointment);
            }
        }
        if(i.equals("all"))
        return appointmentList;
        else if(i.equals("weekly"))
            return weeklyAppointmentList;
        else
            return monthlyAppointmentList;
    }

    /**
     * when called creates count report for appointments by month
     * @param month month used to search for appointments
     * @return number of appointments of specified month
     * @throws SQLException
     */
    public static int appointmentsByMonth(Month month) throws SQLException{
        //connection and prepared statement setup
        int appointmentCount = 0;
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM appointments;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();

        //checks month if true, adds to count
        while (rs.next()) {
            Timestamp start = rs.getTimestamp("Start");

            LocalDateTime startDT = start.toLocalDateTime();

            if(month.equals(startDT.getMonth())){
                appointmentCount++;
            }
        }
        return appointmentCount;

    }

    /**
     * when called gets count for appointment by specified region
     * @param i country ID
     * @return appointmentCount
     * @throws SQLException
     */
    public static int appointmentsByRegion(int i) throws SQLException{
        int appointmentCount = 0;

        //connection and prepared statement set up
        //3 prepared statements to go from appointments->customers->first_level_divisions
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM appointments;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps1 = DBQuery.getPreparedStatement();

        String selectStatement2 = ("SELECT * FROM customers WHERE Customer_ID = ?;");
        DBQuery.setPreparedStatement(conn, selectStatement2);
        PreparedStatement ps2 = DBQuery.getPreparedStatement();

        String selectStatement3 = ("SELECT * FROM first_level_divisions WHERE Division_ID = ?;");
        DBQuery.setPreparedStatement(conn, selectStatement3);
        PreparedStatement ps3 = DBQuery.getPreparedStatement();

        ps1.execute();
        ResultSet rs = ps1.getResultSet();

        //goes through each prepared statements gets Country_ID and adds count to specified country
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            ps2.setInt(1, customerId);
            ps2.execute();
            ResultSet rs2 = ps2.getResultSet();

            rs2.next();
            int divisionID = rs2.getInt("Division_ID");
            ps3.setInt(1, divisionID);
            ps3.execute();
            ResultSet rs3 = ps3.getResultSet();

            rs3.next();
            int countryID = rs3.getInt("Country_ID");

            if (i == countryID)
                appointmentCount++;
        }
        return appointmentCount;



    }

    /**
     * when called gets appointment count by Type scheduled
     * @param type of appointment
     * @return appointmentCount
     * @throws SQLException
     */
    public static int appointmentsByType(String type) throws SQLException{
        //connection and prepared statement setup
        int appointmentCount = 0;
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM appointments WHERE Type = ?;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1,type);
        ps.execute();
        ResultSet rs = ps.getResultSet();

        //checks month if true, adds to count
        while (rs.next()) {
            appointmentCount++;
        }
        return appointmentCount;
    }

    /**
     * when called creates contact list
     * @return returns ObservableList of contacts
     * @throws SQLException
     */
    public static ObservableList<Contact> getContactList() throws SQLException {
        //connection and prepared statement set up
        contactList.clear();
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM contacts;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();

        //creates contact, and adds to list
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact contact = new Contact(contactID, name, email);
            contactList.add(contact);
        }
        return contactList;
    }

    /**
     * when called creates appointments for specified contact
     * @param contactID contactID to search for appointments
     * @return appointments ObservableList for display
     * @throws SQLException
     */
    public static ObservableList<Appointment> appointmentsByContacts(int contactID) throws SQLException{
        //connection and prepared statement set up
        appointmentByContactsList.clear();
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM appointments WHERE Contact_ID = ?;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1,contactID);
        ps.execute();
        ResultSet rs = ps.getResultSet();

        //creates appointment and adds to list for display
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            String contactName = getContactName(contactId);


            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            ZoneId gmtZoneId = ZoneId.of("UTC");
            LocalDateTime startDT = start.toLocalDateTime();
            LocalDateTime endDT = end.toLocalDateTime();

            ZonedDateTime startZDT = ZonedDateTime.of(startDT,gmtZoneId);
            ZonedDateTime startZDTtoGMT =startZDT.withZoneSameInstant(localZoneId);
            LocalDateTime startDTinGMT = LocalDateTime.of(startZDTtoGMT.toLocalDate(),startZDTtoGMT.toLocalTime());
            String startDTs = startDTinGMT.format(formatter);


            ZonedDateTime endZDT = ZonedDateTime.of(endDT,gmtZoneId);
            ZonedDateTime endZDTtoGMT =endZDT.withZoneSameInstant(localZoneId);
            LocalDateTime endDTinGMT = LocalDateTime.of(endZDTtoGMT.toLocalDate(),endZDTtoGMT.toLocalTime());
            String endDTs = endDTinGMT.format(formatter);


            Appointment appointment = new Appointment(appointmentId, title, description, location, type, startDTs, endDTs, customerId, userId, contactId, contactName);
            appointmentByContactsList.add(appointment);
        }
        return appointmentByContactsList;
    }

    /**
     * when called checks appointment time to see overlap
     * @param startTime appointmentTime to check for overlap
     * @param endTime
     * @return true if there is no overlap, false if appointment already scheduled
     * @throws SQLException
     */
    public static boolean errorCheck(LocalDateTime startTime, LocalDateTime endTime, int appointmentID,int customerId) throws SQLException {

        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM appointments WHERE Customer_ID = ?";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1,customerId);
        ps.execute();

        ResultSet rs = ps.getResultSet();
        
            while (rs.next()) {
                LocalDateTime startDT = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDT = rs.getTimestamp("End").toLocalDateTime();
                int appID = rs.getInt("Appointment_ID");

                if (appointmentID != appID) {
                    //checks if any appointment times overlaps, if so error message is sent
                    if (startTime.isAfter(startDT) && startTime.isBefore(endDT)
                            || endTime.isAfter(startDT) && endTime.isBefore(endDT)
                            || startTime.equals(startDT)
                            || startTime.isBefore(startDT) && endTime.isAfter(endDT)
                            || startTime.isBefore(startDT) && endTime.equals(endDT)) {
                        return false;
                    } else if (endTime.isEqual(startDT)) {
                        return true;
                    }
                }
            }

        return true;
    }

    public static int getAppointmentByMonthAndType(String month, String type) throws SQLException{
        //connection and prepared statement setup
        int appointmentCount = 0;
        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM appointments WHERE Type = ?;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1,type);

        ps.execute();
        ResultSet rs = ps.getResultSet();

        //checks month if true, adds to count
        while (rs.next()) {
            Timestamp start = rs.getTimestamp("Start");
            LocalDateTime startDT = start.toLocalDateTime();
            if(month.equals(startDT.getMonth().toString())){
                appointmentCount++;
            }
        }
        return appointmentCount;

    }

    /**
     * when called checks for any upcoming appointments
     * @param x passes user id to check specified user appointments
     * @return appointment if found or null
     * @throws SQLException
     */
    public static Appointment getUpcomingAppointments(int x) throws SQLException{

        //getting localtime and changing to UTC zone
        LocalDateTime localDT = LocalDateTime.now();
        ZoneId gmtZoneId = ZoneId.of("UTC");
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime localZDT = ZonedDateTime.of(localDT,localZoneId);
        ZonedDateTime localZDTtoGMT = localZDT.withZoneSameInstant(gmtZoneId);
        LocalDateTime localGMTDT = localZDTtoGMT.toLocalDateTime();

        Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT * FROM appointments;";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();


        while(rs.next()){
            Timestamp start = rs.getTimestamp("Start");
            int userId = rs.getInt("User_ID");
            LocalDateTime startLDT = start.toLocalDateTime();

            //checks if within 15 minutes of current time, create and return appointment
            if(startLDT.isAfter(localGMTDT) && startLDT.isBefore(localGMTDT.plusMinutes(15)) && x == userId) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp end = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = getContactName(contactId);

                LocalDateTime startDT = start.toLocalDateTime();
                LocalDateTime endDT = end.toLocalDateTime();

                //formatting time to HH:mm a
                ZonedDateTime startZDT = ZonedDateTime.of(startDT,gmtZoneId);
                ZonedDateTime startZDTtoGMT =startZDT.withZoneSameInstant(localZoneId);
                LocalDateTime startDTinGMT = LocalDateTime.of(startZDTtoGMT.toLocalDate(),startZDTtoGMT.toLocalTime());
                String startDTs = startDTinGMT.format(formatter);

                //formatting time to HH:mm a
                ZonedDateTime endZDT = ZonedDateTime.of(endDT,gmtZoneId);
                ZonedDateTime endZDTtoGMT =endZDT.withZoneSameInstant(localZoneId);
                LocalDateTime endDTinGMT = LocalDateTime.of(endZDTtoGMT.toLocalDate(),endZDTtoGMT.toLocalTime());
                String endDTs = endDTinGMT.format(formatter);

                return new Appointment(appointmentId, title, description, location, type, startDTs, endDTs, customerId, userId, contactId, contactName);
            }
        }
        return null;
    }

}
