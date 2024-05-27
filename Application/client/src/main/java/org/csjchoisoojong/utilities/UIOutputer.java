package org.csjchoisoojong.utilities;

import javafx.scene.control.Alert;
import org.csjchoisoojong.controllers.tools.ObservableResourceFactory;

import java.text.MessageFormat;
import java.util.MissingResourceException;

public class UIOutputer {
    private static ObservableResourceFactory resourceFactory;

    public static void printError(String toOut, String[] args) {
        message("Organization Management Application", toOut, args, Alert.AlertType.ERROR);
    }

    public static void printError(String toOut) {
        printError(toOut, null);
    }

    public static void tryError(String toOut, String[] args) {
        if (toOut.startsWith("Error: ")) message("Organization Management Application", toOut.substring(7), args, Alert.AlertType.ERROR);
        else message("Collection keeper", toOut, args, Alert.AlertType.INFORMATION);
    }

    private static boolean haveResourceFactory() {
        return resourceFactory == null;
    }

    public static String tryResource(String str, String[] args) {
        try {
            if (haveResourceFactory()) throw new NullPointerException();
            if (args == null) return resourceFactory.getResources().getString(str);
            MessageFormat messageFormat = new MessageFormat(resourceFactory.getResources().getString(str));
            return messageFormat.format(args);
        } catch (MissingResourceException | NullPointerException exception) {
            return str;
        }
    }
    public static void setResourceFactory(ObservableResourceFactory resourceFactory) {
        UIOutputer.resourceFactory = resourceFactory;
    }

    private static void message(String title, String toOut, String[] args, Alert.AlertType messageType) {
        Alert alert = new Alert(messageType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(tryResource(toOut, args));
        alert.showAndWait();
    }
}
