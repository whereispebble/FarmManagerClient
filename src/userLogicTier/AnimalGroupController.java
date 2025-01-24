/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import DTO.AnimalGroupBean;
import DTO.ManagerBean;
import cellFactories.DatePickerTableCell;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.SelectionMode;

/**
 * FXML Controller class
 *
 * @author Ander
 */
public class AnimalGroupController implements Initializable {

    /**
     * Logger to track the class activity and handle debugging information.
     */
    private static final Logger logger = Logger.getLogger(AnimalGroupController.class.getName());

    private Stage stage;

    private ManagerBean manager;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField searchField;

    @FXML
    private TableView tbAnimalGroup;

    @FXML
    private TableColumn<AnimalGroupBean, String> tcName;

    @FXML
    private TableColumn<AnimalGroupBean, String> tcDescription;

//    @FXML
//    private TableColumn tcAnimals;
    @FXML
    private TableColumn tcConsume;

    @FXML
    private TableColumn tcDate;

    @FXML
    private TableColumn<AnimalGroupBean, String> tcArea;

    private ObservableList<AnimalGroupBean> groupData;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ////////////////////////////////////////////
        manager = new ManagerBean();
        manager.setId(1L);
        System.out.println(manager.getId());
        ////////////////////////////////////////////

        tbAnimalGroup.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        logger.log(Level.INFO, "Initilizing Animal Group controller");

        //Uncomment next lanes when necessary
//        // Establecer el título de la ventana
//            stage.setTitle("Animal Groups");
//
//            // Establecer dimensiones fijas
//            stage.setWidth(1024);
//            stage.setHeight(720);
//
//            // Deshabilitar la redimensión de la ventana
//            stage.setResizable(false);
        searchField.requestFocus();
        btnSearch.setDefaultButton(true);

        // BUTTONS
        btnSearch.setOnAction(this::onSearchButtonClicked);
        btnCreate.setOnAction(this::onCreateButtonClicked);

        logger.log(Level.INFO, "Setting cell value factories");

        // COLUMNS
        // Name column
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcName.setCellFactory(TextFieldTableCell.<AnimalGroupBean>forTableColumn());
        tcName.setOnEditCommit(event -> handleEditCommit(event, "name"));

        // Description column
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcDescription.setCellFactory(TextFieldTableCell.<AnimalGroupBean>forTableColumn());
        tcDescription.setOnEditCommit(event -> handleEditCommit(event, "description"));

        // Area column
        tcArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        tcArea.setCellFactory(TextFieldTableCell.<AnimalGroupBean>forTableColumn());
        tcArea.setOnEditCommit(event -> handleEditCommit(event, "area"));

        // Animals column
//            tcAnimals.setCellValueFactory(new PropertyValueFactory<>("animals"));
//            tcAnimals.setStyle("-fx-alignment: center;");
        // Consumes column
        tcConsume.setCellValueFactory(new PropertyValueFactory<>("consume"));
        tcConsume.setStyle("-fx-alignment: center;");

        // Creation date column
        tcDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        tcDate.setStyle("-fx-alignment: center;");
        tcDate.setCellFactory(new Callback<TableColumn<AnimalGroupBean, Date>, TableCell<AnimalGroupBean, Date>>() {
            @Override
            public TableCell<AnimalGroupBean, Date> call(TableColumn<AnimalGroupBean, Date> param) {
                DatePickerTableCell<AnimalGroupBean> cell = new DatePickerTableCell<>(param);
                cell.updateDateCallback = (Date updatedDate) -> {
                    try {
                        updateAnimalGroup(updatedDate);
                    } catch (CloneNotSupportedException ex) {
                        logger.log(Level.SEVERE, "Error updating animal group: ", ex);
                    }
                };
                return cell;
            }
        });

        // Get animals
        //List<AnimalBean> animalList = AnimalManagerFactory.get().getAnimalsByAnimalGroup(new GenericType<List<AnimalBean>>() {}, "North Cows");
//            logger.log(Level.INFO, "Printing animal list");
//            for (AnimalBean ab : animalList) {
//                logger.log(Level.INFO, ab.toString());
//            }
        tbAnimalGroup.setEditable(true);

