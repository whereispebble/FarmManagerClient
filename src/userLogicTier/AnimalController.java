/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import DTO.AnimalBean;
import DTO.SpeciesBean;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * FXML Controller class
 *
 * @author Aitziber
 */
public class AnimalController implements Initializable {
    
    
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
        try {  
    //        // Establecer el título de la ventana
    //        stage.setTitle("Animals");
    //
    //        // Establecer dimensiones fijas
    //        stage.setWidth(1024);
    //        stage.setHeight(720);

    //        // Deshabilitar la redimensión de la ventana
    //        stage.setResizable(false);

    //        // Limpiar los campos de fechas
    //        dpSearchFrom.setValue(null);
    //        dpSearchTo.setValue(null);
    //
    //        // Hacer los campos de fechas invisibles
    //        dpSearchFrom.setVisible(false);
    //        dpSearchTo.setVisible(false);

            // Cargar los elementos en el combo de búsqueda
    //        comboSearch.getItems().addAll("Subespecies", "Animal Group", "Birthdate");
    //
    //        // Seleccionar por defecto "Animal Group"
    //        comboSearch.setValue("Animal Group");
    //        
    //        // Escuchar cambios en el ComboBox
    ////        comboSearch.valueProperty().addListener(this::handleComboBoxChange);
    //
    //        // Limpiar y habilitar el campo tfSearch
    //        tfSearch.clear();
    //        tfSearch.setDisable(false);
    //        
    //        // Enfocar el campo de búsqueda
    //        tfSearch.requestFocus();  
    //        
    //         // Habilitar los botones
    //        btnSearch.setDisable(false);
    //        btnAdd.setDisable(false);
    //
    //        // Establecer "Search" como el botón por defecto
    //        btnSearch.setDefaultButton(true);
    //

            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
            // Formatear la fecha en dd/MM/yyyy
            tcBirthdate.setCellFactory(new Callback<TableColumn<AnimalBean, Date>, javafx.scene.control.TableCell<AnimalBean, Date>>() {
                @Override
                public javafx.scene.control.TableCell<AnimalBean, Date> call(TableColumn<AnimalBean, Date> param) {
                    return new javafx.scene.control.TableCell<AnimalBean, Date>() {
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
            tcBirthdate.setStyle("-fx-alignment: center;");

    //        Animal Group y Species: combo no editable con el valor del nombre 
    //        del Animal Group y el valor del nombre de la Species asociados 
    //        seleccionados. Edición ComboBoxTableCell

            tcAnimalGroup.setCellValueFactory(new PropertyValueFactory<>("animalGroup"));
            tcSubespecies.setCellValueFactory(new PropertyValueFactory<>("subespecies"));


            tcSpecies.setCellValueFactory(new PropertyValueFactory<>("species"));
            List<SpeciesBean> speciesList = new ArrayList<SpeciesBean>();
            speciesList = SpeciesManagerFactory.get().getAllSpecies(new GenericType<List<SpeciesBean>>() {});             
            ObservableList<SpeciesBean> speciesData = FXCollections.observableArrayList(speciesList);
            tcSpecies.setCellFactory(ComboBoxTableCell.forTableColumn(speciesData));

    //       tcSpecies.setOnEditCommit(event -> {
    //           AnimalBean animal = event.getRowValue();
    //           animal.setSpecies(event.getNewValue());
    //           System.out.printf("Species updated for %s to %s%n", animal.getName(), event.getNewValue());
    //       });

            tcConsume.setCellValueFactory(new PropertyValueFactory<>("monthlyConsume"));
            tcConsume.setStyle("-fx-alignment: center-right;");

            List<AnimalBean> animalList = new ArrayList<AnimalBean>();
            animalList = AnimalManagerFactory.get().getAnimalsByAnimalGroup(new GenericType<List<AnimalBean>>() {}, "North Cows");
    //        for (AnimalBean ab:animalList){
    //            System.out.println(ab.toString());
    //        }
            ObservableList<AnimalBean> animalData = FXCollections.observableArrayList(animalList);
            tbAnimal.setItems(animalData);

            tbAnimal.setEditable(true);
            //        stage.show(); 
         
        } catch (WebApplicationException e) {
            System.err.println("Error fetching animals: " + e.getMessage());
        }
    }    
    
//    Manejador de cambios en el ComboBox
//    private void handleComboBoxChange(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//        if ("Birthdate".equals(newValue)) {
//            // Ocultar el campo de búsqueda de texto
//            tfSearch.setVisible(false);
//            tfSearch.clear();
//            
//            // Mostrar los campos de fecha
//            lblFrom.setVisible(true);
//            lblTo.setVisible(true);
//            dpSearchFrom.setVisible(true);
//            dpSearchTo.setVisible(true);
//        } else {            
//            // Ocultar los campos de fecha
//            lblFrom.setVisible(false);
//            lblTo.setVisible(false);
//            dpSearchFrom.setVisible(false);
//            dpSearchTo.setVisible(false);
////            dpSearchFrom.clear();
////            dpSearchTo.clear();
//            
//            // Mostrar el campo de búsqueda de texto
//            tfSearch.setVisible(true);
//        }
    }
    
//    private void onSearchButtonClicked() {
//        String searchType = comboSearch.getValue();
//        try {
//            List<AnimalBean> animalList = null;
//
//            switch (searchType) {
//                case "Animal Group":
//                    animalList = AnimalManagerFactory.get().getAnimalsByAnimalGroup(AnimalBean.class, tfSearch.getText());
//                    break;
//                case "Subespecies":
//                    animalList = AnimalManagerFactory.get().getAnimalsBySubespecies(AnimalBean.class, tfSearch.getText());
//                    break;
//                case "Birthdate":
//                    String from = dpSearchFrom.getValue().toString();
//                    String to = dpSearchTo.getValue().toString();
//                    animalList = AnimalManagerFactory.get().getAnimalsByBirthdate(AnimalBean.class, from, to);
//                    break;
//            }
//
//            if (animalList != null) {
//                ObservableList<AnimalBean> animalData = FXCollections.observableArrayList(animalList);
//                tbAnimal.setItems(animalData);
//            }
//        } catch (Exception e) {
//            System.err.println("Error fetching data: " + e.getMessage());
//        }
//    }

    

