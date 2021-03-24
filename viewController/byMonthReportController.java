package viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.DBAppointment;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * this class controls byMonthReports scene
 */
public class byMonthReportController implements Initializable {

    //variable declarations
    @FXML private Label monthTypeLabel;
    @FXML private Label count;
    @FXML private ComboBox<String> Month;
    @FXML private ComboBox<String> Type;

    ObservableList<String> monthList = FXCollections.observableArrayList("January","February","March", "April", "May", "June","July", "August","September", "October","November","December");
    ObservableList<String> typeList = FXCollections.observableArrayList("De-Briefing","Planning Session","Team Meeting","Material Review","Performance Review");

    static MessageInterface message = (title, header, content) -> {
            Alert alertNothing = new Alert(Alert.AlertType.INFORMATION);
            alertNothing.setTitle(title);
            alertNothing.setHeaderText(header);
            alertNothing.setContentText(content);
            alertNothing.showAndWait();
            return true;
    };

    /**
     * displays the appointment count for selected month and type
     * @param event button press
     * @throws SQLException
     */
    @FXML private void getAppointmentCount(ActionEvent event) throws SQLException {
        if(errorCheck(Month) && errorCheck(Type)) {
            monthTypeLabel.setText(Month.getValue() + " " + Type.getValue() + " appointments:");
            String selectedMonth = Month.getValue().toUpperCase();
            System.out.println(selectedMonth);
            String selectedType = Type.getValue();
            String appointmentCount = Integer.toString(DBAppointment.getAppointmentByMonthAndType(selectedMonth, selectedType));
            count.setText(appointmentCount);
        }

    }

    /**
     * checks for any missing combo box values and displays error
     * @param comboBox
     * @return true/false
     */
    public static boolean errorCheck(ComboBox comboBox){
        if(comboBox.getSelectionModel().isEmpty()){
            String content = comboBox.getId() + " is missing";
            message.message("Error","Missing value",content);
            return false;
        }
        return true;
    }


    /**
     * closes window
     * @param event when close button pushed, closes window
     */
    @FXML private void closeButtonPushed(ActionEvent event){
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

    /**
     * sets labels to show appointmentCount by month
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Month.setItems((monthList));
        Type.setItems(typeList);
    }
}
