package org.csjchoisoojong.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.csjchoisoojong.controllers.tools.ObservableResourceFactory;
import org.csjchoisoojong.data.Organization;
import org.csjchoisoojong.data.OrganizationType;
import org.csjchoisoojong.interaction.OrganizationRaw;
import org.csjchoisoojong.processing.CommandHandler;
import org.csjchoisoojong.script.FileScriptHandler;
import org.csjchoisoojong.utilities.IconGenerator;
import org.csjchoisoojong.utilities.UIOutputer;

import java.io.File;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;

public class MainWindowController {
    public static final String LOGIN_COMMAND_NAME = "login";
    public static final String REGISTER_COMMAND_NAME = "register";
    public static final String REFRESH_COMMAND_NAME = "refresh";
    public static final String INFO_COMMAND_NAME = "info";
    public static final String ADD_COMMAND_NAME = "add";
    public static final String ADD_IF_MAX_COMMAND_NAME = "add_if_max";
    public static final String UPDATE_COMMAND_NAME = "update";
    public static final String REMOVE_BY_ID_COMMAND_NAME = "remove_by_id";
    public static final String REMOVE_LOWER_COMMAND_NAME = "remove_lower";
    public static final String CLEAR_COMMAND_NAME = "clear";

    private final long RANDOM_SEED = 1821L;
    @FXML
    private TableView<Organization> organizationTableView;
    @FXML
    private TableColumn<Organization, Integer> idColumn;
    @FXML
    private TableColumn<Organization, String> nameColumn;
    @FXML
    private TableColumn<Organization, Long> xColumn;
    @FXML
    private TableColumn<Organization, Long> yColumn;
    @FXML
    private TableColumn<Organization, ZonedDateTime> creationTimeColumn;
    @FXML
    private TableColumn<Organization, Float> annualTurnoverColumn;
    @FXML
    private TableColumn<Organization, String> fullNameColumn;
    @FXML
    private TableColumn<Organization, Integer> employeesCountColumn;
    @FXML
    private TableColumn<Organization, OrganizationType> typeColumn;
    @FXML
    private TableColumn<Organization, String> addressColumn;
    @FXML
    private TableColumn<Organization, String> ownerColumn;
    @FXML
    private Canvas canvas;
    @FXML
    private Tab tableTab;
    @FXML
    private Tab canvasTab;
    @FXML
    private Button infoButton;
    @FXML
    private Button addButton;
    @FXML
    private Button addIfMaxButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button removeLowerButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button executeScriptButton;
    @FXML
    private Button exitButton;
    @FXML
    private Tooltip infoButtonToolTip;
    @FXML
    private Tooltip addButtonToolTip;
    @FXML
    private Tooltip addIfMaxButtonToolTip;
    @FXML
    private Tooltip updateButtonToolTip;
    @FXML
    private Tooltip removeButtonToolTip;
    @FXML
    private Tooltip removeLowerButtonToolTip;
    @FXML
    private Tooltip clearButtonToolTip;
    @FXML
    private Tooltip executeScriptButtonToolTip;
    @FXML
    private Tooltip exitButtonToolTip;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Label userNameLabel;

    private Stage askStage;
    private Stage primaryStage;
    private FileChooser fileChooser;
    private CommandHandler commandHandler;
    private AskWindowController askWindowController;
    private Map<String, Color> userColorMap;
    private Map<String, Locale> localeMap;
    private Random randomGenerator;
    private ObservableResourceFactory resourceFactory;
    private FileScriptHandler fileScriptHandler;
    private volatile boolean running = true;
    private Thread periodicThread;
    private Organization organizationFromTable;


