package org.csjchoisoojong.run;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.csjchoisoojong.controllers.LoginWindowController;
import org.csjchoisoojong.controllers.tools.ObservableResourceFactory;
import org.csjchoisoojong.utilities.ConsoleManager;
import org.csjchoisoojong.utilities.UIOutputer;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class App extends Application {
    private static final String APP_TITLE = "Organization Management Application";
    private static ObservableResourceFactory resourceFactory;
    private static Scanner scanner;
    private static String host;
    private static String port;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            this.primaryStage = stage;
            FXMLLoader loginWindowLoader = new FXMLLoader();
            loginWindowLoader.setLocation(getClass().getResource("/view/LoginWindow.fxml"));
            Parent loginWindowRootNode = loginWindowLoader.load();
            Scene loginWindowScene = new Scene(loginWindowRootNode);
            LoginWindowController loginWindowController = loginWindowLoader.getController();
            loginWindowController.setApp(this);

            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(loginWindowScene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception exception) {
            System.err.println(exception);
            exception.printStackTrace();
        }
    }

    @Override
    public void init() {
        scanner = new Scanner(System.in);
        ConsoleManager.interactive(host, port);
    }

    @Override
    public void stop() {
        scanner.close();
    }


    public static void main(String[] args) {
        resourceFactory = new ObservableResourceFactory();
        resourceFactory.setResources(ResourceBundle.getBundle("bundles.gui"));
        UIOutputer.setResourceFactory(resourceFactory);
        launch();
    }

    public void setMainWindow() {
        try {
            FXMLLoader mainWindowLoader = new FXMLLoader();
            mainWindowLoader.setLocation(getClass().getResource());
        }
    }
}