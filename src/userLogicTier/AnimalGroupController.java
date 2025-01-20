/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import model.AnimalGroupBean;

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
    private TableView tbAnimalGroup;

    @FXML
    private TableColumn tcName;

    @FXML
    private TableColumn tcDescription;

    @FXML
    private TableColumn tcAnimals;

    @FXML
    private TableColumn tcConsume;

    @FXML
    private TableColumn tcDate;
    
    @FXML
    private TableColumn tcArea;

    private ObservableList<AnimalGroupBean> groupData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            logger.log(Level.INFO, "Initilizing Animal Group controller");

            //Uncomment next lines when necessary
//        // Establecer el título de la ventana
//        stage.setTitle("Animal Groups");
//        
//        // Establecer dimensiones fijas
//        stage.setWidth(1024);
//        stage.setHeight(720);
//        
//        // Deshabilitar la redimensión de la ventana
//        stage.setResizable(false);
            logger.log(Level.INFO, "Setting cell value factories");

            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            tcAnimals.setCellValueFactory(new PropertyValueFactory<>("animals"));
            tcConsume.setCellValueFactory(new PropertyValueFactory<>("consume"));
            tcDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
            tcArea.setCellValueFactory(new PropertyValueFactory<>("area"));

            List<AnimalGroupBean> groupList = new ArrayList<AnimalGroupBean>();
            managerId = "1";

            logger.log(Level.INFO, "Sending request");

            groupList = AnimalGroupFactory.get().getAnimalGroupsByManager(new GenericType<List<AnimalGroupBean>>() {
            }, managerId);

            logger.log(Level.INFO, "Request sent");

            // for testing purposes
            logger.log(Level.INFO, "Printing list");
            for (AnimalGroupBean agb : groupList) {
                System.out.println(agb.toString());
            }

            groupData = FXCollections.observableArrayList(groupList);
            tbAnimalGroup.setItems(groupData);
            tbAnimalGroup.setEditable(false);

        } catch (WebApplicationException e) {
            logger.log(Level.SEVERE, "Error fetching animal groups: ", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Animal Group data not found");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
