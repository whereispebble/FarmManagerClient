/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import DTO.AnimalGroupBean;
import DTO.ManagerBean;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import ui.utilities.WindowManager;

/**
 * FXML Controller class
 *
 * @author Aitziber
 */
public class MenuController implements Initializable {

    private static final Logger logger = Logger.getLogger(MenuController.class.getName());
    
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem miAnimal;
    @FXML
    private MenuItem miAnimalGroup;
    @FXML
    private MenuItem miConsume;
    @FXML
    private MenuItem miProduct;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        miAnimalGroup.setOnAction(this::handleAnimalGroup);
        miAnimal.setOnAction(this::handleAnimal);
        miConsume.setOnAction(this::handleConsume);
        miProduct.setOnAction(this::handleProduct);
    }
     private void handleAnimalGroup(ActionEvent event) {
        try {
            openWindow("AnimalGroup.fxml", "Animal Group");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAnimal(ActionEvent event) {
        try {
            openWindow("Animal.fxml", "Animal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void handleConsume(ActionEvent event) {
        try {
            openWindow("Consume.fxml", "Consume");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void handleProduct(ActionEvent event) {
        try {
            openWindow("Product.fxml", "Product");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openWindow(String fxmlFile, String windowTitle) throws Exception {
        
        Stage stage = (Stage) menuBar.getScene().getWindow();
        System.out.println(stage.getTitle());
        stage.hide();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/view/" + fxmlFile));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/ui/view/styles.css").toExternalForm());
        
        Stage st = new Stage();
        st.setScene(scene);
        st.setResizable(false);
        st.setTitle(windowTitle);
        
        st.show();
    }
    
}