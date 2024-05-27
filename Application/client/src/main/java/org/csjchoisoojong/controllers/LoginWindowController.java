package org.csjchoisoojong.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
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
    public void setApp(App app) {
        this.app = app;
    }
    @FXML
    private void signInButtonOnAction() {
        if(userAuthHandler.processAuthentication(usernameField.getText(), passwordField.getText(), registerCheckBox.isSelected()))
    }

}
