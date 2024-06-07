package org.csjchoisoojong.controllers;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
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
import org.csjchoisoojong.utilities.UIOutputer;

import java.io.File;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    public static final String EXIT_COMMAND_NAME = "exit";

    private final long RANDOM_SEED = 1821L;
    private final Duration ANIMATION_DURATION = Duration.millis(800);
    private final double MAX_SIZE = 2500;
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
    private Map<Integer, Text> textMap;
    private Map<Shape, Tooltip> tooltipMap;
    private Map<String, Locale> localeMap;
    private Shape prevClicked;
    private Color prevColor;
    private Random randomGenerator;
    private ObservableResourceFactory resourceFactory;
    private FileScriptHandler fileScriptHandler;
    private int cnt = -1;
    private final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);


    public void initialize() {
        initializeTable();
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        userColorMap = new HashMap<>();
        shapeMap = new HashMap<>();
        textMap = new HashMap<>();
        randomGenerator = new Random(RANDOM_SEED);
        localeMap = new HashMap<>();
        localeMap.put("English", new Locale("en", "CA"));
        localeMap.put("Русский", new Locale("ru", "RU"));
        localeMap.put("Slovenčina", new Locale("sk", "SK"));
        localeMap.put("Latviešu", new Locale("lv", "LV"));
        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
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
        Runnable task = () -> {
            try {
                Platform.runLater(() -> requestAction(REFRESH_COMMAND_NAME));
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        scheduler.scheduleAtFixedRate(task,0, 5, TimeUnit.SECONDS);
    }
    private void stopPeriodicRefresh() {
        scheduler.shutdown();
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
        stopPeriodicRefresh();
        if (!organizationTableView.getSelectionModel().isEmpty()) {

            int id = organizationTableView.getSelectionModel().getSelectedItem().getId();
            askWindowController.setOrganization(organizationTableView.getSelectionModel().getSelectedItem());
            askStage.showAndWait();
            OrganizationRaw organizationRaw = askWindowController.getAndClear();
            if (organizationRaw != null) requestAction(UPDATE_COMMAND_NAME, id + "", organizationRaw);
        } else UIOutputer.printError("UpdateButtonSelectionException");
        refreshButtonOnAction();
        startPeriodicRefresh();
    }

    @FXML
    private void removeButtonOnAction() {
        if (!organizationTableView.getSelectionModel().isEmpty()) {
            requestAction(REMOVE_BY_ID_COMMAND_NAME, organizationTableView.getSelectionModel().getSelectedItem().getId().toString(), null);
        } else UIOutputer.printError("RemoveByIdButtonException");
        refreshButtonOnAction();
    }

    @FXML
    private void removeLowerButtonOnAction() {
        if (!organizationTableView.getSelectionModel().isEmpty()) {
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
        }
        else refreshButtonOnAction();
    }

    @FXML
    private void exitButtonOnAction() {
        stopPeriodicRefresh();
        System.exit(1);
    }

    private void refreshCanvas() {
        cnt = cnt +1;
        if (cnt <= 1) {
            drawAxes();
        }
        shapeMap.keySet().forEach(s -> canvasPane.getChildren().remove(s));
        shapeMap.clear();
        textMap.values().forEach(s -> canvasPane.getChildren().remove(s));
        textMap.clear();
        tooltipMap = new HashMap<>();
        for (Organization organization : organizationTableView.getItems()) {
            if (!userColorMap.containsKey(organization.getUsername()))
                userColorMap.put(organization.getUsername(), Color.color(randomGenerator.nextDouble(), randomGenerator.nextDouble(), randomGenerator.nextDouble()));
            double size = Math.min(organization.getAnnualTurnover()/2, MAX_SIZE);
            Shape object = null;
            switch (organization.getType()) {
                case PUBLIC :
                    object = new Circle(size, userColorMap.get(organization.getUsername()));
                    break;
                case GOVERNMENT:
                    object = new Rectangle(size, size, userColorMap.get(organization.getUsername()));
                    break;
                case TRUST:
                    object = new Rectangle(size, size/2, userColorMap.get(organization.getUsername()));
                    break;
                case OPEN_JOINT_STOCK_COMPANY:
                    object = new Rectangle(size/2, size, userColorMap.get(organization.getUsername()));
                    break;
                case PRIVATE_LIMITED_COMPANY:
                    object = new Ellipse(size, size/2);
                    object.setFill(userColorMap.get(organization.getUsername()));
                    break;
            }
            object.setOnMouseClicked(this::shapeOnMouseClicked);
            object.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(organization.getCoordinates().getX()));
            object.translateYProperty().bind(canvasPane.heightProperty().divide(2).subtract(organization.getCoordinates().getY()));

            Tooltip tooltip = new Tooltip(organization.toString());
            Tooltip.install(object,tooltip);
            tooltipMap.put(object, tooltip);

            Text textObject = new Text(organization.getId().toString());
            textObject.setOnMouseClicked(object::fireEvent);
            textObject.setFont(Font.font(size / 3));
            textObject.setFill(userColorMap.get(organization.getUsername()).darker());
            textObject.translateXProperty().bind(object.translateXProperty().subtract(textObject.getLayoutBounds().getWidth()/4));
            textObject.translateYProperty().bind(object.translateYProperty().add(textObject.getLayoutBounds().getHeight()));

            canvasPane.getChildren().add(object);
            canvasPane.getChildren().add(textObject);
            shapeMap.put(object, organization.getId());
            textMap.put(organization.getId(), textObject);

            ScaleTransition objectAnimation = new ScaleTransition(ANIMATION_DURATION, object);
            ScaleTransition textAnimation = new ScaleTransition(ANIMATION_DURATION, textObject);

            objectAnimation.setFromX(0);
            objectAnimation.setToX(1);
            objectAnimation.setFromY(0);
            objectAnimation.setToY(1);
            textAnimation.setFromX(0);
            textAnimation.setToX(1);
            textAnimation.setFromY(0);
            textAnimation.setToY(1);
            objectAnimation.play();
            textAnimation.play();

        }
    }

    private void  shapeOnMouseClicked(MouseEvent event) {
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
    }

    private void requestAction(String commandName, String commandStringArgument, Serializable commandObjectArgument) {
        ArrayDeque<Organization> organizations = commandHandler.executeCommand(commandName, commandStringArgument, commandObjectArgument);
        if (organizations != null) {
            ObservableList<Organization> organizationObservableList = FXCollections.observableArrayList(organizations);
            Platform.runLater(() -> {
                organizationTableView.setItems(organizationObservableList);
//                TableFilter.forTableView(organizationTableView).apply();
                organizationTableView.getSelectionModel().clearSelection();
                refreshCanvas();
            });
        }
    }

    private void requestAction(String commandName) {
        requestAction(commandName, "", null);
    }
    private void drawAxes() {
        double canvasWidth = canvasPane.getWidth();
        double canvasHeight = canvasPane.getHeight();
        double maxAxisValue = 100; // Giới hạn lớn nhất cho trục tọa độ

        // Vẽ trục X
        Line xAxis = new Line();
        xAxis.setStartX(0);
        xAxis.setEndX(canvasWidth);
        xAxis.setStartY(canvasHeight / 2);
        xAxis.setEndY(canvasHeight / 2);
        xAxis.setStroke(Color.RED);
        xAxis.setStrokeWidth(2);

        // Vẽ các đường vạch trên trục X
        for (double x = -maxAxisValue; x <= maxAxisValue; x += 5) {
            Line tick = new Line();
            double tickX = (x / (2 * maxAxisValue)) * canvasWidth + (canvasWidth / 2);
            tick.setStartX(tickX);
            tick.setEndX(tickX);
            tick.setStartY(canvasHeight / 2 - 5); // Độ dài vạch là 5 pixel
            tick.setEndY(canvasHeight / 2 + 5);
            tick.setStroke(Color.RED);
            tick.setStrokeWidth(1);
            canvasPane.getChildren().add(tick);
        }

        // Vẽ trục Y
        Line yAxis = new Line();
        yAxis.setStartX(canvasWidth / 2);
        yAxis.setEndX(canvasWidth / 2);
        yAxis.setStartY(0);
        yAxis.setEndY(canvasHeight);
        yAxis.setStroke(Color.RED);
        yAxis.setStrokeWidth(2);

        // Vẽ các đường vạch trên trục Y
        for (double y = -maxAxisValue; y <= maxAxisValue; y += 5) {
            Line tick = new Line();
            double tickY = canvasHeight - ((y / (2 * maxAxisValue)) * canvasHeight + (canvasHeight / 2));
            tick.setStartX(canvasWidth / 2 - 5); // Độ dài vạch là 5 pixel
            tick.setEndX(canvasWidth / 2 + 5);
            tick.setStartY(tickY);
            tick.setEndY(tickY);
            tick.setStroke(Color.RED);
            tick.setStrokeWidth(1);
            canvasPane.getChildren().add(tick);
        }

        // Thêm trục tọa độ vào canvas
        canvasPane.getChildren().addAll(xAxis, yAxis);
    }




}