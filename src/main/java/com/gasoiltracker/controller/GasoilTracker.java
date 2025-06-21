package com.gasoiltracker.controller;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import com.gasoiltracker.model.GasStation;
import com.gasoiltracker.model.GasStationBuy;
import com.gasoiltracker.model.GasStationFueling;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class GasoilTracker {

    //private static final Set<KeyCode> VALID_KEYS = Set.of(KeyCode.BACK_SPACE,KeyCode.DELETE,KeyCode.PASTE,KeyCode.ENTER);



    @FXML
    private Button buttonCompr;

    @FXML
    private TableColumn<?, ?> colChoferCarga;

    @FXML
    private TableColumn<?, ?> colCompCarga;

    @FXML
    private TableColumn<?, ?> colCompCompra;

    @FXML
    private TableColumn<?, ?> colFechaCarga;

    @FXML
    private TableColumn<?, ?> colFechaCompra;

    @FXML
    private TableColumn<?, ?> colLitroCompra;

    @FXML
    private TableColumn<?, ?> colLitrosCarga;

    @FXML
    private TextField intLiters;

    @FXML
    private TableView<?> tblCargas;

    @FXML
    private TextField txtChofer;

    @FXML
    void agregarCarga(ActionEvent event) {

    }

    @FXML
    void agregarCompra(ActionEvent event) {

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
            buttonCompr.setText(comprobante.getName());
        }
        
    }

    @FXML
    void validateKeyPressed(KeyEvent event) {
    char character = event.getCharacter().charAt(0);
    if (!Character.isDigit(character)) {
            
    }
}

    @FXML
    void initialize(){

    }

    private Scanner scanner;
    private ArrayList<GasStation> gasStations = new ArrayList<>();
    private GasStation currentGasStation;

    public void addGasStation(String name, String locality, int quantity) {
        GasStation gasStation = new GasStation(name, locality, quantity);
        gasStations.add(gasStation);
    }

    public void setCurrentGasStation() {
        scanner = new Scanner(System.in);        
        int index = 0;
        System.out.println("Existing gas stations:");
        for (GasStation gasStation : gasStations) {
            index++;
            String current = gasStation.getName();
            System.out.println(index +") " + current);
        }
        System.out.print("Select a gas station by number: ");
        Integer selection = null;
        while (selection == null || selection < 1 || selection > gasStations.size()) {
            try {
                selection = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 1 and " + gasStations.size() + ".");
                scanner.next(); // Clear the invalid input
                selection = null; // Reset selection to prompt again
            }
            if (selection != null && (selection < 1 || selection > gasStations.size())) {
                System.out.println("Invalid selection. Please select a number between 1 and " + gasStations.size() + ".");
            }
        }
        currentGasStation = gasStations.get(selection - 1);
        System.out.println("Current gas station set to: " + currentGasStation.getName());
    }

    public GasStation getCurrentGasStation() {
        return currentGasStation;
    }

    public void addFueling(){
        scanner = new Scanner(System.in);
        LocalDate date = null;
        if (currentGasStation != null) {
            boolean choosingDate = true;
            System.out.println("use current date? (y/n)");
            String useCurrentDate = scanner.nextLine().trim().toLowerCase();
            while (useCurrentDate.isEmpty() || (!useCurrentDate.equals("y") && !useCurrentDate.equals("n"))) {
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
                useCurrentDate = scanner.nextLine().trim().toLowerCase();
            }
            if (useCurrentDate.equals("y")) {
                choosingDate = false;
            } else {
                choosingDate = true;
            }
            if (choosingDate) {
                System.out.print("Enter the date (yyyy-mm-dd): ");
                date = null;
                while (date == null) {
                    try {
                        String dateString = scanner.nextLine();
                        date = LocalDate.parse(dateString);

                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                    }
                }
            }
            System.out.print("Enter the amount of fuel (in liters): ");
            Integer amount = null;
            while (amount == null || amount <= 0) {
                try {
                    amount = scanner.nextInt();
                    if (amount <= 0) {
                        System.out.println("Amount must be greater than zero.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number for the amount.");
                    scanner.next(); // Clear the invalid input
                    amount = null; // Reset amount to prompt again
                }
            }
            scanner.nextLine();  // Consume the newline character
            System.out.print("Enter the driver name: ");
            String driverName = scanner.nextLine();
            System.out.print("Enter the receipt path: ");
            String receiptPath = scanner.nextLine();
            if (currentGasStation.getQuantity() < amount) {
                System.out.println("Not enough fuel available at the gas station. Current quantity: " + currentGasStation.getQuantity() + "L");
                return;
            }
            currentGasStation.addFueling(new GasStationFueling((choosingDate ? date : LocalDate.now()), driverName, amount, receiptPath));
            currentGasStation.setQuantity(currentGasStation.getQuantity() - amount);
            System.out.println("Fueling added for station: " + currentGasStation.getName());
        } else {
            System.out.println("No gas station selected.");
        }
    }

    public ArrayList<GasStationFueling> getFuelingList() {
        if (currentGasStation != null) {
            return currentGasStation.getFuelingList();
        } else {
            System.out.println("No gas station selected.");
            return new ArrayList<>();
        }
    }

    public void showFuelingList() {
        ArrayList<GasStationFueling> fuelingList = getFuelingList();
        if (fuelingList.isEmpty()) {
            System.out.println("No fuelings recorded for the current gas station.");
        } else {
            System.out.println("Fueling List for " + currentGasStation.getName() + ":");
            for (GasStationFueling fueling : fuelingList) {
                System.out.println("Date: " + fueling.getDate() + ", Driver: " + fueling.getDriver() +
                                   ", Quantity: " + fueling.getQuantity() + "L, Receipt: " + fueling.getReceipt());
            }
        }
    }

    public void addBuy() {
        scanner = new Scanner(System.in);
        if (currentGasStation != null) {
            LocalDate date = null;
            boolean choosingDate = true;
            System.out.println("use current date? (y/n)");
            String useCurrentDate = scanner.nextLine().trim().toLowerCase();
            while (useCurrentDate.isEmpty() || (!useCurrentDate.equals("y") && !useCurrentDate.equals("n"))) {
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
                useCurrentDate = scanner.nextLine().trim().toLowerCase();
            }
            if (useCurrentDate.equals("y")) {
                choosingDate = false;
            } else {
                choosingDate = true;
            }
            if (choosingDate) {
                System.out.print("Enter the date (yyyy-mm-dd): ");
                date = null;
                while (date == null) {
                    try {
                        String dateString = scanner.nextLine();
                        date = LocalDate.parse(dateString);

                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                    }
                }
            }
            System.out.print("Enter the quantity bought (in liters): ");
            Integer quantity = null;
            while (quantity == null || quantity <= 0) {
                try {
                    quantity = scanner.nextInt();
                    if (quantity <= 0) {
                        System.out.println("Quantity must be greater than zero.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number for the quantity.");
                    scanner.next(); // Clear the invalid input
                    quantity = null; // Reset quantity to prompt again
                }
            }
            System.out.print("Enter the receipt path: ");
            String receipt = scanner.next();
            currentGasStation.addBuy(new GasStationBuy((choosingDate ? date : LocalDate.now()), quantity, receipt));
            currentGasStation.setQuantity(currentGasStation.getQuantity() + quantity);
            System.out.println("Buy added for station: " + currentGasStation.getName());
        } else {
            System.out.println("No gas station selected.");
        }
    }

    public ArrayList<GasStationBuy> getBuyList() {
        if (currentGasStation != null) {
            return currentGasStation.getBuyList();
        } else {
            System.out.println("No gas station selected.");
            return new ArrayList<>();
        }
    }

    public void showBuyList() {
        ArrayList<GasStationBuy> buyList = getBuyList();
        if (buyList.isEmpty()) {
            System.out.println("No buys recorded for the current gas station.");
        } else {
            System.out.println("Buy List for " + currentGasStation.getName() + ":");
            for (GasStationBuy buy : buyList) {
                System.out.println("Date: " + buy.getDate() + ", Quantity: " + buy.getQuantity() +
                                   "L, Receipt: " + buy.getReceipt());
            }
        }
    }

}