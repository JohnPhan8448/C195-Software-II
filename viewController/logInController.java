package viewController;

import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.DBAppointment;
import utils.DBUsers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

//TODO ADD TIME STAMP LABEL, LANGUAGES.

/**
 * this Class controls the log in screen
 * @author John Phan
 */
public class logInController implements Initializable {

    //variable declarations
    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Label locationLabel;
    @FXML private Label userName;
    @FXML private Label password;
    @FXML private Button Login;

    //used to write into txt file
    ResourceBundle rb1;
    String filename = "src/Files/login_activity.txt";
    FileWriter fWriter;
    PrintWriter outputFile;


    //lambda expression for messages
    MessageInterface message = (title, header, content) -> {
        Alert alertNothing = new Alert(Alert.AlertType.INFORMATION);
        alertNothing.setTitle(title);
        alertNothing.setHeaderText(header);
        alertNothing.setContentText(content);
        alertNothing.showAndWait();
        return true;
    };


    /**
     * when called runs login code calls DBusers for verification.
     * @param event to run log in code
     * @throws IOException
     */
    @FXML private void signInButtonPushed(ActionEvent event) throws IOException {

        String userName = userNameTextField.getText();
        String password = passwordField.getText();


        //checks for verification of user if valid will write to txt success, if fail will right to code fail
        Boolean verifyUser = DBUsers.loginVerification(userName, password);
        if (verifyUser)
        {
            outputFile.println("login attempt on user : " + userNameTextField.getText() + "    on " + LocalDateTime.now() + " attempt successful");
            outputFile.close();

            try {
                //message for upcoming appointments
                Appointment appointment = DBAppointment.getUpcomingAppointments(DBUsers.getUserID());
                if (!(appointment == null)) {
                    String header = "Appointment ID: " + appointment.getId();
                    String content = "Appointment Time " + appointment.getStart();
                    message.message("Upcoming Appointment on ", header, content);
                } else {
                    message.message("Upcoming Appointment", "no upcoming appointments", "no upcoming appointments");
                }
            }
            catch(SQLException e)
            {
                System.out.println(e);
            }

            Parent signInParent = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
            Scene signInScene = new Scene(signInParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(signInScene);
            window.show();
        }
        //error message in french fr language
        else if(Locale.getDefault().getLanguage().equals("fr")) {
            outputFile.println("login attempt on " + userNameTextField.getText() + "    on " + LocalDateTime.now() + " attempt unsuccessful");
            outputFile.close();
            message.message(rb1.getString("invalid"),rb1.getString("invalid"),rb1.getString("information"));

        }
        //error message english
        else {
            outputFile.println("login attempt on " + userNameTextField.getText() + "    on " + LocalDateTime.now() + " attempt unsuccessful");
            outputFile.close();
            message.message("Error","Invalid Login","Invalid Login Information");
        }
    }


    /**
     * initializes information at the start of login scene
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        String location  = ZoneId.systemDefault().toString();
        locationLabel.setText(location);
        rb1 = ResourceBundle.getBundle("viewController/Nat", Locale.getDefault());

        //checks if french
        if(Locale.getDefault().getLanguage().equals("fr")) {
            userName.setText(rb1.getString("username"));
            password.setText(rb1.getString("password"));
            Login.setText(rb1.getString("login"));
        }

        try {
            String filename = "src/Files/login_activity.txt";
            fWriter = new FileWriter(filename,true);
            outputFile = new PrintWriter(fWriter);
        }
        catch(IOException e) {
            System.out.println(e);
        }


    }

}
