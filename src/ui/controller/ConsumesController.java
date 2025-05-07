package ui.controller;

import DTO.ConsumesBean;
import DTO.ProductBean;
import DTO.AnimalGroupBean;
import DTO.ConsumesIdBean;
import DTO.ManagerBean;
import businessLogic.animalGroup.AnimalGroupFactory;
import ui.cellFactories.DatePickerTableCell;
import java.net.URL;
import java.util.ArrayList;
import businessLogic.consumes.ConsumesRestClient;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Optional;
import java.util.List;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import ui.controller.AnimalController;
import businessLogic.consumes.ConsumesManagerFactory;
import businessLogic.consumes.IConsumesManager;
import businessLogic.product.ProductManagerFactory;
import static groovy.util.ObservableList.ChangeType.oldValue;
import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javax.ws.rs.ClientErrorException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;


/**
 * Controller class for managing the "Consumes" window in the application. 
 * Handles user interactions such as searching, adding, and deleting consume records. 
 * Provides methods for managing the view and controlling business logic interactions related to consume operations.
 * Implements {@link Initializable} to initialize the controller and its elements.
 *
 * @author Pablo
 */
public class ConsumesController implements Initializable {

    /**
     * Table view displaying the consume records.
     */
    @FXML
    private TableView<ConsumesBean> tableConsumes;

    /**
     * Table column displaying the associated animal group for each consume record.
     */
    @FXML
    private TableColumn<ConsumesBean, AnimalGroupBean> tcAnimalGroup;

    /**
     * Table column displaying the associated product for each consume record.
     */
    @FXML
    private TableColumn<ConsumesBean, ProductBean> tcProduct;

    /**
     * Table column displaying the amount consumed for each consume record.
     */
    @FXML
    private TableColumn<ConsumesBean, Float> tcConsumeAmount;

    /**
     * Table column displaying the date of consumption for each consume record.
     */
    @FXML
    private TableColumn<ConsumesBean, Date> tcDate;

    /**
     * Text field for the user to input a search query.
     */
    @FXML
    private TextField searchField;

    /**
     * Button that triggers the search operation.
     */
    @FXML
    private Button btnSearch;

    /**
     * Button that allows the user to add a new consume record.
     */
    @FXML
    private Button btnAdd;

    /**
     * Menu item to delete a selected consume record.
     */
    @FXML
    private MenuItem itemDelete;

    /**
     * DatePicker for selecting the "From" date in search queries.
     */
    @FXML
    private DatePicker dpSearchFrom;

    /**
     * DatePicker for selecting the "To" date in search queries.
     */
    @FXML
    private DatePicker dpSearchTo;

    /**
     * ComboBox for selecting a search criterion.
     */
    @FXML
    private ComboBox<String> comboSearch;

    
    
     /**
     * Button for the print action.
     */
    @FXML
    private Button btnPrint;


    /**
     * The current stage for the consumes view window.
     */
    private Stage stage;

    /**
     * The client used to interact with the backend consume-related services.
     */
    private ConsumesRestClient consumesClient;
         /**
     * ManagerBean representing the current user managing the animals.
     */
    private static ManagerBean manager;
    
    private String managerId;
    
    /**
     * Sets the manager for this controller.
     *
     * @param manager The ManagerBean to set.
     */
    public static void setManager(ManagerBean manager) {
        ConsumesController.manager = manager;
    }

    /**
     * Gets the current manager.
     *
     * @return The ManagerBean instance.
     */
    public static ManagerBean getManager() {
        return manager;
    }
    /**
     * Logger used for logging any actions or errors within the controller.
     */
    private static final Logger LOGGER = Logger.getLogger("ConsumesController");