    public void initialize() {
        initializeTable();
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> organizationClicked(event.getX(), event.getY()));
        drawMesh();
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        userColorMap = new HashMap<>();
        randomGenerator = new Random(RANDOM_SEED);
        localeMap = new HashMap<>();
        localeMap.put("English", new Locale("en", "CA"));
        localeMap.put("Русский", new Locale("ru", "RU"));
        localeMap.put("Slovenčina", new Locale("sk", "SK"));
        localeMap.put("Latviešu", new Locale("lv", "LV"));
        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
        drawOrganization();
    }

    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void setUsername(String username) {
        userNameLabel.setText(username);
    }

    public void setAskStage(Stage askStage) {
        this.askStage = askStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setAskWindowController(AskWindowController askWindowController) {
        this.askWindowController = askWindowController;
    }

    public void setFileScriptHandler(FileScriptHandler fileScriptHandler) {
        this.fileScriptHandler = fileScriptHandler;
    }

    public void initializeLanguages(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
        for (String localeName : localeMap.keySet()) {
            if (localeMap.get(localeName).equals(resourceFactory.getResources().getLocale()))
                languageComboBox.getSelectionModel().select(localeName);
        }
        String selectedItem = languageComboBox.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
        if (selectedItem == null || selectedItem.isEmpty())
            languageComboBox.getSelectionModel().selectFirst();
        languageComboBox.setOnAction((event) -> resourceFactory.setResources(ResourceBundle.getBundle("bundles.gui", localeMap.get(languageComboBox.getValue()))));
        bindLanguage();
    }

    public void startPeriodicRefresh() {
        running = true;
        periodicThread = new Thread(() -> {
            while (running) {
                try {
                    Platform.runLater(() -> {
                        requestAction(REFRESH_COMMAND_NAME);
                        drawOrganization();
                    });
                    Thread.sleep(5000);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        periodicThread.start();
    }

    public void stopPeriodicRefresh() {
        running = false;
        if (periodicThread != null) {
            try {
                periodicThread.join();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }


    @FXML
    public void refreshButtonOnAction() {
        requestAction(REFRESH_COMMAND_NAME);
    }

    private void initializeTable() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        xColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getX()));
        yColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getY()));
        creationTimeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCreationDate()));
        annualTurnoverColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAnnualTurnover()));
        fullNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFullName()));
        employeesCountColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEmployeesCount()));
        typeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getType()));
        addressColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPostalAddress().getStreet()));
        ownerColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUsername()));

