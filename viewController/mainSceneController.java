package viewController;

import Model.Appointment;
import Model.Customer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.DBAppointment;
import utils.DBCustomers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * this Class controls the main Scene for customers and appointments
 * LAMBDA EXPRESSION message to display messages: sets Title Header Content of message for display
 * @author John Phan
 */
public class mainSceneController implements Initializable {
    //variables for Customer Table
    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, Integer> customerIDColumn;
    @FXML private TableColumn<Customer, String> customerNameColumn;
    @FXML private TableColumn<Customer, String> phoneNumberColumn;
    @FXML private TableColumn<Customer, String> addressColumn;
    @FXML private TableColumn<Customer, String> stateColumn;
    @FXML private TableColumn<Customer, String> postalCodeColumn;
    @FXML private TableColumn<Customer, String> countryNameColumn;

    //variables for all appointments table.
    @FXML private TableView<Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> locationColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, String> startColumn;
    @FXML private TableColumn<Appointment, String> endColumn;
    @FXML private TableColumn<Appointment, Integer> customerIdColumn;
    @FXML private TableColumn<Appointment, String> contactIdColumn;

    //variables for weekly appointments table.
    @FXML private TableView<Appointment> appointmentWeeklyTableView;
    @FXML private TableColumn<Appointment, Integer> appointmentWeeklyIdColumn;
    @FXML private TableColumn<Appointment, String> titleWeeklyColumn;
    @FXML private TableColumn<Appointment, String> descriptionWeeklyColumn;
    @FXML private TableColumn<Appointment, String> locationWeeklyColumn;
    @FXML private TableColumn<Appointment, String> typeWeeklyColumn;
    @FXML private TableColumn<Appointment, String> startWeeklyColumn;
    @FXML private TableColumn<Appointment, String> endWeeklyColumn;
    @FXML private TableColumn<Appointment, Integer> customerIdWeeklyColumn;
    @FXML private TableColumn<Appointment, String> contactIdWeeklyColumn;

    //variables for monthly appointments table.
    @FXML private TableView<Appointment> appointmentMonthTableView;
    @FXML private TableColumn<Appointment, Integer> appointmentMonthIdColumn;
    @FXML private TableColumn<Appointment, String> titleMonthColumn;
    @FXML private TableColumn<Appointment, String> descriptionMonthColumn;
    @FXML private TableColumn<Appointment, String> locationMonthColumn;
    @FXML private TableColumn<Appointment, String> typeMonthColumn;
    @FXML private TableColumn<Appointment, String> startMonthColumn;
    @FXML private TableColumn<Appointment, String> endMonthColumn;
    @FXML private TableColumn<Appointment, Integer> customerIdMonthColumn;
    @FXML private TableColumn<Appointment, String> contactIdMonthColumn;

    //variables for tabs
    @FXML TabPane appointmentsTabPane = new TabPane();
    @FXML Tab allAppointments = new Tab();
    @FXML Tab weeklyAppointments = new Tab();
    @FXML Tab monthlyAppointments = new Tab();


