/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import DTO.AnimalGroupBean;
import DTO.ManagerBean;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mailing.MailingService;
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
    private Menu menuNavigateTo;
    @FXML
    private MenuItem miAnimal;
    @FXML
    private MenuItem miAnimalGroup;
    @FXML
    private MenuItem miConsume;
    @FXML
    private MenuItem miProduct;
    @FXML
    private Menu menuReport;
    @FXML
    private MenuItem miPrint;
    @FXML
    private Menu menuHelp;
    @FXML
    private Menu menuProfile;
    @FXML
    private MenuItem miReset;
    @FXML
    private MenuItem miLogOut;

    private static ManagerBean manager;

    /**
     * Sets the manager instance for this session.
     *
     * @param manager the manager to be set
     */
    public static void setManager(ManagerBean manager) {
        MenuController.manager = manager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        miAnimalGroup.setOnAction(event -> openView("/ui/view/AnimalGroup.fxml", "Animal Groups"));
        miAnimal.setOnAction(event -> openView("/ui/view/Animal.fxml", "Animals"));
        miConsume.setOnAction(event -> openView("/ui/view/Consumes.fxml", "Consumes"));
        miProduct.setOnAction(event -> openView("/ui/view/Product.fxml", "Product"));

        miReset.setOnAction(this::handleResetAction);
        miLogOut.setOnAction(this::handleLogOutAction);
    }

    /**
     * Handles the window transition and hides the current stage.
     *
     * @param fxmlPath the FXML file to load
     * @param title the title of the new window
     */
    private void openView(String fxmlPath, String title) {
        try {
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.hide();
            logger.log(Level.INFO, "Opening window");
            WindowManager.openWindowWithManager(fxmlPath, title, manager);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error opening {0} window: {1}", new Object[]{title, e.getMessage()});
        }
    }

    private void handleResetAction(ActionEvent event) {
        logger.info("reset click");
        MailingService ms = new MailingService();
        boolean sent = ms.sendEmail(manager.getEmail());
    }

    /**
     * Handles the log out action by closing the current window and opening the SignIn window.
     *
     * @param event the action event triggered by clicking the log out button.
     */
    private void handleLogOutAction(ActionEvent event) {
        logger.log(Level.INFO, "Log out action initiated");
        if (showLogoutConfirmation()) {
            try {
                Stage home = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
                home.close();
                WindowManager.openWindow("/ui/view/SignIn.fxml", "SignIn");
                logger.log(Level.INFO, "Successfully navigated to Sign In screen.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Unexpected error during logout process.", e);
            }
        } else {
            logger.log(Level.INFO, "Log out cancelled by user.");
        }
    }

    /**
     * Displays a confirmation dialog for log out.
     *
     * @return {@code true} if the user confirms log out; {@code false} otherwise.
     */
    private boolean showLogoutConfirmation() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Log Out");
            alert.setHeaderText("This window will be closed.");
            alert.setContentText("Are you sure you want to log out?");
            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error showing logout confirmation", e);
            return false;
        }
    }
}
