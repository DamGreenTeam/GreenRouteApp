package com.sanvalero.greenrouteapp.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Creado por @ author: Pedro Orós
 * el 22/11/2020
 */

public class AlertUtils {

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    public static void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    public static void showConfirmation(String title) {
        Alert confirmation = new Alert((Alert.AlertType.CONFIRMATION));
        confirmation.setTitle(title);
        confirmation.setContentText("¿Are you sure?");
        Optional<ButtonType> response = confirmation.showAndWait();
        if (response.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
            return;
    }
}
