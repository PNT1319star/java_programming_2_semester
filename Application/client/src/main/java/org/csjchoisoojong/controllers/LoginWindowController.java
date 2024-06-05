package org.csjchoisoojong.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.csjchoisoojong.connector.Communicator;
import org.csjchoisoojong.controllers.tools.ObservableResourceFactory;
import org.csjchoisoojong.processing.UserAuthHandler;
import org.csjchoisoojong.run.App;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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
    @FXML
    private ComboBox<String> languageComboBox;

    private ObservableResourceFactory resourceFactory;
    private UserAuthHandler userAuthHandler;
    private Communicator communicator;
    private Map<String, Locale> localeMap;

    public void setApp(App app) {
        this.app = app;
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    public void setUserAuthHandler(UserAuthHandler userAuthHandler) {
        this.userAuthHandler = userAuthHandler;
    }
    public void initialize() {
        localeMap = new HashMap<>();
        localeMap.put("English", new Locale("en", "CA"));
        localeMap.put("Русский", new Locale("ru", "RU"));
        localeMap.put("Slovenčina", new Locale("sk", "SK"));
        localeMap.put("Latviešu", new Locale("lv", "LV"));
        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
    }


    public void initializeLanguage(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
        for (String localeName: localeMap.keySet()) {
            if (localeMap.get(localeName).equals(resourceFactory.getResources().getLocale()))
                languageComboBox.getSelectionModel().select(localeName);
        }
        String selectedItem = languageComboBox.getSelectionModel().getSelectedItem();
        if (selectedItem == null || selectedItem.isEmpty()) languageComboBox.getSelectionModel().selectFirst();
        languageComboBox.setOnAction((event) -> resourceFactory.setResources(ResourceBundle.getBundle("bundles.gui", localeMap.get(languageComboBox.getValue()))));
        bindLanguage();
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    @FXML
    private void signInButtonOnAction() {
        if (userAuthHandler.processAuthentication(usernameField.getText(), passwordField.getText(), registerCheckBox.isSelected()) || userAuthHandler.processAuthentication(usernameField.getText(), passwordField.getText(), !registerCheckBox.isSelected())) {
            isConnectedLabel.textProperty().bind(resourceFactory.getStringBinding("Connected"));
            isConnectedLabel.setTextFill(CONNECTED_COLOR);
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
        resourceFactory.setResources(ResourceBundle.getBundle("bundles.gui", localeMap.get(languageComboBox.getSelectionModel().getSelectedItem())));
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