    //LAMBDA EXPRESSION for messages takes in title,header, and content and creates a pop up message based on title, to display
    MessageInterface message = (title, header, content) -> {
        if(title.equals("Error") || title.equals("Upcoming Appointment")) {
            Alert alertNothing = new Alert(Alert.AlertType.INFORMATION);
            alertNothing.setTitle(title);
            alertNothing.setHeaderText(header);
            alertNothing.setContentText(content);
            alertNothing.showAndWait();
            return true;
        }
        else if (title.equals("Delete"))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                return true;
            }
            else return false;
        }
        return false;
    };


    /**
     * when called change to add Customer scene
     * @param event to change scenes to newCustomer.fxml
     * @throws IOException
     */
    @FXML private void addCustomerButtonPushed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newCustomer.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * when called change to add Update Customer scene
     * LAMBDA EXPRESSION message used to display messages: sets Title Header Content of message for display
     * @param event to change scenes to updateCustomer.fxml
     * @throws IOException
     */
    @FXML private void updateCustomerButtonPushed(ActionEvent event) throws IOException {
        try {
            //gets selected customer
            Customer customer = customerTableView.getSelectionModel().getSelectedItem();

            //error if null
            if (customer == null)
                message.message("Error","Customer not selected","Please select a customer");
            else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("updateCustomer.fxml"));
                Parent modifyCustomer = loader.load();
                Scene modifyProductScene = new Scene(modifyCustomer);

                updateCustomerController controller = loader.getController();
                controller.initData(customer);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(modifyProductScene);
                stage.show();
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    /**
     * When called used to delete selected customer
     * LAMBDA EXPRESSION message used to display messages: sets Title Header Content of message for display
     * @param event to delete selected customer
     * @throws SQLException
     */
    @FXML private void deleteCustomerButtonPushed(ActionEvent event) throws SQLException{

        //get selected customer
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();

        //error if nothing selected
        if (customer == null)
            message.message("Error","Customer not selected","Please select a customer");
        else {
            //checks to see if customer has appointments before deletion
            if(DBAppointment.appointmentCheck(customer.getId()))
                message.message("Error","Customer has appointments","Please delete customer's appointments first");
            else {
                boolean x = message.message("Delete","Confirmation","Are you sure you want to remove customer?");
                if (x) {
                    //updates deletes customer and updates customer table
                    DBCustomers.deleteCustomer(customer.getId());
                    DBCustomers.getCustomerList();
                }
            }
        }
    }

    /**
     * when called change to new appointment scene
     * @param event to change scenes to newAppointment.fxml
     * @throws IOException WHEN ERROR CALLED
     */
    @FXML private void newAppointmentButtonPushed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newAppointment.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    /**
     * when called change to update appointment scene
     * LAMBDA EXPRESSION message used to display messages: sets Title Header Content of message for display
     * @param event to change scenes to updateAppointment.fxml
     * @throws IOException WHEN ERROR CALLED
     */
    @FXML private void updateAppointmentButtonPushed(ActionEvent event) throws IOException {
        try {
            //checks to see which tab is selected and gets selected appointment
            Appointment appointment = null;
            if (allAppointments.isSelected())
                appointment = appointmentTableView.getSelectionModel().getSelectedItem();
            else if(weeklyAppointments.isSelected())
                appointment = appointmentWeeklyTableView.getSelectionModel().getSelectedItem();
            else if(monthlyAppointments.isSelected())
                appointment = appointmentMonthTableView.getSelectionModel().getSelectedItem();

            //if nothing selected error
            if (appointment == null) {
                message.message("Error","Appointment not selected", "Please select an appointment");
            }
            else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("updateAppointment.fxml"));
                Parent modifyAppointment = loader.load();
                Scene modifyAppointmentScene = new Scene(modifyAppointment);

                updateAppointmentController controller = loader.getController();
                controller.initData(appointment);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(modifyAppointmentScene);
                stage.show();
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }

    }

    /**
     *  when called used to delete selected appointment.
     * LAMBDA EXPRESSION message used to display messages: sets Title Header Content of message for display
     * @param event to delete selected appointment.
     * @throws SQLException
     */
    @FXML private void deleteAppointmentButtonPushed(ActionEvent event) throws SQLException{
        //get selected appointment
        Appointment appointment = null;

        //checks which tab is selected and to pull selected appointment.
        if (allAppointments.isSelected())
            appointment = appointmentTableView.getSelectionModel().getSelectedItem();
        else if(weeklyAppointments.isSelected())
            appointment = appointmentWeeklyTableView.getSelectionModel().getSelectedItem();
        else if(monthlyAppointments.isSelected())
            appointment = appointmentMonthTableView.getSelectionModel().getSelectedItem();

        //error if nothing selected
        if(appointment == null)
            message.message("Error","Appointment not selected", "Please select an appointment");
        else {

            String appointmentDelete = "Delete AppointmentID: " + appointment.getId() + " Type: " + appointment.getType() + "?";
            boolean x = message.message("Delete","Confirmation",appointmentDelete);
            if (x) {
                //updates appointments tables
                DBAppointment.deleteAppointment(appointment.getId());
                DBAppointment.getAppointmentList("all");
                DBAppointment.getAppointmentList("weekly");
                DBAppointment.getAppointmentList("monthly");
            }
        }
    }

    /**
     * when called change to reports scene
     * @param event to change scenes to reports.fxml
     * @throws IOException WHEN ERROR CALLED
     */
    @FXML private void reportsButtonPushed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("reports.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML private void closeButtonPushed(ActionEvent event){
        Platform.exit();
    }

    /**
     * initializes information at the start.  displays for customer/appointments
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        try {

            //setting variables for customer table
            customerTableView.setItems(DBCustomers.getCustomerList());
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
            postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            countryNameColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

            //setting variables for all appointments table
            appointmentTableView.setItems(DBAppointment.getAppointmentList("all"));
            appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));

            //setting variables for weekly appointments table
            appointmentWeeklyTableView.setItems(DBAppointment.getAppointmentList("weekly"));
            appointmentWeeklyIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            titleWeeklyColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionWeeklyColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationWeeklyColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeWeeklyColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startWeeklyColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            endWeeklyColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIdWeeklyColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            contactIdWeeklyColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));

            //setting variables for monthly appointments table
            appointmentMonthTableView.setItems(DBAppointment.getAppointmentList("monthly"));
            appointmentMonthIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            titleMonthColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionMonthColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationMonthColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeMonthColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startMonthColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            endMonthColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIdMonthColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            contactIdMonthColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
}
