package org.csjchoisoojong.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.csjchoisoojong.connector.Communicator;
import org.csjchoisoojong.controllers.tools.ObservableResourceFactory;
import org.csjchoisoojong.processing.UserAuthHandler;
import org.csjchoisoojong.run.App;

public class LoginWindowController {
    private final Color CONNECTED_COLOR = Color.GREEN;
    private final Color NOT_CONNECTED_COLOR = Color.RED;
    App app;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox registerCheckBox;
    @FXML
    private Label isConnectedLabel;
    @FXML
    private Button signInButton;

    private ObservableResourceFactory resourceFactory;
    private UserAuthHandler userAuthHandler;
    private Communicator communicator;

    public void setApp(App app) {
        this.app = app;
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    public void setUserAuthHandler(UserAuthHandler userAuthHandler) {
        this.userAuthHandler = userAuthHandler;
    }

    public void initializeLanguage(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
        bindLanguage();
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    @FXML
    private void signInButtonOnAction() {
        if (userAuthHandler.processAuthentication(usernameField.getText(), passwordField.getText(), registerCheckBox.isSelected()) || userAuthHandler.processAuthentication(usernameField.getText(), passwordField.getText(), !registerCheckBox.isSelected())) {
            app.setMainWindow();
        } else if (!communicator.isConnected()) {
            isConnectedLabel.textProperty().bind(resourceFactory.getStringBinding("NotConnected"));
            isConnectedLabel.setTextFill(NOT_CONNECTED_COLOR);
        } else {
            isConnectedLabel.textProperty().bind(resourceFactory.getStringBinding("Connected"));
            isConnectedLabel.setTextFill(CONNECTED_COLOR);
        }
    }

    private void bindLanguage() {
        usernameLabel.textProperty().bind(resourceFactory.getStringBinding("UsernameLabel"));
        passwordLabel.textProperty().bind(resourceFactory.getStringBinding("PasswordLabel"));
        registerCheckBox.textProperty().bind(resourceFactory.getStringBinding("RegisterCheckBox"));
        signInButton.textProperty().bind(resourceFactory.getStringBinding("SignInButton"));
        if (communicator.isConnected()) {
            isConnectedLabel.textProperty().bind(resourceFactory.getStringBinding("Connected"));
            isConnectedLabel.setTextFill(CONNECTED_COLOR);
        } else {
            isConnectedLabel.textProperty().bind(resourceFactory.getStringBinding("NotConnected"));
            isConnectedLabel.setTextFill(NOT_CONNECTED_COLOR);
        }
    }


}
