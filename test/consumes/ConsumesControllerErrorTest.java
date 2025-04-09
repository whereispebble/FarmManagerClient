/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumes;



import DTO.*;
import java.io.ByteArrayOutputStream;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;

import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import org.testfx.util.WaitForAsyncUtils;
import ui.controller.ConsumesController;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConsumesControllerErrorTest extends ApplicationTest {

  
    private static final Logger LOGGER = Logger.getLogger(ConsumesControllerTest.class.getName());
    private static boolean loggedIn = false;
    private ConsumesController controller;
    private Stage primaryStage; 
    private FXMLLoader fxmlLoader;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage; 
        fxmlLoader = new FXMLLoader();
        if (!loggedIn) {
            fxmlLoader.setLocation(getClass().getResource("/ui/view/SignIn.fxml")); 
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            LOGGER.log(Level.FINE, "Signed In");
        } 
    }



    @Test
    public void testA_OpenConsumesView() throws Exception {
        if (!loggedIn) {
            clickOn("#tfUsername").write("pablo@paia.com");
            verifyThat("#tfUsername", hasText("pablo@paia.com"));

            clickOn("#pfPasswd").write("12345678");
            verifyThat("#pfPasswd", hasText("12345678"));

            clickOn("#btnSignIn");
            loggedIn = true;

       
         verifyThat("#menuBar", isVisible());
          clickOn("#menuNavigateTo");
          clickOn("#miConsume");
          LOGGER.log(Level.FINE, "Consumes opened");
    }
     }
    
   
    
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    
    @Test
     public void testB_UpdateAmount() throws Exception {
     
     
    WaitForAsyncUtils.waitForFxEvents(); 
    TableView<ConsumesBean> table = lookup("#tableConsumes").query();   
   
    int lastRowIndex = table.getItems().size() - 1;
    table.getSelectionModel().select(lastRowIndex);
    WaitForAsyncUtils.waitForFxEvents();
     
      Node tcConsumeAmount = lookup("#tcConsumeAmount").nth(lastRowIndex + 1).query();
    doubleClickOn(tcConsumeAmount);
    write("-123.45f");
    push(KeyCode.ENTER);
    
   try {

    DialogPane dialogPane = lookup(".dialog-pane").query();
    

    boolean titleFound = false;
    boolean messageFound = false;
    

    Set<Node> allLabels = lookup(".dialog-pane .label").queryAll();
    for (Node node : allLabels) {
        if (node instanceof Labeled) {
            String text = ((Labeled) node).getText();
            if (text != null) {

                if (text.contains("Invalid Format")) {
                    titleFound = true;
                }
                if (text.contains("Value must be 0.0 or greater.")) {
                    messageFound = true;
                }
            }
        }
    }

    Window window = dialogPane.getScene().getWindow();
    if (window instanceof Stage) {
        String windowTitle = ((Stage) window).getTitle();
        if (windowTitle != null && windowTitle.contains("Invalid Format")) {
            titleFound = true;
        }
    }

    assertTrue("No se encontró el texto 'Invalid Format' en el diálogo", titleFound);
    assertTrue("No se encontró el mensaje 'Value must be 0.0 or greater.' en el diálogo", messageFound);
    
} catch (NoSuchElementException e) {
    fail("No se mostró ningún diálogo de alerta");
}
    push(KeyCode.ENTER);

     }
    
       @Test
     public void testC_UpdateDate() throws Exception {
         
           WaitForAsyncUtils.waitForFxEvents(); 
    TableView<ConsumesBean> table = lookup("#tableConsumes").query();   
   
    int lastRowIndex = table.getItems().size() - 1;
    table.getSelectionModel().select(lastRowIndex);
    WaitForAsyncUtils.waitForFxEvents();
         
           Node tcDate = lookup("#tcDate").nth(lastRowIndex + 1).query();
        doubleClickOn(tcDate);
        push(KeyCode.DELETE);
        push(KeyCode.DELETE);
        push(KeyCode.DELETE);
        push(KeyCode.DELETE);
        push(KeyCode.DELETE);
        push(KeyCode.DELETE);
        push(KeyCode.DELETE);
        push(KeyCode.DELETE);
        push(KeyCode.DELETE);
        write("15/03/2028");
        push(KeyCode.ENTER);     
        
   try {

    DialogPane dialogPane = lookup(".dialog-pane").query();
    

    boolean titleFound = false;
    boolean messageFound = false;
    

    Set<Node> allLabels = lookup(".dialog-pane .label").queryAll();
    for (Node node : allLabels) {
        if (node instanceof Labeled) {
            String text = ((Labeled) node).getText();
            if (text != null) {

                if (text.contains("Invalid Date")) {
                    titleFound = true;
                }
                if (text.contains("The date cannot be later than today.")) {
                    messageFound = true;
                }
            }
        }
    }

    Window window = dialogPane.getScene().getWindow();
    if (window instanceof Stage) {
        String windowTitle = ((Stage) window).getTitle();
        if (windowTitle != null && windowTitle.contains("Invalid Date")) {
            titleFound = true;
        }
    }

    assertTrue("No se encontró el texto 'Invalid Date' en el diálogo", titleFound);
    assertTrue("No se encontró el mensaje 'The date cannot be later than today.' en el dialogo", messageFound);
    
} catch (NoSuchElementException e) {
    fail("No se mostró ningún diálogo de alerta");
}
    push(KeyCode.ENTER);

         
     }
    
    
    
}
    


    