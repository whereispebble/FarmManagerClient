/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import java.awt.Button;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AnimalGroupBean;

/**
 * FXML Controller class
 *
 * @author Ander
 */
public class AnimalGroupController implements Initializable {

    private Stage stage;

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

    private ObservableList<AnimalGroupBean> groupData;

    protected IAnimalGroup groupManager;

    public void setGroups(IAnimalGroup groupManager) {
        this.groupManager = groupManager;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcAnimals.setCellValueFactory(new PropertyValueFactory<>("animals"));
        tcConsume.setCellValueFactory(new PropertyValueFactory<>("consume"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));

//        List<AnimalGroupBean> groupList = new ArrayList<>();
//        groupData = FXCollections.observableArrayList(groupManager.getAnimalGroupsByManager(responseType, 1));
        tbAnimalGroup.setItems(groupData);
        tbAnimalGroup.setEditable(false);
    }

}
