package com.gasoiltracker;

import java.util.Optional;

import com.gasoiltracker.controller.GasoilTrackerController;
import com.gasoiltracker.controller.RootController;
import com.gasoiltracker.model.GasStation;
import com.gasoiltracker.utils.Alerts;
import com.gasoiltracker.utils.Paths;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;


public class App extends Application {

    private RootController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.ROOTVIEW));
        Parent root = loader.load();
        
        controller = loader.getController();
        Scene scene = new Scene(root);
        
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