    /**
     * Initializes the controller class, setting up the interface components and event handlers.
     * 
     * @param url The location of the FXML file that is being loaded.
     * @param rb  The resource bundle used to localize the controller's text.
     */
    public void initialize(URL url, ResourceBundle rb) {

        //Initialize RestClient
  try {
        consumesClient = new ConsumesRestClient();
        if (manager != null) {
            LOGGER.info("Manager ID: " + manager.getId()); // Log the manager ID
        } else {
            LOGGER.severe("Manager is null");
        }
    } catch (Exception e) {
        String errorMsg = "Error initializing Rest: " + e + consumesClient;
        showErrorAlert(errorMsg);
        LOGGER.log(Level.SEVERE, errorMsg);
        return;
    }



        try {
            // Initialize UI components (e.g., buttons, fields, etc.)
            initializeComponents();
          
            // Initialize the table to display the consume records
            initializeTable();

            // Set up event handlers (e.g., button clicks, table actions)
            setupEventHandlers();

            // Load and display all consume records from the backend system
            showAllConsumes();

            // Show the window to the user
            stage.show();

            // Log the successful initialization of the window
            LOGGER.info("Consumes window initialized.");

        } catch (Exception e) {
            // In case of any errors during window initialization, capture and log the stack trace
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            StringBuilder traceBuilder = new StringBuilder();
            for (StackTraceElement element : stackTraceElements) {
                traceBuilder.append(element.toString()).append("\n");
            }
            String errorMsg = "Error initializing window: \n" + traceBuilder.toString();
            LOGGER.log(Level.SEVERE, errorMsg);
        }
    }

    /**
     * Initializes the window components including the ComboBox, search field, buttons, and menu items.
     * This method configures the basic UI elements and sets up their default states.
     * It also handles the visibility of certain UI components based on the initial setup.   
     *
     * @throws Exception if any error occurs while initializing components.
     */
    private void initializeComponents() {
        try {
            // Initialize ComboBox for search criteria
            LOGGER.info("Initializing ComboBox...");
            comboSearch.getItems().addAll("Animal Group", "Product", "Date");
            comboSearch.setValue("Animal Group");
            comboSearch.setEditable(false);
            LOGGER.info("ComboBox initialized successfully.");

            // Initialize search field with placeholder text
            LOGGER.info("Initializing search field...");
            searchField.setPromptText("Enter search text");
            LOGGER.info("Search field initialized successfully.");

            // Initialize the search button
            LOGGER.info("Initializing buttons...");
            btnSearch.setDisable(false);
            btnSearch.setDefaultButton(true);
            LOGGER.info("Buttons initialized successfully.");

            // Initialize menu items and set the delete item as disabled initially
            LOGGER.info("Initializing menu items...");
            itemDelete.setDisable(true);
            LOGGER.info("Menu items initialized successfully.");

            // Hide the date pickers initially
            LOGGER.info("Hiding date pickers...");
            dpSearchFrom.setVisible(false);
            dpSearchTo.setVisible(false);
            LOGGER.info("Date pickers hidden successfully.");

        } catch (Exception e) {
            // Log and display error message in case of an exception
            String errorMsg = "Error initializing components: " + e;
            showErrorAlert(errorMsg); // Display error to user
            LOGGER.log(Level.SEVERE, errorMsg); // Log the error for debugging
        }
    }

