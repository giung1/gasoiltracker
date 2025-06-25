package com.gasoiltracker.utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class Alerts {
    
    private static Alert alertError = new Alert(Alert.AlertType.ERROR);
    private static Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
    private static Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

    public static void newErrorAlert(String title, String content){
        alertError.setHeaderText(title);
        alertError.setContentText(content);
        alertError.showAndWait();
        alertError = new Alert(Alert.AlertType.ERROR);
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
    }
}
