package com.gasoiltracker.utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class Alerts {
    
    private static Alert alertError = new Alert(Alert.AlertType.ERROR);
    private static Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
    private static Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

    static {
        // Apply CSS styling to all alert dialogs
        try {
            String cssFile = Alerts.class.getResource("/com/gasoiltracker/styles.css").toExternalForm();
            alertError.getDialogPane().getStylesheets().add(cssFile);
            alertConfirm.getDialogPane().getStylesheets().add(cssFile);
            alertInfo.getDialogPane().getStylesheets().add(cssFile);
            
            alertError.getDialogPane().getStyleClass().add("dialog-pane");
            alertConfirm.getDialogPane().getStyleClass().add("dialog-pane");
            alertInfo.getDialogPane().getStyleClass().add("dialog-pane");
        } catch (Exception e) {
            // Fallback if CSS file is not found
            System.err.println("Could not load CSS for alerts: " + e.getMessage());
        }
    }

    public static void newErrorAlert(String title, String content){
        alertError.setHeaderText(title);
        alertError.setContentText(content);
        alertError.showAndWait();
        alertError = new Alert(Alert.AlertType.ERROR);
        
        // Reapply styling to new instance
        try {
            String cssFile = Alerts.class.getResource("/com/gasoiltracker/styles.css").toExternalForm();
            alertError.getDialogPane().getStylesheets().add(cssFile);
            alertError.getDialogPane().getStyleClass().add("dialog-pane");
        } catch (Exception e) {
            // Ignore if CSS can't be loaded
        }
    }
    
    /**
     * 
     * @param title
     * @param content
     * @return true if the confirmation is OK and false if windows is closed or cancelled
     */
    public static boolean newConfirmAlert(String title, String content){
        alertConfirm.setHeaderText(title);
        alertConfirm.setContentText(content);
        Optional<ButtonType> result = alertConfirm.showAndWait();
        alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        
        // Reapply styling to new instance
        try {
            String cssFile = Alerts.class.getResource("/com/gasoiltracker/styles.css").toExternalForm();
            alertConfirm.getDialogPane().getStylesheets().add(cssFile);
            alertConfirm.getDialogPane().getStyleClass().add("dialog-pane");
        } catch (Exception e) {
            // Ignore if CSS can't be loaded
        }
        
        if(result.isEmpty() || result.get() != ButtonType.OK){
            return false;
        } else {
            return true;
        }
    }

    public static void newInfoAlert(String title, String content){
        alertInfo.setHeaderText(title);
        alertInfo.setContentText(content);
        alertInfo.showAndWait();
        alertInfo = new Alert(Alert.AlertType.INFORMATION);
        
        // Reapply styling to new instance
        try {
            String cssFile = Alerts.class.getResource("/com/gasoiltracker/styles.css").toExternalForm();
            alertInfo.getDialogPane().getStylesheets().add(cssFile);
            alertInfo.getDialogPane().getStyleClass().add("dialog-pane");
        } catch (Exception e) {
            // Ignore if CSS can't be loaded
        }
    }
}
