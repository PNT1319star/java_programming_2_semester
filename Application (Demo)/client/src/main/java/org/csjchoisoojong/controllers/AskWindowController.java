package org.csjchoisoojong.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.csjchoisoojong.controllers.tools.ObservableResourceFactory;
import org.csjchoisoojong.data.Address;
import org.csjchoisoojong.data.Coordinates;
import org.csjchoisoojong.data.Organization;
import org.csjchoisoojong.data.OrganizationType;
import org.csjchoisoojong.exceptions.EmptyException;
import org.csjchoisoojong.interaction.OrganizationRaw;
import org.csjchoisoojong.utilities.UIOutputer;

public class AskWindowController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label xLabel;
    @FXML
    private Label yLabel;
    @FXML
    private Label annualTurnoverLabel;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label employeesCountLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private TextField annualTurnoverField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField employeesCountField;
    @FXML
    private TextField addressField;
    @FXML
    private ComboBox<OrganizationType> typeComboBox;
    @FXML
    private Button enterButton;

    private Stage askStage;
    private OrganizationRaw resultOrganization;
    private ObservableResourceFactory resourceFactory;

    public void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList(OrganizationType.values()));
    }

    public void initializeLanguage(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
        bindGuiLanguage();
    }

    public void setAskStage(Stage askStage) {
        this.askStage = askStage;
    }

    public OrganizationRaw getAndClear() {
        OrganizationRaw returnedOrganization = resultOrganization;
        resultOrganization = null;
        return returnedOrganization;
    }

    public void clearOrganization() {
        nameField.clear();
        xField.clear();
        yField.clear();
        annualTurnoverField.clear();
        fullNameField.clear();
        employeesCountField.clear();
        typeComboBox.setValue(OrganizationType.PUBLIC);
        addressField.clear();
    }

    public void setOrganization(Organization organization) {
        nameField.setText(organization.getName());
        xField.setText(organization.getCoordinates().getX() + "");
        yField.setText(organization.getCoordinates().getY() + "");
        annualTurnoverField.setText(organization.getAnnualTurnover() + "");
        fullNameField.setText(organization.getFullName());
        employeesCountField.setText(organization.getEmployeesCount() + "");
        typeComboBox.setValue(organization.getType());
        addressField.setText(organization.getPostalAddress().getStreet());
    }

    @FXML
    private void enterButtonOnAction() {
        try {
            resultOrganization = new OrganizationRaw(
                    convertName(),
                    new Coordinates(convertX(), convertY()),
                    convertAnnualTurnover(),
                    convertFullName(),
                    convertEmployeesCount(),
                    typeComboBox.getValue(),
                    new Address(convertAddress())
            );
            askStage.close();
        } catch (IllegalArgumentException ignored) {
        }
    }

    private void bindGuiLanguage() {
        nameLabel.textProperty().bind(resourceFactory.getStringBinding("NameColumn"));
        xLabel.textProperty().bind(resourceFactory.getStringBinding("XColumn"));
        yLabel.textProperty().bind(resourceFactory.getStringBinding("YColumn"));
        annualTurnoverLabel.textProperty().bind(resourceFactory.getStringBinding("AnnualTurnoverColumn"));
        fullNameLabel.textProperty().bind(resourceFactory.getStringBinding("FullNameColumn"));
        employeesCountLabel.textProperty().bind(resourceFactory.getStringBinding("EmployeesCountColumn"));
        typeLabel.textProperty().bind(resourceFactory.getStringBinding("TypeColumn"));
        addressLabel.textProperty().bind(resourceFactory.getStringBinding("AddressColumn"));
        enterButton.textProperty().bind(resourceFactory.getStringBinding("EnterButton"));
    }

    private String convertName() throws IllegalArgumentException {
        String name;
        try {
            name = nameField.getText();
            if (name.isEmpty()) throw new EmptyException();
        } catch (EmptyException exception) {
            UIOutputer.printError("NameEmptyException");
            throw new IllegalArgumentException();
        }
        return name;
    }

    private long convertX() throws IllegalArgumentException {
        String strX;
        long x;
        try {
            strX = xField.getText();
            x = Long.parseLong(strX);
        } catch (NumberFormatException exception) {
            UIOutputer.printError("XFormatException");
            throw new IllegalArgumentException();
        }
        return x;
    }

    private long convertY() throws IllegalArgumentException {
        String strY;
        long y;
        try {
            strY = yField.getText();
            y = Long.parseLong(strY);
        } catch (NumberFormatException exception) {
            UIOutputer.printError("YFormatException");
            throw new IllegalArgumentException();
        }
        return y;
    }

    private float convertAnnualTurnover() throws IllegalArgumentException {
        String strAnnualTurnover;
        float annualTurnover;
        try {
            strAnnualTurnover = annualTurnoverField.getText();
            annualTurnover = Float.parseFloat(strAnnualTurnover);
        } catch (NumberFormatException exception) {
            UIOutputer.printError("AnnualTurnoverException");
            throw new IllegalArgumentException();
        }
        return annualTurnover;
    }

    private String convertFullName() throws IllegalArgumentException {
        String fullName;
        try {
            fullName = fullNameField.getText();
            if (fullName.isEmpty()) throw new EmptyException();
        } catch (EmptyException exception) {
            UIOutputer.printError("FullNameException");
            throw new IllegalArgumentException();
        }
        return fullName;
    }

    private int convertEmployeesCount() throws IllegalArgumentException {
        String strEmployeesCount;
        int employeesCount;
        try {
            strEmployeesCount = employeesCountField.getText();
            employeesCount = Integer.parseInt(strEmployeesCount);
        } catch (NumberFormatException exception) {
            UIOutputer.printError("EmployeesCountException");
            throw new IllegalArgumentException();
        }
        return employeesCount;
    }

    private String convertAddress() throws IllegalArgumentException {
        String address;
        try {
            address = addressField.getText();
            if (address.isEmpty()) throw new EmptyException();
        } catch (EmptyException exception) {
            UIOutputer.printError("AddressException");
            throw new IllegalArgumentException();
        }
        return address;
    }

}
