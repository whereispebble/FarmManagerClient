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
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
//    private ManagerBean manager;
//    
//    private static final Logger logger = Logger.getLogger(MenuController.class.getName());
//    
//    @FXML
//    private MenuBar menuBar;
//    @FXML
//    private MenuItem miAnimal;
//    @FXML
//    private MenuItem miAnimalGroup;
//    @FXML
//    private MenuItem miConsume;
//    @FXML
//    private MenuItem miProduct;
//    @FXML
//    private MenuItem miLogOut;
//    
//    public void setManager(ManagerBean manager) {
//        this.manager = manager;
//        logger.log(Level.INFO, "Manager {0}", manager.toString());
//    }
//      
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//        // F1 para lanzar ayuda
//        // HelpController
//        miAnimal.setOnAction(event -> handleNavigation("Animal"));
//        miAnimalGroup.setOnAction(event -> handleNavigation("AnimalByAnimalGroup"));
//        miConsume.setOnAction(event -> handleNavigation("Consumes"));
//        miProduct.setOnAction(event -> handleNavigation("Product"));
//        miLogOut.setOnAction(event -> handleLogOut());
//    }
//
//   private void handleNavigation(String view) {
//        try {
//            switch (view) {
//                case "AnimalByAnimalGroup":
//                    // Obtain selected animal group (replace with real selection logic)
//                    AnimalGroupBean group = getSelectedAnimalGroup();
//                    if (group == null) {
//                        throw new NullPointerException("No animal group selected.");
//                    }
//                    logger.log(Level.INFO, "Opening animal group: {0}", group.getName());
//                    closeCurrentWindow();
//                    WindowManager.openAnimalViewWithAnimalGroup(
//                            "/ui/view/Animal.fxml", "Animal", manager, group);
//                    break;
//                case "Animal":
//                    closeCurrentWindow();
//                    WindowManager.openWindowWithManager(
//                            "/ui/view/Animal.fxml", "Animal", manager, view);
//                    break;
//                case "Consumes":
//                    closeCurrentWindow();
//                    WindowManager.openWindowWithManager(
//                            "/ui/view/Consumes.fxml", "Consumes", manager, view);
//                    break;
//                case "Product":
//                    closeCurrentWindow();
//                    WindowManager.openWindowWithManager(
//                            "/ui/view/Product.fxml", "Product", manager, view);
//                    break;
//                default:
//                    throw new IllegalArgumentException("Unknown or wrong view: " + view);
//            }
//        } catch (NullPointerException e) {
//            logger.log(Level.SEVERE, "Error opening window: ", e);
//        }
//    }
//
//    private void handleLogOut() {
//        closeCurrentWindow();
//        WindowManager.openWindow("/userInterfaceTier/Login.fxml", "Login");
//    }
//
//    private void closeCurrentWindow() {
//        Stage currentStage = (Stage) menuBar.getScene().getWindow();
//        currentStage.close();
//    }
//
//    private AnimalGroupBean getSelectedAnimalGroup() {
//        // This is a placeholder. Replace it with your actual logic to get the selected animal group.
//        // Example: return (AnimalGroupBean) tbAnimalGroup.getSelectionModel().getSelectedItem();
//        return null; // Modify this!
//    }
}