    /**
     * Initializes the table and sets up each column with appropriate cell factories, data bindings, and edit commit handlers. 
     * This method configures the table for displaying consume records, with each column bound to a specific property of the `ConsumesBean` model. 
     * It also loads data for the columns (such as animal groups, products, and consume amounts) and sets up event handlers to allow editing of the table's contents.
     * 
     * @throws Exception if any error occurs while initializing the table or setting up the columns.
     */
    private void initializeTable() {

        try {
 
    LOGGER.info("Setting up Product column...");
    tcProduct.setCellValueFactory(new PropertyValueFactory<>("product")); // Access the ProductBean

    List<ProductBean> productList = ProductManagerFactory.get().getAllProducts(new GenericType<List<ProductBean>>() {});

    ObservableList<ProductBean> productData = FXCollections.observableArrayList(productList);
    LOGGER.info("Products fetched, setting up ComboBox cell...");
    tcProduct.setCellFactory(ComboBoxTableCell.forTableColumn(productData)); // Same as Animal Group

    tcProduct.setOnEditCommit(event -> {
        LOGGER.info("Product edit committed: " + event.getNewValue());
        handleEditCommit(event, "product");
    });

} catch (Exception e) {
   
}
       try {
        if (manager != null && manager.getId() != null) {
            List<AnimalGroupBean> animalGroupList = AnimalGroupFactory.get().getAnimalGroupsByManager(new GenericType<List<AnimalGroupBean>>() {}, manager.getId().toString());
            ObservableList<AnimalGroupBean> animalGroupData = FXCollections.observableArrayList(animalGroupList);

            tcAnimalGroup.setCellValueFactory(new PropertyValueFactory<>("animalGroup"));
            tcAnimalGroup.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<AnimalGroupBean>() {
                @Override
                public String toString(AnimalGroupBean animalGroup) {
                    return animalGroup != null ? animalGroup.getName() : "";
                }

                @Override
                public AnimalGroupBean fromString(String string) {
                    return animalGroupData.stream().filter(ag -> ag.getName().equals(string)).findFirst().orElse(null);
                }
            }, animalGroupData));

            tcAnimalGroup.setOnEditCommit(event -> {
                ConsumesBean consume = event.getRowValue();
                consume.setAnimalGroup(event.getNewValue());
                try {
                    ConsumesManagerFactory.get().updateConsume(consume);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error updating consume: ", e);
                }
            });
        } else {
            LOGGER.severe("Manager ID is null");
        }
    } catch (Exception e) {
        String errorMsg = "Error initializing animal group column: " + e.getMessage();
        showErrorAlert(errorMsg);
        LOGGER.log(Level.SEVERE, errorMsg, e);
    }

      
tcConsumeAmount.setCellValueFactory(new PropertyValueFactory<>("consumeAmount"));

tcConsumeAmount.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Float>() {

    public String toString(Float value) {
        return value != null ? value.toString() : "";
    }

    public Float fromString(String string) {
        try {
            if (string == null || string.trim().isEmpty()) {
                throw new IllegalArgumentException("Enter a valid value.");
            }

            Float value = Float.parseFloat(string);

            if (value < 0.0f) {
                throw new IllegalArgumentException("Value must be 0.0 or greater.");
            }
            return value;

        } catch (NumberFormatException e) {
            showAlert("Invalid Format", "Value must be 0.0 or greater.");
            return null;
        } catch (IllegalArgumentException e) {
            showAlert("Invalid Format", e.getMessage());
            return null;
        }
    }
}));

tcConsumeAmount.setOnEditCommit(event -> {
    ConsumesBean consume = event.getRowValue();
    Float newValue = event.getNewValue();
    
    if (newValue != null) { 
        consume.setConsumeAmount(newValue);
        try {
            ConsumesManagerFactory.get().updateConsume(consume);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el consumo: ", e);
            showAlert("Error", "No se pudo actualizar el consumo en la base de datos.");
        }
    }
});

        // Initialize the Date column with a custom DatePicker cell
        try {
            LOGGER.info("Setting up Date column...");
            tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));

            // Set up the custom DatePicker cell for the date column
            tcDate.setCellFactory(new Callback<TableColumn<ConsumesBean, Date>, TableCell<ConsumesBean, Date>>() {
                @Override
                public TableCell<ConsumesBean, Date> call(TableColumn<ConsumesBean, Date> param) {
                    LOGGER.info("Creating custom DatePicker table cell...");
                    DatePickerTableCell<ConsumesBean> cell = new DatePickerTableCell<>(param);
                    cell.updateDateCallback = (Date updatedDate) -> {
                        try {
                            LOGGER.info("Updating consume date: " + updatedDate);
                            updateConsumeDate(updatedDate);
                        } catch (CloneNotSupportedException ex) {
                            LOGGER.log(Level.SEVERE, "Error updating consume date", ex);
                        }
                    };
                    return cell;
                }
            });

            tcDate.setStyle("-fx-alignment: center;");
            LOGGER.info("Date column setup complete.");
        } catch (Exception e) {
            String errorMsg = "Error setting up date column: \n";
            handleException(e); // Handle error
            LOGGER.log(Level.SEVERE, errorMsg);
        }
    }
