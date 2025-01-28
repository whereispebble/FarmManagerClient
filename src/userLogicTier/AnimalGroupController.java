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
import DTO.AnimalBean;

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

    private String managerId;

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

    @FXML
    private TableColumn tcAnimals;

    @FXML
    private TableColumn tcConsume;

    @FXML
    private TableColumn tcDate;

    @FXML
    private TableColumn<AnimalGroupBean, String> tcArea;

    private ObservableList<AnimalGroupBean> groupData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
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
            tcAnimals.setCellValueFactory(new PropertyValueFactory<>("animals"));
            tcAnimals.setStyle("-fx-alignment: center;");

            // Consume column
            tcConsume.setCellValueFactory(new PropertyValueFactory<>("consume"));
            tcConsume.setStyle("-fx-alignment: center;");

            // Creation date column
            tcDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
            tcDate.setStyle("-fx-alignment: center;");
            tcDate.setCellFactory(new Callback<TableColumn<AnimalGroupBean, Date>, TableCell<AnimalGroupBean, Date>>() {
                @Override
                public TableCell<AnimalGroupBean, Date> call(TableColumn<AnimalGroupBean, Date> param) {
                    return new TableCell<AnimalGroupBean, Date>() {
                        @Override
                        protected void updateItem(Date item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                setText(sdf.format(item));
                            }
                        }
                    };
                }
            });

            List<AnimalGroupBean> groupList = new ArrayList<>();

            logger.log(Level.INFO, "Sending request");

            managerId = "2";
            groupList = AnimalGroupFactory.get().getAnimalGroupsByManager(new GenericType<List<AnimalGroupBean>>() {
            }, managerId);

            List<AnimalBean> animalList = new ArrayList<>();
            // Uncoment when AnimalManagerFactory, AnimmalRestClient are added
//            animalList = AnimalManagerFactory.get().getAnimalsByAnimalGroup(new GenericType<List<AnimalBean>>() {}, "North Cows");

            logger.log(Level.INFO, "Request sent");

            // for testing purposes
//            logger.log(Level.INFO, "Printing list");
//            for (AnimalGroupBean agb : groupList) {
//                System.out.println(agb.toString());
//            }
            groupData = FXCollections.observableArrayList(groupList);
            tbAnimalGroup.setItems(groupData);

            tbAnimalGroup.setEditable(true);

        } catch (WebApplicationException e) {
            logger.log(Level.SEVERE, "Error fetching animal groups: ", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Animal Group data not found");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error editing Animal Group");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
