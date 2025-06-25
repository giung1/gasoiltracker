package com.gasoiltracker.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.gasoiltracker.model.GasStation;
import com.gasoiltracker.utils.Paths;
import com.gasoiltracker.utils.FileObjectsLoader;
import com.gasoiltracker.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RootController {

    String currentGasStationFile;
    GasStation currentGasStation;
    ArrayList<GasStation> gasStationList = new ArrayList<>();

    @FXML
    private Button addGasStation;

    @FXML
    private Button selectGasStation;

    @FXML
    private TableColumn<GasStation, String> colGasStation;

    @FXML
    private TableColumn<GasStation, Integer> colLitros;

    @FXML
    private TableColumn<GasStation, String> colLocality;

    @FXML
    private TableView<GasStation> tblGasStations;

    @FXML
    void selectGasStation(ActionEvent event) {
        currentGasStation = tblGasStations.getSelectionModel().getSelectedItem();
        this.currentGasStationFile = currentGasStation.getName() + "-" + currentGasStation.getLocality();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.GASOILTRACKERVIEW));
        System.out.println(currentGasStation);
        if (currentGasStation == null) {
            Alerts.newErrorAlert("Error", "Debes seleccionar una estacion de servicio de la lista");
        } else {
            try {
                Parent root = loader.load();
                GasoilTrackerController controller = loader.getController();
                controller.setCurrentGasStation(currentGasStation);
                controller.initData();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                controller.setStage(stage);
                stage.centerOnScreen();
                stage.show();                
                stage.setOnCloseRequest(e -> {
                boolean result = Alerts.newConfirmAlert("Salir", "¿Esta seguro que desea salir?");
                if (!result) {
                    e.consume();
                } else {
                    FileObjectsLoader.objToFile(currentGasStation, currentGasStationFile );
                }
            });
    
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @FXML
    void addGasStation(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.GASSTATIONLISTVIEW));
        try {
            Parent root = loader.load();
            GasStationController controller =  loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            refreshStationList();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize(){
        colGasStation.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLocality.setCellValueFactory(new PropertyValueFactory<>("locality"));
        colLitros.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        refreshStationList();
    }

    public void refreshStationList() {
        File filesDirectory = new File(FileObjectsLoader.SUBPATH);
        String[] stationList = filesDirectory.list();
        if (stationList != null){
            tblGasStations.getItems().clear();
            GasStation newStation;
            for (String station : stationList){
                newStation =(GasStation) FileObjectsLoader.objFromFile(station);
                gasStationList.add(newStation);
                tblGasStations.getItems().add(newStation);
            }
        }
        tblGasStations.refresh();
    }

    public String getCurrentGasStationFile(){
        return this.currentGasStationFile; 
    }
}