//Alert Methods
private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
    /**
     * Sets up event handlers for all the UI components on the window. This method configures actions for buttons, combo boxes, and table selections.
     */
    private void setupEventHandlers() {
        LOGGER.info("Setting up search button action...");
        btnSearch.setOnAction(this::handleSearchAction);
        LOGGER.info("Search button clicked");

        LOGGER.info("Setting up add button action...");
        btnAdd.setOnAction(this::handleCreateAction);
        LOGGER.info("Add button clicked");

        LOGGER.info("Setting up combo box action...");
        comboSearch.setOnAction(this::handleComboSearchAction);
        LOGGER.info("Search combo box selection changed to: " + comboSearch.getValue());

        LOGGER.info("Setting up Delete item.");
        itemDelete.setDisable(false);
        itemDelete.setOnAction(this::handleDeleteAction);
        
        LOGGER.info("Setting up print button.");
        
        btnPrint.setOnAction(this::handlePrintAction);

        LOGGER.info("Setting up selection listener for table...");
        tableConsumes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    boolean hasSelection = newVal != null;
                    itemDelete.setDisable(!hasSelection);
                    LOGGER.info("Selection changed: " + (hasSelection ? "Item selected" : "No item selected"));
                });

        tableConsumes.setEditable(true);
    }

    /**
     * Handles changes in the combo box selection for the search criteria. Updates the prompt text in the search field based on the selected search type.
     *
     * @param event The action event triggered by the combo box selection change.
     */
    private void handleComboSearchAction(Event event) {
        String searchType = comboSearch.getValue();
        updateSearchFieldPrompt(searchType);
    }

    /**
     * Updates the prompt text for the search field based on the selected search type. 
     * The prompt will change based on whether the search type is "Product", "Animal Group", or "Date".
     *
     * @param searchType The type of search selected (Product, Animal Group, or Date).
     */
    private void updateSearchFieldPrompt(String searchType) {
        switch (searchType) {
            case "Product":
                dpSearchFrom.setVisible(false);
                dpSearchTo.setVisible(false);
                searchField.setVisible(true);
                searchField.setPromptText("Enter product name");
                break;
            case "Animal Group":
                dpSearchFrom.setVisible(false);
                dpSearchTo.setVisible(false);
                searchField.setVisible(true);
                searchField.setPromptText("Enter animal group name");
                break;
            case "Date":
                dpSearchFrom.setVisible(true);
                dpSearchTo.setVisible(true);
                searchField.setVisible(false);
                break;
        }
    }

    /**
     * Handles the search action when the search button is clicked.
     * It performs a search based on the selected criteria (Product, Animal Group, Date) and displays the results in the table. 
     * If no search criteria is provided, it retrieves and displays all consumption records.
     *
     * @param event The action event triggered by the search button click.
     */
    private void handleSearchAction(ActionEvent event) {
        try {
            LOGGER.info("Handling search action.");

            String searchText = searchField.getText().trim();
            String searchType = comboSearch.getValue();

            List<ConsumesBean> consumesList = null;

            if (!searchText.isEmpty()) {
                switch (searchType) {
                    case "Product":
                        consumesList = ConsumesManagerFactory.get().findConsumesByProduct(new GenericType<List<ConsumesBean>>() {
                        }, searchText);
                        break;

                    case "Animal Group":
                        consumesList = ConsumesManagerFactory.get().findConsumesByAnimalGroup(new GenericType<List<ConsumesBean>>() {
                        }, searchText);
                        break;

                    case "Date":
                        String from = (dpSearchFrom.getValue() != null && !dpSearchFrom.getValue().toString().isEmpty()) ? dpSearchFrom.getValue().toString() : null;
                        String to = (dpSearchTo.getValue() != null && !dpSearchTo.getValue().toString().isEmpty()) ? dpSearchTo.getValue().toString() : null;

                        if (from != null && to != null) {
                            consumesList = ConsumesManagerFactory.get().getConsumesByDate(new GenericType<List<ConsumesBean>>() {
                            }, from, to);
                        } else if (from != null) {
                            consumesList = ConsumesManagerFactory.get().getConsumesByDateFrom(new GenericType<List<ConsumesBean>>() {
                            }, from);
                        } else if (to != null) {
                            consumesList = ConsumesManagerFactory.get().getConsumesByDateTo(new GenericType<List<ConsumesBean>>() {
                            }, to);
                        } else {
                            showAllConsumes(); 
                        }
                        break;

                    default:
                        LOGGER.warning("Unrecognized search type.");
                        consumesList = consumesClient.getAllConsumes(new GenericType<List<ConsumesBean>>() {
                        });
                        break;
                }
            } else {
                consumesList = ConsumesManagerFactory.get().getAllConsumes(new GenericType<List<ConsumesBean>>() {
                });
            }

            if (consumesList != null && !consumesList.isEmpty()) {
                ObservableList<ConsumesBean> consumesData = FXCollections.observableArrayList(consumesList);
                tableConsumes.setItems(consumesData);
                btnAdd.setDisable(false);
                LOGGER.info("Search completed successfully.");
            } else {
                System.out.println("No consumptions found for the search.");
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Handles the commit of an edit in the table's columns (e.g., when a user changes a value in a table cell).
     * Validates the new value and updates the underlying data model accordingly.
     *
     * @param event The cell edit event triggered by editing a table cell.
     * @param fieldName The field name to which the edited value should be applied (e.g., "consumeAmount", "animalGroup", "product").
     */

    private <T> void handleEditCommit(TableColumn.CellEditEvent<ConsumesBean, T> event, String fieldName) {
    try {
        TablePosition<ConsumesBean, T> pos = event.getTablePosition();
        T newValue = event.getNewValue();
        T oldValue = event.getOldValue();
        if (newValue == null || (newValue instanceof String && ((String) newValue).trim().isEmpty())) {
            throw new IllegalArgumentException("The entered value is invalid.");
        }

        int row = pos.getRow();
        ConsumesBean consume = event.getTableView().getItems().get(row);
        ConsumesBean consumeCopy = (ConsumesBean) consume.clone();

        switch (fieldName) {
             case "animalGroup":
                    if (newValue instanceof AnimalGroupBean) {
                        consumeCopy.setAnimalGroup((AnimalGroupBean) newValue);
                        ConsumesManagerFactory.get().updateConsume(consumeCopy);
                        consume.setAnimalGroup((AnimalGroupBean) newValue);
                    }
                    break;

            case "product":
                if (newValue instanceof ProductBean) {
                    consumeCopy.setProduct((ProductBean) newValue);
                    ConsumesManagerFactory.get().updateConsume(consumeCopy);
                    consume.setProduct((ProductBean) newValue);
                }
                break;

            case "consumeAmount":
                float newValueFloat = ((Number) newValue).floatValue();
                if (newValueFloat >= 0.0f) {  
                     consumeCopy.setConsumeAmount(newValueFloat);
                     consume.setConsumeAmount(newValueFloat);
                     ConsumesManagerFactory.get().updateConsume(consumeCopy);
               } else {
                 float oldValueFloat = ((Number) oldValue).floatValue(); 
                 consume.setConsumeAmount(oldValueFloat);
                 tcConsumeAmount.getTableView().refresh();
               }
               break;
            case "Date":
                 LocalDate today = LocalDate.now(); 

                 LocalDate fromDate = dpSearchFrom.getValue();
                 LocalDate toDate = dpSearchTo.getValue();

                if ((fromDate != null && fromDate.isAfter(today)) || (toDate != null && toDate.isAfter(today))) {
                showAlert("Invalid Date", "The date cannot be later than today.");
                  break; 
 
               } else {
                 showAllConsumes(); 
               }
               break;


            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }

        event.getTableView().refresh();
    } catch (CloneNotSupportedException | IllegalArgumentException | WebApplicationException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
        alert.showAndWait();
        event.consume();
        handleException(e);
    }
}

    /**
     * Updates the consumption date for the currently selected item in the table. 
     * The method creates a clone of the selected `ConsumesBean` item, sets the updated date on the clone,
     * and then updates the original item in the backend and the table.
     *
     * @param updatedDate The updated date to set for the consumption.
     * @throws CloneNotSupportedException If cloning the `ConsumesBean` object fails.
     */
    private void updateConsumeDate(Date updatedDate) throws CloneNotSupportedException {
    ConsumesBean consume = tableConsumes.getSelectionModel().getSelectedItem();

    if (consume != null && updatedDate != null) {
        LocalDate today = LocalDate.now();
        Instant instant = updatedDate.toInstant();
        LocalDate updatedLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        if (updatedLocalDate.isAfter(today)) {
            showAlert("Invalid Date", "The date cannot be later than today.");
            return; 
        }

        ConsumesBean consumeCopy = (ConsumesBean) consume.clone();
        consumeCopy.setDate(updatedDate);

        try {
            ConsumesManagerFactory.get().updateConsume(consumeCopy);
            consume.setDate(updatedDate);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}

    /**
     * Handles the create action. This method is invoked when a user clicks the "Add" button.
     * It creates a new `ConsumesBean` object with initial values, persists it via the backend, and adds it to the table for display.
     * The newly added item is also edited in the column "Quantity".
     *
     * @param event The action event triggered by clicking the "Add" button.
     */
    private void handleCreateAction(ActionEvent event) {
        
        // Create a new ConsumesBean with initial values
        ConsumesBean newConsume = new ConsumesBean();
        newConsume.setProduct(null);  
        newConsume.setAnimalGroup(null); 
        newConsume.setConsumeAmount(0f); 
        newConsume.setDate(new Date());  
        try {
            ConsumesManagerFactory.get().createConsume(newConsume);
        } catch (Exception e) {
           LOGGER.severe("Error occurred while: " + e.getMessage());
            handleException(e);
            return;
        }

        // Add the new consumption to the table
        tableConsumes.getItems().add(newConsume);
        tableConsumes.refresh();

        // Set the "Quantity" column to edit mode for the newly added item
        final int NEW_CONSUME_ROW = tableConsumes.getItems().size() - 1; 
        Platform.runLater(() -> tableConsumes.edit(NEW_CONSUME_ROW, tcAnimalGroup));
    }
private void handleDeleteAction(ActionEvent event) {
    ObservableList<ConsumesBean> selectedConsumes = tableConsumes.getSelectionModel().getSelectedItems();

    if (selectedConsumes.isEmpty()) {
        Alert warningAlert = new Alert(Alert.AlertType.WARNING, "No consumes selected for deletion.", ButtonType.OK);
        warningAlert.showAndWait();
        return;
    }
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to delete the selected consumes?",
            ButtonType.YES, ButtonType.NO);
    Optional<ButtonType> result = confirmationAlert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.YES) {
        
        List<ConsumesBean> successfullyDeleted = new ArrayList<>();
        List<ConsumesBean> failedToDelete = new ArrayList<>(); 

        for (ConsumesBean selectedConsume : selectedConsumes) {
            try {
                String productId = selectedConsume.getProduct().getId().toString();
                String animalGroupId = selectedConsume.getAnimalGroup().getId().toString();

                ConsumesManagerFactory.get().deleteConsume(productId, animalGroupId);
                successfullyDeleted.add(selectedConsume);
            } catch (Exception e) {
                LOGGER.severe("Error occurred while: " + e.getMessage());
                failedToDelete.add(selectedConsume); 
                handleException(e);      
                System.err.println("Unexpected error deleting consume ID " + selectedConsume.getConsumesId() + ": " + e.getMessage());
                failedToDelete.add(selectedConsume); 
                Platform.runLater(() -> {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR,
                            "Unexpected error during deletion: " + e.getMessage(),
                            ButtonType.OK);
                    errorAlert.showAndWait();
                });
            }
        }

        Platform.runLater(() -> {
            if (!successfullyDeleted.isEmpty()) {
                tableConsumes.getItems().removeAll(successfullyDeleted);
                tableConsumes.getSelectionModel().clearSelection();
                tableConsumes.refresh();
            }

            if (!failedToDelete.isEmpty()) {
                String errorMessage = "Some consumes could not be deleted:\n";
                for (ConsumesBean consume : failedToDelete) {
                    errorMessage += "ID: " + consume.getConsumesId() + "\n"; // Incluye el ID
                }
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
                errorAlert.showAndWait();
            }
        });
    }
}
    /**
     * Fetches and displays all consumes from the backend.
     * If no consumes are found, it shows an informational alert.
     * If any error occurs while fetching the data, it shows an error alert.
     */
    private void showAllConsumes() {
        try {
            LOGGER.info("Fetching all consumes...");
            List<ConsumesBean> allConsumes = ConsumesManagerFactory.get().getAllConsumes(new GenericType<List<ConsumesBean>>() {
            });

            if (allConsumes != null && !allConsumes.isEmpty()) {
                LOGGER.info("Consumes fetched successfully. Total items: " + allConsumes.size());
                ObservableList<ConsumesBean> consumesData = FXCollections.observableArrayList(allConsumes);
                tableConsumes.setItems(consumesData);
                btnAdd.setDisable(false);
                LOGGER.info("Table updated with consumes data. Add button enabled.");
            } else {
                LOGGER.info("No consumes found.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No tiene consumos asociados", ButtonType.OK);
                alert.showAndWait();
            }

        } catch (Exception e) {
            LOGGER.severe("Error occurred while fetching consumes: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al cargar los consumos: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Shows an error alert with the specified message.
     *
     * @param message The error message to display in the alert.
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Handles exceptions by displaying the stack trace of the exception and logging the error details.
     *
     * @param e The exception to handle.
     */
    private void handleException(Exception e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        StringBuilder traceBuilder = new StringBuilder();
        for (StackTraceElement element : stackTraceElements) {
            traceBuilder.append(element.toString()).append("\n");
        }
        
        String errorMsg = "Error in search Action Handler: \n" + traceBuilder.toString();
        System.err.println(errorMsg);
        LOGGER.severe("Error occurred while: " + e.getMessage());
    }

    /**
     * Handles the print action. It generates a report based on the current table data and displays it in a JasperViewer window.
     *
     * @param event The action event triggered by clicking the "Print" button.
     */
   private void handlePrintAction(ActionEvent event) {
    try {
        LOGGER.info("Beginning printing action...");

        List<ConsumesBean> consumesList = new ArrayList<>(this.tableConsumes.getItems());
        generateReport(consumesList);

    } catch (Exception ex) {
        showErrorAlert("Error al imprimir:\n" + ex.getMessage());    
    }
   }
    
     private void generateReport(List<ConsumesBean> consumeList) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/ui/reports/ConsumesReport.jrxml"));
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(consumeList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Consumes Report");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    
}

