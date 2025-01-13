/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import DTO.AnimalBean;
import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aitziber
 */
public class AnimalController implements Initializable {
    
    @FXML
    private Stage stage;
    
    @FXML
    private ComboBox<String> comboSearch;
    
    @FXML
    private TextField tfSearch;
    
    @FXML
    private Button btnSearch;

    @FXML
    private Button btnAdd;
    
    @FXML
    private Label lblFrom;
    
    @FXML
    private Label lblTo;
    
    @FXML
    private DatePicker dpSearchFrom;

    @FXML
    private DatePicker dpSearchTo;
    
    @FXML
    private TableView tbAnimal;
    
    @FXML
    private TableColumn tcName;
    @FXML
    private TableColumn tcBirthdate;
    @FXML
    private TableColumn tcAnimalGroup;
    @FXML
    private TableColumn tcSubespecies;
    @FXML
    private TableColumn tcSpecies;
    @FXML
    private TableColumn tcConsume;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ObservableList 
        
        
        // Establecer el título de la ventana
        stage.setTitle("Animals");

        // Establecer dimensiones fijas
        stage.setWidth(1024);
        stage.setHeight(720);

        // Deshabilitar la redimensión de la ventana
        stage.setResizable(false);
        
        // Limpiar los campos de fechas
        dpSearchFrom.setValue(null);
        dpSearchTo.setValue(null);

        // Hacer los campos de fechas invisibles
        dpSearchFrom.setVisible(false);
        dpSearchTo.setVisible(false);
        
        // Cargar los elementos en el combo de búsqueda
        comboSearch.getItems().addAll("Subespecies", "Animal Group", "Birthdate");

        // Seleccionar por defecto "Animal Group"
        comboSearch.setValue("Animal Group");
        
        // Escuchar cambios en el ComboBox
        comboSearch.valueProperty().addListener(this::handleComboBoxChange);

        // Limpiar y habilitar el campo tfSearch
        tfSearch.clear();
        tfSearch.setDisable(false);
        
        // Enfocar el campo de búsqueda
        tfSearch.requestFocus();  
        
         // Habilitar los botones
        btnSearch.setDisable(false);
        btnAdd.setDisable(false);

        // Establecer "Search" como el botón por defecto
        btnSearch.setDefaultButton(true);

        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        tcAnimalGroup.setCellValueFactory(new PropertyValueFactory<>("animalGroup"));
        tcSubespecies.setCellValueFactory(new PropertyValueFactory<>("subespecies"));
        tcSpecies.setCellValueFactory(new PropertyValueFactory<>("species"));
        tcConsume.setCellValueFactory(new PropertyValueFactory<>("monthlyonsume"));
        tcConsume.setStyle("-fx-alignment: center-right;");
        
        //set data for table view
//        List<AnimalBean> animals=((Account)cbAccount.getSelectionModel()
//                                .getSelectedItem()).getMovements();
        
        
        stage.show();

        
    }    
    
     // Manejador de cambios en el ComboBox
    private void handleComboBoxChange(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if ("Birthdate".equals(newValue)) {
            // Ocultar el campo de búsqueda de texto
            tfSearch.setVisible(false);
            tfSearch.clear();
            
            // Mostrar los campos de fecha
            lblFrom.setVisible(true);
            lblTo.setVisible(true);
            dpSearchFrom.setVisible(true);
            dpSearchTo.setVisible(true);
        } else {            
            // Ocultar los campos de fecha
            lblFrom.setVisible(false);
            lblTo.setVisible(false);
            dpSearchFrom.setVisible(false);
            dpSearchTo.setVisible(false);
            dpSearchFrom.clear();
            dpSearchTo.clear();
            
            // Mostrar el campo de búsqueda de texto
            tfSearch.setVisible(true);
        }
    }
    
}
