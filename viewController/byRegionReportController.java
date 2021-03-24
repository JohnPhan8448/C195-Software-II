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
 * this controls byRegionReport scene
 */
public class byRegionReportController implements Initializable {

    //variable declarations
    @FXML private Label us;
    @FXML private Label uk;
    @FXML private Label canada;

    /**
     * when called closes Region report window
     * @param event
     */
    @FXML private void closeButtonPushed(ActionEvent event){
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

    /**
     * sets labels to display appointment numbers by region
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //runs method to get count of reports per region
            us.setText(Integer.toString(DBAppointment.appointmentsByRegion(1)));
            uk.setText(Integer.toString(DBAppointment.appointmentsByRegion(2)));
            canada.setText(Integer.toString(DBAppointment.appointmentsByRegion(3)));
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

}
