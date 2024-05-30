package org.csjchoisoojong.utilities;

import javafx.scene.control.Alert;
import org.csjchoisoojong.controllers.tools.ObservableResourceFactory;

import java.util.MissingResourceException;

public class UIOutputer {
    private static ObservableResourceFactory resourceFactory;

    public static void printError(String toOut) {
        message("Organization Management Application", toOut, Alert.AlertType.ERROR);
    }


    public static void tryError(String toOut) {
        if (toOut.startsWith("Error: ")) message("Organization Management Application", toOut.substring(7), Alert.AlertType.ERROR);
        else message("", toOut, Alert.AlertType.INFORMATION);
    }

    private static boolean haveResourceFactory() {
        return resourceFactory == null;
    }

    public static String tryResource(String str) {
        try {
            if (haveResourceFactory()) throw new NullPointerException();
            return resourceFactory.getResources().getString(str);
        } catch (MissingResourceException | NullPointerException exception) {
            return str;
        }
    }
    public static void setResourceFactory(ObservableResourceFactory resourceFactory) {
        UIOutputer.resourceFactory = resourceFactory;
    }

    private static void message(String title, String toOut, Alert.AlertType messageType) {
        Alert alert = new Alert(messageType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(tryResource(toOut));
        alert.showAndWait();
    }
}