        showAnimalGroups();
    }

    private <T> void handleEditCommit(CellEditEvent<AnimalGroupBean, T> event, String column) {
        try {
            // Get the position of the event
            TablePosition<AnimalGroupBean, T> pos = event.getTablePosition();
            T newValue = event.getNewValue();

            // Control that the value is a String or is not empty
            if (newValue == null || (newValue instanceof String && ((String) newValue).trim().isEmpty())) {
                throw new IllegalArgumentException("The value is not valid");
            }

            // Get the position of the row from the position of the event
            int row = pos.getRow();
            // Get the object from the row
            AnimalGroupBean group = event.getTableView().getItems().get(row);
            // Make a copy of the object
            AnimalGroupBean groupCopy = group.clone();

            switch (column) {
                case "name":
                    if (newValue instanceof String) {
                        groupCopy.setName((String) newValue);
                        AnimalGroupFactory.get().updateAnimalGroup(groupCopy);
                        group.setName((String) newValue);
                    }
                    break;
                case "description":
                    if (newValue instanceof String) {
                        groupCopy.setDescription((String) newValue);
                        AnimalGroupFactory.get().updateAnimalGroup(groupCopy);
                        group.setDescription((String) newValue);
                    }
                    break;
                case "area":
                    if (newValue instanceof String) {
                        groupCopy.setArea((String) newValue);
                        AnimalGroupFactory.get().updateAnimalGroup(groupCopy);
                        group.setArea((String) newValue);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Campo desconocido: " + column);
            }

            event.getTableView().refresh();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error editing Animal Group: ", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error editing Animal Group");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void updateAnimalGroup(Date updatedDate) throws CloneNotSupportedException {
        AnimalGroupBean group = (AnimalGroupBean) tbAnimalGroup.getSelectionModel().getSelectedItem();
        if (group != null && updatedDate != null) {
            AnimalGroupBean groupCopy = group.clone();
            groupCopy.setCreationDate(updatedDate);
            try {
                AnimalGroupFactory.get().updateAnimalGroup(groupCopy);
                group.setCreationDate(updatedDate);
                logger.log(Level.INFO, "Animal group updated. New Date: {0}", group.getCreationDate());
            } catch (WebApplicationException e) {
                logger.log(Level.SEVERE, "Error updating", e);
            }
        }
    }

    private void onSearchButtonClicked(ActionEvent event) {
        try {
            List<AnimalGroupBean> groupList = null;
            if (searchField.getText() != null && !searchField.getText().isEmpty()) {
                logger.log(Level.INFO, "Searching group/s by name and manager id");
                groupList = AnimalGroupFactory.get().getAnimalGroupByName(new GenericType<List<AnimalGroupBean>>() {
                }, searchField.getText(), manager.getId().toString());
                logger.log(Level.INFO, "Group/s gotten");
            } else {
                showAnimalGroups();
            }

            if (groupList != null) {
                ObservableList<AnimalGroupBean> groupData = FXCollections.observableArrayList(groupList);
                tbAnimalGroup.setItems(groupData);
            } else {
                logger.log(Level.WARNING, "No animal groups found");
            }
        } catch (WebApplicationException e) {
            logger.log(Level.SEVERE, "Error fetching animal groups: ", e);
        }
    }

    @FXML
    private void onCreateButtonClicked(ActionEvent event) {
        try {
            // Create new group
            AnimalGroupBean group = new AnimalGroupBean();
            // Set attributes
            String groupName = "Group " + (int) (Math.random() * 1000000);
            group.setName(groupName);
            group.setDescription("New animal group");
            group.setArea("Not defined yet");
            // Make a list for the managers (just actual manager but has to be a list)
            List<ManagerBean> managers = new ArrayList<>();
            managers.add(manager);
            group.setManagers(managers);
            // Actual date
            group.setCreationDate(new Date());

            logger.log(Level.SEVERE, "Creating animal group: {0}", group.toString());
            AnimalGroupFactory.get().createAnimalGroup(group);

            showAnimalGroups();

            // Set in edition mode the new animal group (searched by the new name)
            final int frow;
            for (int row = 0; row < tbAnimalGroup.getItems().size(); row++) {
                group = (AnimalGroupBean) tbAnimalGroup.getItems().get(row);
                if (group.getName().equals(groupName)) {
//                tbAnimalGroup.edit(row, tcName);

                    frow = row;
                    Platform.runLater(() -> tbAnimalGroup.edit(frow, tcName));

                    System.out.println(tbAnimalGroup.getEditingCell());

//                tbAnimalGroup.getEditingCell().startEdit();
//                tbAnimalGroup.edit(row, TableColumn<AnimalGroupBean, String> tcName);
                    tbAnimalGroup.refresh();
                    break;
                }
            }

        } catch (WebApplicationException e) {
            logger.log(Level.SEVERE, "Error creating animal group", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Cannot create the animal group");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void showAnimalGroups() {
        try {
            // Get animal groups
            logger.log(Level.INFO, "Getting animal groups");
            List<AnimalGroupBean> groupList = AnimalGroupFactory.get().getAnimalGroupsByManager(new GenericType<List<AnimalGroupBean>>() {
            }, manager.getId().toString());
            logger.log(Level.INFO, "Animal groups gotten");
            // for testing purposes
//            logger.log(Level.INFO, "Printing list");
//            for (AnimalGroupBean agb : groupList) {
//                logger.log(Level.INFO, agb.toString());
//            }

            // Show animal groups
            groupData = FXCollections.observableArrayList(groupList);
            tbAnimalGroup.setItems(groupData);

        } catch (WebApplicationException e) {
            logger.log(Level.SEVERE, "Error fetching animal groups", e);
        }
    }
}
