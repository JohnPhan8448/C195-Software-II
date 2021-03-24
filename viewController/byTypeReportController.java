package viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.DBAppointment;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * this class controls the byTypeReport Scene
 */
public class byTypeReportController implements Initializable {

    //variable declarations
    @FXML private Label deBriefing;
    @FXML private Label planningSession;
    @FXML private Label teamMeeting;
    @FXML private Label materialReview;
    @FXML private Label performanceReview;

    /**
     * when called closes window
     * @param event when button pressed
     */
    @FXML
    private void closeButtonPushed(ActionEvent event){
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

    /**
     * sets text on labels to display number of appointments by type
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        try {
            deBriefing.setText(Integer.toString(DBAppointment.appointmentsByType("De-Briefing")));
            planningSession.setText(Integer.toString(DBAppointment.appointmentsByType("Planning Session")));
            teamMeeting.setText(Integer.toString(DBAppointment.appointmentsByType("Team Meeting")));
            materialReview.setText(Integer.toString(DBAppointment.appointmentsByType("Material Review")));
            performanceReview.setText(Integer.toString(DBAppointment.appointmentsByType("Performance Review")));

        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

}
