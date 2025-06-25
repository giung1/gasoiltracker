package com.gasoiltracker.controller;

import com.gasoiltracker.model.GasStation;
import com.gasoiltracker.utils.Alerts;
import com.gasoiltracker.utils.FileObjectsLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;


public class GasStationController {

    @FXML
    private Button btnAddEstacion;

    @FXML
    private TextField intLitrosActEstacion;

    @FXML
    private TextField txtLocalidadEstacion;

    @FXML
    private TextField txtNombreEstacion;

    @FXML
    void agregarEstacionDeServ(ActionEvent event) throws Exception {
        String nombre = txtNombreEstacion.getText();
        String localidad = txtLocalidadEstacion.getText();
        String litrosTxt = intLitrosActEstacion.getText();
        int litros = 0;
        if (!litrosTxt.isEmpty()){
            litros = Integer.parseInt(litrosTxt); 
        }

        GasStation newGasStation = new GasStation(nombre, localidad, litros);

        String fileName = nombre+"-"+localidad;
        FileObjectsLoader.objToFile(newGasStation,fileName);

        Alerts.newInfoAlert("Agregado correctamente", "La estacion de servicio fue agregada correctamente");
        Stage stage = (Stage) btnAddEstacion.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize(){
        intLitrosActEstacion.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            } else {
                return null; 
            }
        }));
    }

}
