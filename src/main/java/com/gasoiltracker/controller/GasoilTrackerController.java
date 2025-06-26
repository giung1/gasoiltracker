package com.gasoiltracker.controller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


import com.gasoiltracker.model.GasStation;
import com.gasoiltracker.model.GasStationBuy;
import com.gasoiltracker.model.GasStationFueling;
import com.gasoiltracker.utils.Alerts;
import com.gasoiltracker.utils.FileObjectsLoader;
import com.gasoiltracker.utils.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class GasoilTrackerController {

    private GasStation currentGasStation;
    private String comprobanteName = "Sin especificar";
    private static Stage stage = new Stage();

    @FXML
    private Label gasStationTitle;

    @FXML
    private Label gasStationLocality;

    @FXML
    private Label gasStationLitros;

    @FXML
    private DatePicker localDateDate;

    @FXML
    private Button buttonCompr;
    
    //  Cargas table
    @FXML
    private TableView<GasStationFueling> tblCargas;
    
    @FXML
    private TableColumn<GasStationFueling, String> colChoferCarga;

    @FXML
    private TableColumn<GasStationFueling, String> colCompCarga;

    @FXML
    private TableColumn<GasStationFueling, LocalDate> colFechaCarga;
    
    @FXML
    private TableColumn<GasStationFueling, Integer> colLitrosCarga;

    //  Compras table
    @FXML
    private TableColumn<GasStationBuy, String> colCompCompra;

    @FXML
    private TableColumn<GasStationBuy, LocalDate> colFechaCompra;

    @FXML
    private TableColumn<GasStationBuy, Integer> colLitrosCompra;
    
    @FXML
    private TableView<GasStationBuy> tblCompras;
    
    @FXML
    private TextField intLiters;

    @FXML
    private TextField txtChofer;

    @FXML
    void agregarCarga(ActionEvent event) {
        GasStationFueling carga = new GasStationFueling();
        String litersString = intLiters.getText();
        String chofeString = txtChofer.getText();
        LocalDate tempDate = localDateDate.getValue();
        if (!litersString.isEmpty() && !chofeString.isEmpty() && tempDate != null){
            int liters = Integer.parseInt(litersString);
            if (this.currentGasStation.getQuantity() < liters){
                showModal("Los litros de la carga son mayores a los que tiene la cuenta");
                return;
            }
            carga.setDate(tempDate);
            carga.setQuantity(liters);
            carga.setReceipt(comprobanteName);
            carga.setDriver(chofeString);
            currentGasStation.addFueling(carga);
            updateLitersLabel();
            updateTableCargas();
        } else {
            if(litersString.isEmpty()){
                showModal("Litros sin especificar");
            } else 
                if (chofeString.isEmpty()){
                    showModal("Chofer sin especificar");
                }
                else {
                showModal("Fecha sin especificar");
            }
        }
    }

    private void updateLitersLabel() {
        gasStationLitros.setText(String.valueOf(currentGasStation.getQuantity()));
    }

    private void showModal(String message){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setResizable(false);
        ButtonType okButton = new ButtonType("Aceptar");
        dialog.setTitle("Cuidado!");
        dialog.setHeaderText(message);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.getDialogPane().getButtonTypes().add(okButton);
        dialog.initOwner(getStage());
        dialog.showAndWait();
    }

    private void updateTableCargas() {
        tblCargas.getItems().clear();
        tblCargas.getItems().addAll(currentGasStation.getFuelingList());
        tblCargas.refresh();
        clearFields();
    }

    @FXML
    void agregarCompra(ActionEvent event) {        
        GasStationBuy compra = new GasStationBuy();
        String litersString = intLiters.getText();
        LocalDate tempDate = localDateDate.getValue();
        if (!litersString.isEmpty() && tempDate != null){
            int liters = Integer.parseInt(litersString);
            compra.setDate(tempDate);
            compra.setQuantity(liters);
            compra.setReceipt(comprobanteName);
            currentGasStation.addBuy(compra);
            updateTableCompras();
            updateLitersLabel();
        } else {
            if (litersString.isEmpty()){
                showModal("Litros sin especificar");
            } else {
                showModal("Fecha sin especificar");
            }
        }
    }

    @FXML
    void selectCompr(ActionEvent event) {
        FileChooser fChooser = new FileChooser();
        ExtensionFilter pdfExt = new ExtensionFilter("PDF", "*.pdf");
        ExtensionFilter allExt = new ExtensionFilter("Todos los archivos", "*.*");
        fChooser.setTitle("Seleccionar comprobante");
        fChooser.setInitialDirectory( new File(System.getProperty("user.dir")));
        fChooser.getExtensionFilters().addAll(pdfExt,allExt);
        File comprobante = fChooser.showOpenDialog(new Stage());

        if (comprobante != null){
            this.comprobanteName = comprobante.getName();
            buttonCompr.setText(this.comprobanteName);
        }
        
    }

    @FXML
    void initialize(){
        colChoferCarga.setCellValueFactory(new PropertyValueFactory<>("driver"));
        colCompCarga.setCellValueFactory(new PropertyValueFactory<>("receipt"));
        colFechaCarga.setCellValueFactory(new PropertyValueFactory<>("date"));
        colLitrosCarga.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        colCompCompra.setCellValueFactory(new PropertyValueFactory<>("receipt"));
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<>("date"));
        colLitrosCompra.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        intLiters.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            } else {
                return null; 
            }
        }));
    }

    @FXML
    void verListaEstDeServ(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.ROOTVIEW));
        try {
            Parent root = loader.load();
            RootController controller = loader.getController();
            String currentGSFile = currentGasStation.getName() + "-" + currentGasStation.getLocality();
            FileObjectsLoader.objToFile(currentGasStation, currentGSFile);
            controller.refreshStationList();
            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void Eliminar(ActionEvent event) {
        GasStationFueling carga = tblCargas.getSelectionModel().getSelectedItem();
        GasStationBuy compra = tblCompras.getSelectionModel().getSelectedItem();
        if (carga != null){
            currentGasStation.removeFueling(carga);
            updateTableCargas();
        } else if (compra != null){
            currentGasStation.removeBuy(compra);
            updateTableCompras();
        } else {
            Alerts.newErrorAlert("Error", "Seleccione que desea eliminar");
        }
    }


    public void initData(){
        updateTableCargas();
        updateTableCompras();
        gasStationTitle.setText(this.currentGasStation.getName());
        gasStationLocality.setText(this.currentGasStation.getLocality());
        gasStationLitros.setText(String.valueOf(this.currentGasStation.getQuantity())+" litros");
    }

    private void updateTableCompras() {
        tblCompras.getItems().clear();
        tblCompras.getItems().addAll(currentGasStation.getBuyList());
        tblCompras.refresh();
        clearFields();
    }

    public void setCurrentGasStation(GasStation newGasStation){
        this.currentGasStation = newGasStation;
    }

    private void clearFields(){
        intLiters.clear();
        txtChofer.clear();
        buttonCompr.setText("Seleccionar comprobante");
        this.comprobanteName = "Sin especificar";
    }

    public GasStation getCurrentGasStation(){
        return this.currentGasStation;
    }

    public static void setStage(Stage aStage){
        stage = aStage;
    }

    public static Stage getStage(){
        return stage;
    }

}