package viewController;

import Model.Appointment;
import Model.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.DBAppointment;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * this class controls the contactSchedules scene
 */
public class contactScheduleController {

    //variable declarations
    @FXML private TableView<Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, Timestamp> startColumn;
    @FXML private TableColumn<Appointment, Timestamp> endColumn;
    @FXML private TableColumn<Appointment, Integer> customerIdColumn;


    /**
     * initializes the scene passes contact from previous scene
     * @param contact selected contact from previous scene table
     * @throws SQLException
     */
    public void initData(Contact contact) throws SQLException {
        appointmentTableView.setItems(DBAppointment.appointmentsByContacts(contact.getContactID()));
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * closes contactSchedule scene
     * @param event
     */
    @FXML
    private void backButtonPushed(ActionEvent event)
    {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }
}
