package org.csjchoisoojong.run;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.csjchoisoojong.connector.Communicator;
import org.csjchoisoojong.controllers.AskWindowController;
import org.csjchoisoojong.controllers.LoginWindowController;
import org.csjchoisoojong.controllers.MainWindowController;
import org.csjchoisoojong.controllers.tools.ObservableResourceFactory;
import org.csjchoisoojong.processing.CommandHandler;
import org.csjchoisoojong.processing.UserAuthHandler;
import org.csjchoisoojong.script.FileScriptHandler;
import org.csjchoisoojong.utilities.UIOutputer;
import org.csjchoisoojong.utility.ConsolePrinter;

import java.util.ResourceBundle;
import java.util.Scanner;

public class App extends Application {
    private static final String APP_TITLE = "Organization Management Application";
    private static ObservableResourceFactory resourceFactory;
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;
    private static Scanner scanner;
    private static final String host = "localhost";
    private static final String sPort = "2222";
    private Stage primaryStage;
    private Communicator communicator;
    private UserAuthHandler userAuthHandler;
    private CommandHandler commandHandler;
    private LoginWindowController loginWindowController;
    private FileScriptHandler fileScriptHandler;


    @Override
    public void start(Stage stage) {
        try {
            this.primaryStage = stage;
            setLoginWindow();
        } catch (Exception exception) {
            System.err.println(exception);
            exception.printStackTrace();
        }
    }

    @Override
    public void init() {
        scanner = new Scanner(System.in);
        int port = Integer.parseInt(sPort);
        communicator = new Communicator(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS);
        userAuthHandler = new UserAuthHandler(scanner, communicator);
        commandHandler = new CommandHandler(communicator,scanner);
        fileScriptHandler = new FileScriptHandler(commandHandler, communicator);
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
            commandHandler.setSessionId(userAuthHandler.getSessionId());
            fileScriptHandler.setSession_id(userAuthHandler.getSessionId());
            FXMLLoader mainWindowLoader = new FXMLLoader();
            mainWindowLoader.setLocation(getClass().getResource("/view/MainWindow.fxml"));
            Parent mainWindowRootNode = mainWindowLoader.load();
            Scene mainWindowScene = new Scene(mainWindowRootNode);
            MainWindowController mainWindowController = mainWindowLoader.getController();
            mainWindowController.initialize();
            mainWindowController.initializeLanguages(resourceFactory);

            FXMLLoader askWindowLoader = new FXMLLoader();
            askWindowLoader.setLocation(getClass().getResource("/view/AskWindow.fxml"));
            Parent askWindowRootNode = askWindowLoader.load();
            Scene askWindowScene = new Scene(askWindowRootNode);
            Stage askStage = new Stage();
            askStage.setTitle(APP_TITLE);
            askStage.setScene(askWindowScene);
            askStage.setResizable(false);
            askStage.initModality(Modality.WINDOW_MODAL);
            askStage.initOwner(primaryStage);
            AskWindowController askWindowController = askWindowLoader.getController();
            askWindowController.setAskStage(askStage);
            askWindowController.initializeLanguage(resourceFactory);

            mainWindowController.setApp(this);
            mainWindowController.setUsername(loginWindowController.getUsername());
            mainWindowController.setCommandHandler(commandHandler);
            mainWindowController.setFileScriptHandler(fileScriptHandler);
            mainWindowController.setAskStage(askStage);
            mainWindowController.setPrimaryStage(primaryStage);
            mainWindowController.setAskWindowController(askWindowController);
            mainWindowController.startPeriodicRefresh();

            primaryStage.setScene(mainWindowScene);
            primaryStage.setMinWidth(mainWindowScene.getWidth());
            primaryStage.setMinHeight(mainWindowScene.getHeight());
            primaryStage.setResizable(true);
        } catch (Exception exception) {
            System.err.println(exception);
            exception.printStackTrace();
        }
    }
    public void setLoginWindow() {
        try {
            FXMLLoader loginWindowLoader = new FXMLLoader();
            loginWindowLoader.setLocation(getClass().getResource("/view/LoginWindow.fxml"));
            Parent loginWindowRootNode = loginWindowLoader.load();
            Scene loginWindowScene = new Scene(loginWindowRootNode);
            loginWindowController = loginWindowLoader.getController();
            loginWindowController.setApp(this);
            loginWindowController.setCommunicator(communicator);
            loginWindowController.setUserAuthHandler(userAuthHandler);
            loginWindowController.initialize();
            loginWindowController.initializeLanguage(resourceFactory);

            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(loginWindowScene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception exception) {
            ConsolePrinter.printError(exception);
        }
    }
}