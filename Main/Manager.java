package Main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Manager, starts application, opens connection to DB
 */
public class Manager extends Application {


    /**
     * starts the java application
     */
    public static void main(String[] args) throws SQLException {

        Connection conn = DBConnection.startConnection(); //connections

        launch(args);
        DBConnection.closeConnection(); //closing database

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Load the FXML File
        Parent root = FXMLLoader.load(getClass().getResource("/viewController/logIn.fxml"));

        //Build the Scene Graph

        Scene scene = new Scene(root);

        //Display our window using the scene graph
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}