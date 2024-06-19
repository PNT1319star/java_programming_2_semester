package org.csjchoisoojong.controllers;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.table.TableFilter;
import org.csjchoisoojong.controllers.tools.ObservableResourceFactory;
import org.csjchoisoojong.data.Organization;
import org.csjchoisoojong.data.OrganizationType;
import org.csjchoisoojong.interaction.OrganizationRaw;
import org.csjchoisoojong.processing.CommandHandler;
import org.csjchoisoojong.run.App;
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
    private final Duration ANIMATION_DURATION = Duration.millis(800);
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
    private AnchorPane canvasPane;
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
    private Map<Shape, Integer> shapeMap;
    private Map<String, Locale> localeMap;
    private Shape prevClicked;
    private Color prevColor;
    private Random randomGenerator;
    private ObservableResourceFactory resourceFactory;
    private FileScriptHandler fileScriptHandler;
    private Thread periodicThread;
    private volatile boolean isPaused = false;
    private ArrayDeque<Organization> existedOrganizationCollection = new ArrayDeque<>();
    private App app;

    public void initialize() {
        initializeTable();
        initializeCanvas();
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        userColorMap = new HashMap<>();
        shapeMap = new HashMap<>();
        long RANDOM_SEED = 1821L;
        randomGenerator = new Random(RANDOM_SEED);
        localeMap = new HashMap<>();
        localeMap.put("English", new Locale("en", "CA"));
        localeMap.put("Русский", new Locale("ru", "RU"));
        localeMap.put("Slovenčina", new Locale("sk", "SK"));
        localeMap.put("Latviešu", new Locale("lv", "LV"));
        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
        organizationTableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                pauseAndResumePeriodicRefresh();
            }
        });
    }

    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void setApp(App app) {
        this.app = app;
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
        if (selectedItem == null || selectedItem.isEmpty())
            languageComboBox.getSelectionModel().selectFirst();
        languageComboBox.setOnAction((event) -> resourceFactory.setResources(ResourceBundle.getBundle("bundles.gui", localeMap.get(languageComboBox.getValue()))));
        bindLanguage();
    }

    public void startPeriodicRefresh() {
        periodicThread = new Thread(() -> {
            while (true) {
                try {
                    if (!isPaused) {
                        Platform.runLater(() -> requestAction(REFRESH_COMMAND_NAME));
                    }
                    Thread.sleep(10000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        });
        periodicThread.start();
    }


    public void stopPeriodicRefresh() {
        isPaused = true;
        if (periodicThread != null && periodicThread.isAlive()) {
            try {
                periodicThread.join(1000); // Đợi một thời gian ngắn để luồng kết thúc
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }


    private void pausePeriodicRefresh() {
        isPaused = true;
    }
    private void pauseAndResumePeriodicRefresh() {
        pausePeriodicRefresh(); // Dừng periodicThread khi có sự kiện chuột

        // Sử dụng Timer để resume sau 30 giây
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> resumePeriodicRefresh());
            }
        }, 30000); // 30 giây
    }

    private void resumePeriodicRefresh() {
        isPaused = false;
    }

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
        pausePeriodicRefresh();
        requestAction(INFO_COMMAND_NAME);
        resumePeriodicRefresh();
    }

    @FXML
    private void addButtonOnAction() {
        pausePeriodicRefresh();
        askWindowController.clearOrganization();
        askStage.showAndWait();
        OrganizationRaw organizationRaw = askWindowController.getAndClear();
        if (organizationRaw != null) requestAction(ADD_COMMAND_NAME, "", organizationRaw);
        refreshButtonOnAction();
        resumePeriodicRefresh();
    }

    @FXML
    private void addIfMaxButtonOnAction() {
        pausePeriodicRefresh();
        askWindowController.clearOrganization();
        askStage.showAndWait();
        OrganizationRaw organizationRaw = askWindowController.getAndClear();
        if (organizationRaw != null) requestAction(ADD_IF_MAX_COMMAND_NAME, "", organizationRaw);
        refreshButtonOnAction();
        resumePeriodicRefresh();
    }

    @FXML
    private void updateButtonOnAction() {
        if (!organizationTableView.getSelectionModel().isEmpty()) {
            pausePeriodicRefresh();
            int id = organizationTableView.getSelectionModel().getSelectedItem().getId();
            askWindowController.setOrganization(organizationTableView.getSelectionModel().getSelectedItem());
            askStage.showAndWait();
            OrganizationRaw organizationRaw = askWindowController.getAndClear();
            if (organizationRaw != null) requestAction(UPDATE_COMMAND_NAME, id + "", organizationRaw);
            refreshButtonOnAction();
            resumePeriodicRefresh();
        } else UIOutputer.printError("UpdateButtonSelectionException");
    }

    @FXML
    private void removeButtonOnAction() {
        if (!organizationTableView.getSelectionModel().isEmpty()) {
            pausePeriodicRefresh();
            requestAction(REMOVE_BY_ID_COMMAND_NAME, organizationTableView.getSelectionModel().getSelectedItem().getId().toString(), null);
            refreshButtonOnAction();
            resumePeriodicRefresh();
        } else UIOutputer.printError("RemoveByIdButtonException");
    }

    @FXML
    private void removeLowerButtonOnAction() {
        if (!organizationTableView.getSelectionModel().isEmpty()) {
            pausePeriodicRefresh();
            Organization organizationFromTable = organizationTableView.getSelectionModel().getSelectedItem();
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
            refreshButtonOnAction();
            resumePeriodicRefresh();
        } else UIOutputer.printError("RemoveLowerException");
    }

    @FXML
    private void clearButtonOnAction() {
        pausePeriodicRefresh();
        requestAction(CLEAR_COMMAND_NAME);
        refreshButtonOnAction();
        resumePeriodicRefresh();
    }

    @FXML
    private void executeScriptButtonOnAction() {
        pausePeriodicRefresh();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null) return;
        if (!fileScriptHandler.execute(file)) {
            Platform.exit();
        } else refreshButtonOnAction();
        resumePeriodicRefresh();
    }

    @FXML
    private void exitButtonOnAction() {
        stopPeriodicRefresh();
        Platform.runLater(() -> {
            try {
                app.setLoginWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void refreshCanvas() {
        shapeMap.keySet().forEach(s -> canvasPane.getChildren().remove(s));
        shapeMap.clear();
        Map<Shape, Tooltip> tooltipMap = new HashMap<>();
        for (Organization organization : organizationTableView.getItems()) {
            if (!userColorMap.containsKey(organization.getUsername()))
                userColorMap.put(organization.getUsername(), Color.color(randomGenerator.nextDouble(), randomGenerator.nextDouble(), randomGenerator.nextDouble()));
            double MAX_SIZE = 2500;
            double size = Math.min(organization.getAnnualTurnover(), MAX_SIZE);
            Shape object = IconGenerator.convertImageToShape(organization.getType(), userColorMap.get(organization.getUsername()), size);
            object.setFill(userColorMap.get(organization.getUsername()));
            object.setOnMouseClicked(this::shapeOnMouseClicked);
            object.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(organization.getCoordinates().getX()));
            object.translateYProperty().bind(canvasPane.heightProperty().divide(2).subtract(organization.getCoordinates().getY()));

            Tooltip tooltip = new Tooltip(organization.toString());
            Tooltip.install(object, tooltip);
            tooltipMap.put(object, tooltip);
            canvasPane.getChildren().add(object);
            shapeMap.put(object, organization.getId());

        }

    }

    private void shapeOnMouseClicked(MouseEvent event) {
        Shape shape = (Shape) event.getSource();
        int id = shapeMap.get(shape);
        for (Organization organization : organizationTableView.getItems()) {
            if (organization.getId() == id) {
                organizationTableView.getSelectionModel().select(organization);
                break;
            }
        }
        if (prevClicked != null) {
            prevClicked.setFill(prevColor);
        }
        prevClicked = shape;
        prevColor = (Color) shape.getFill();
        shape.setFill(prevColor.brighter());
        pausePeriodicRefresh();
    }

    private void requestAction(String commandName, String commandStringArgument, Serializable commandObjectArgument) {
        ArrayDeque<Organization> organizations = commandHandler.executeCommand(commandName, commandStringArgument, commandObjectArgument);
        if (organizations != null) {
            ObservableList<Organization> organizationObservableList = FXCollections.observableArrayList(organizations);
            Platform.runLater(() -> {
                organizationTableView.setItems(organizationObservableList);
                TableFilter.forTableView(organizationTableView).apply();
                organizationTableView.getSelectionModel().clearSelection();
                refreshCanvas();
            });
        }
    }

    private void requestAction(String commandName) {
        requestAction(commandName, "", null);
    }

    public void initializeCanvas() {
        drawGridAndAxes();
        canvasPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            drawGridAndAxes();
            refreshCanvas();
        });
        canvasPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            drawGridAndAxes();
            refreshCanvas();
        });

    }

    private void drawGridAndAxes() {
        canvasPane.getChildren().clear();
        double width = canvasPane.getWidth();
        double height = canvasPane.getHeight();

        for (int i = 10; i < width; i += 10) {
            Line verticalLine = new Line(i, 0, i, height);
            verticalLine.setStroke(Color.LIGHTGRAY);
            verticalLine.setStrokeWidth(0.5);
            canvasPane.getChildren().add(verticalLine);
        }

        for (int i = 10; i < height; i += 10) {
            Line horizontalLine = new Line(0, i, width, i);
            horizontalLine.setStroke(Color.LIGHTGRAY);
            horizontalLine.setStrokeWidth(0.5);
            canvasPane.getChildren().add(horizontalLine);
        }

        Line yAxis = new Line(width / 2, 0, width / 2, height);
        yAxis.setStroke(Color.RED);
        yAxis.setStrokeWidth(2);

        Line xAxis = new Line(0, height / 2, width, height / 2);
        xAxis.setStroke(Color.RED);
        xAxis.setStrokeWidth(2);

        canvasPane.getChildren().addAll(yAxis, xAxis);
    }
}