//        organizationTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                stopPeriodicRefresh();
//            }
//        });
    }

    private void bindLanguage() {
        resourceFactory.setResources(ResourceBundle.getBundle("bundles.gui", localeMap.get(languageComboBox.getSelectionModel().getSelectedItem())));

        idColumn.textProperty().bind(resourceFactory.getStringBinding("IdColumn"));
        nameColumn.textProperty().bind(resourceFactory.getStringBinding("NameColumn"));
        xColumn.textProperty().bind(resourceFactory.getStringBinding("XColumn"));
        yColumn.textProperty().bind(resourceFactory.getStringBinding("YColumn"));
        creationTimeColumn.textProperty().bind(resourceFactory.getStringBinding("CreationTimeColumn"));
        annualTurnoverColumn.textProperty().bind(resourceFactory.getStringBinding("AnnualTurnoverColumn"));
        fullNameColumn.textProperty().bind(resourceFactory.getStringBinding("FullNameColumn"));
        employeesCountColumn.textProperty().bind(resourceFactory.getStringBinding("EmployeesCountColumn"));
        typeColumn.textProperty().bind(resourceFactory.getStringBinding("TypeColumn"));
        addressColumn.textProperty().bind(resourceFactory.getStringBinding("AddressColumn"));
        ownerColumn.textProperty().bind(resourceFactory.getStringBinding("OwnerColumn"));

        tableTab.textProperty().bind(resourceFactory.getStringBinding("TableTab"));
        canvasTab.textProperty().bind(resourceFactory.getStringBinding("CanvasTab"));

        infoButton.textProperty().bind(resourceFactory.getStringBinding("InfoButton"));
        addButton.textProperty().bind(resourceFactory.getStringBinding("AddButton"));
        addIfMaxButton.textProperty().bind(resourceFactory.getStringBinding("AddIfMaxButton"));
        updateButton.textProperty().bind(resourceFactory.getStringBinding("UpdateButton"));
        removeButton.textProperty().bind(resourceFactory.getStringBinding("RemoveButton"));
        removeLowerButton.textProperty().bind(resourceFactory.getStringBinding("RemoveLowerButton"));
        clearButton.textProperty().bind(resourceFactory.getStringBinding("ClearButton"));
        executeScriptButton.textProperty().bind(resourceFactory.getStringBinding("ExecuteScriptButton"));
        exitButton.textProperty().bind(resourceFactory.getStringBinding("ExitButton"));

        infoButtonToolTip.textProperty().bind(resourceFactory.getStringBinding("InfoButtonToolTip"));
        addButtonToolTip.textProperty().bind(resourceFactory.getStringBinding("AddButtonToolTip"));
        addIfMaxButtonToolTip.textProperty().bind(resourceFactory.getStringBinding("AddIfMaxButtonToolTip"));
        updateButtonToolTip.textProperty().bind(resourceFactory.getStringBinding("UpdateButtonToolTip"));
        removeButtonToolTip.textProperty().bind(resourceFactory.getStringBinding("RemoveButtonToolTip"));
        removeLowerButtonToolTip.textProperty().bind(resourceFactory.getStringBinding("RemoveLowerButtonToolTip"));
        clearButtonToolTip.textProperty().bind(resourceFactory.getStringBinding("ClearButtonToolTip"));
        executeScriptButtonToolTip.textProperty().bind(resourceFactory.getStringBinding("ExecuteScriptButtonToolTip"));
        exitButtonToolTip.textProperty().bind(resourceFactory.getStringBinding("ExitButtonToolTip"));
    }

    @FXML
    private void infoButtonOnAction() {
        requestAction(INFO_COMMAND_NAME);
    }

    @FXML
    private void addButtonOnAction() {
        askWindowController.clearOrganization();
        askStage.showAndWait();
        OrganizationRaw organizationRaw = askWindowController.getAndClear();
        if (organizationRaw != null) requestAction(ADD_COMMAND_NAME, "", organizationRaw);
        refreshButtonOnAction();
    }

    @FXML
    private void addIfMaxButtonOnAction() {
        askWindowController.clearOrganization();
        askStage.showAndWait();
        OrganizationRaw organizationRaw = askWindowController.getAndClear();
        if (organizationRaw != null) requestAction(ADD_IF_MAX_COMMAND_NAME, "", organizationRaw);
        refreshButtonOnAction();
    }

    @FXML
    private void updateButtonOnAction() {
        if (!organizationTableView.getSelectionModel().isEmpty()) {
            organizationFromTable = organizationTableView.getSelectionModel().getSelectedItem();
            int id = organizationFromTable.getId();
            askWindowController.setOrganization(organizationFromTable);
            askStage.showAndWait();
            OrganizationRaw organizationRaw = askWindowController.getAndClear();
            if (organizationRaw != null) {
                requestAction(UPDATE_COMMAND_NAME, id + "", organizationRaw);
                startPeriodicRefresh();
            }
        } else UIOutputer.printError("UpdateButtonSelectionException");
    }

    @FXML
    private void removeButtonOnAction() {
        if (!organizationTableView.getSelectionModel().isEmpty()) {
            organizationFromTable = organizationTableView.getSelectionModel().getSelectedItem();
            requestAction(REMOVE_BY_ID_COMMAND_NAME, organizationFromTable.getId().toString(), null);
        } else UIOutputer.printError("RemoveByIdButtonException");
        refreshButtonOnAction();
    }

    @FXML
    private void removeLowerButtonOnAction() {
        if (!organizationTableView.getSelectionModel().isEmpty()) {
            organizationFromTable = organizationTableView.getSelectionModel().getSelectedItem();
            OrganizationRaw newOrganization = new OrganizationRaw(
                    organizationFromTable.getName(),
                    organizationFromTable.getCoordinates(),
                    organizationFromTable.getAnnualTurnover(),
                    organizationFromTable.getFullName(),
                    organizationFromTable.getEmployeesCount(),
                    organizationFromTable.getType(),
                    organizationFromTable.getPostalAddress()
            );
            requestAction(REMOVE_LOWER_COMMAND_NAME, "", newOrganization);
        } else UIOutputer.printError("RemoveLowerException");
        refreshButtonOnAction();
    }

    @FXML
    private void clearButtonOnAction() {
        requestAction(CLEAR_COMMAND_NAME);
        refreshButtonOnAction();
    }

    @FXML
    private void executeScriptButtonOnAction() {
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null) return;
        if (!fileScriptHandler.execute(file)) {
            Platform.exit();
        } else refreshButtonOnAction();
    }

    @FXML
    private void exitButtonOnAction() {
        stopPeriodicRefresh();
        System.exit(1);
    }

    private void requestAction(String commandName, String commandStringArgument, Serializable commandObjectArgument) {
        ArrayDeque<Organization> organizations = commandHandler.executeCommand(commandName, commandStringArgument, commandObjectArgument);
        if (organizations != null) {
            ObservableList<Organization> organizationObservableList = FXCollections.observableArrayList(organizations);
            Platform.runLater(() -> {
                organizationTableView.setItems(organizationObservableList);
                organizationTableView.getSelectionModel().clearSelection();
            });
        }
    }

    private void requestAction(String commandName) {
        requestAction(commandName, "", null);
    }

    private void drawMesh() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.web("#797979"));
        gc.setLineWidth(1);
        double cellSize = 20.0;
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        for (double x = cellSize; x < width; x += cellSize) {
            gc.strokeLine(x, 0, x, height);
        }

        for (double y = cellSize; y < height; y += cellSize) {
            gc.strokeLine(0, height - y, width, height - y);
        }
    }

    private void organizationClicked(double x, double y) {
        Organization clickedOrganization = null;
        for (Organization organization : organizationTableView.getItems()) {
            double canvasX = (double) organization.getCoordinates().getX() / 1000 * canvas.getWidth();
            double canvasY = (1 - organization.getCoordinates().getY() / 1000) * canvas.getHeight();
            double size = Math.log(organization.getAnnualTurnover()) * 2;
            double distance = Math.sqrt(Math.pow(x - canvasX, 2) + Math.pow(y - canvasY, 2));

            if (distance <= size / 2) {
                clickedOrganization = organization;
                break;
            }
        }
        if (clickedOrganization != null) {
            organizationFromTable = clickedOrganization;
            System.out.println(clickedOrganization);
        }
    }

    private void drawOrganization() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawMesh();

        for (Organization organization : organizationTableView.getItems()) {
            if (!userColorMap.containsKey(organization.getUsername()))
                userColorMap.put(organization.getUsername(), Color.color(randomGenerator.nextDouble(), randomGenerator.nextDouble(), randomGenerator.nextDouble()));
            Color color = userColorMap.get(organization.getUsername());
            double size = organization.getAnnualTurnover()/10 * 2;

            double canvasX = (double) (organization.getCoordinates().getX()  / 1000) * canvas.getWidth();
            double canvasY = (double) (organization.getCoordinates().getY() / 1000) * canvas.getHeight();

            gc.setStroke(color);
            gc.setLineWidth(2);
            gc.strokeOval(canvasX - size / 2, canvasY - size / 2, size, size);

            Image organizationIcon = IconGenerator.getBuilding(organization.getType());
            int iconSize = (int) Math.round(size - 4);
            if (iconSize <= 0) iconSize = 1;
            ImageView imageView = new ImageView(organizationIcon);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(iconSize);
            imageView.setFitHeight(iconSize);
            SnapshotParameters sp = new SnapshotParameters();
            sp.setFill(Color.TRANSPARENT);
            Image scaledOrganizationIcon = imageView.snapshot(sp, null);

            PixelReader pr = scaledOrganizationIcon.getPixelReader();
            WritableImage coloredIcon = new WritableImage((int) iconSize, (int) iconSize);
            PixelWriter pw = coloredIcon.getPixelWriter();
            for (int y = 0; y < scaledOrganizationIcon.getHeight(); y++) {
                for (int x = 0; x < scaledOrganizationIcon.getWidth(); x++) {
                    Color pixelColor = pr.getColor(x, y);
                    if (pixelColor.getOpacity() > 0) pw.setColor(x, y, color);
                    else pw.setColor(x, y, Color.TRANSPARENT);
                }
            }
            gc.drawImage(coloredIcon, canvasX - iconSize / 2, canvasY / iconSize / 2);
        }
    }

//    private void startTimer() {
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> drawOrganization()));
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
//    }
}