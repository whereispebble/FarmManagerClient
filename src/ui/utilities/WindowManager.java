/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utilities;

import DTO.AnimalGroupBean;
import DTO.ManagerBean;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.controller.AnimalController;
import ui.controller.AnimalGroupController;

/**
 * Utility class for managing the opening of new windows in the application. This class provides methods to open different windows (scenes) based on the provided FXML file path and title. It also allows passing a user object to the new window for context.
 *
 * @author inifr
 * @author Aitziber
 * @author Ander
 */
public class WindowManager {

    private static final Logger logger = Logger.getLogger(WindowManager.class.getName());

    public static void openWindow(String fxmlFilePath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.getIcons().add(new Image("resources/logo.png"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error opening window: {0}", e.getMessage());
        }
    }

    public static void openAnimalViewWithAnimalGroup(String fxmlFilePath, String title, ManagerBean manager, AnimalGroupBean animalGroup) {
        try {
            if (AnimalController.getManager() == null){
                AnimalController.setManager(manager); 
            }
            AnimalController.setConditionalAnimalGroup(animalGroup);

            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image("resources/logo.png"));
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error opening window: {0}", e);
        }
    }

    public static void openWindowWithManager(String fxmlFilePath, String title, ManagerBean manager) {
        try {
            //coleccion de controladores e iterarla seteando igual, ver cómo hacerlo sin instanceof y cambiar los ififif
            
            if (AnimalGroupController.getManager() == null){
                AnimalGroupController.setManager(manager); 
            }
            if (AnimalController.getManager() == null){
                AnimalController.setManager(manager); 
            }
//            if (ProductController.getManager() == null){
//                ProductController.setManager(manager); 
//            }
//            if (ConsumesController.getManager() == null){
//                ConsumesController.setManager(manager); 
//            }
            
            AnimalController.setConditionalAnimalGroup(null);
            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.getIcons().add(new Image("resources/logo.png"));
            stage.setScene(new Scene(root));
            // Deshabilitar la redimensión de la ventana
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error opening window: {0}", e);
        }
    }
}
