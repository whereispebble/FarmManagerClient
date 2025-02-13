/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumes;



import DTO.*;
import businessLogic.animalGroup.AnimalGroupFactory;
import businessLogic.consumes.ConsumesManagerFactory;
import businessLogic.product.ProductManagerFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
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
        } else {
            loadConsumesView();
        }
    }

    private void loadConsumesView() throws Exception {
        fxmlLoader.setLocation(getClass().getResource("/ui/view/ConsumesView.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        controller = fxmlLoader.getController();
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
            loadConsumesView();
        }
        verifyThat("#tableConsumes", isVisible());
    }

    @Test
    public void testB_LoadConsumes() {
        WaitForAsyncUtils.waitForFxEvents();
        TableView<ConsumesBean> table = lookup("#tableConsumes").query();
        assertNotNull("Consumes table should not be null", table);
        assertFalse("Consumes table should not be empty", table.getItems().isEmpty());
    }

    @Test
    public void testC_AddConsumes() {
        WaitForAsyncUtils.waitForFxEvents();
        TableView<ConsumesBean> table = lookup("#tableConsumes").query();
        int initialSize = table.getItems().size();
        clickOn("#btnAdd");
        WaitForAsyncUtils.waitForFxEvents();

        int finalSize = table.getItems().size();
        assertEquals("Table size should increase by 1", initialSize + 1, finalSize);

        ConsumesBean newConsume = table.getItems().get(finalSize - 1);
        assertNotNull("New consume should not be null", newConsume);
        assertNull("Product should be null initially", newConsume.getProduct());
        assertNull("AnimalGroup should be null initially", newConsume.getAnimalGroup());
    }

    
    
    
    @Test
    public void testD_UpdateConsumes() {
        WaitForAsyncUtils.waitForFxEvents();
        TableView<ConsumesBean> table = lookup("#tableConsumes").query();
        if (table.getItems().isEmpty()) {
            testC_AddConsumes();
        }
        ConsumesBean consumeToUpdate = table.getItems().get(0);
        clickOn(table.lookup(".table-row-cell"));
        doubleClickOn(table.lookup(".table-cell"));        

        push(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        ConsumesBean updatedConsume = table.getItems().get(0);
        assertNotEquals("Animal Group should be updated", consumeToUpdate.getAnimalGroup(), updatedConsume.getAnimalGroup());

    }

    @Test
    public void testE_DeleteConsumes() {
        WaitForAsyncUtils.waitForFxEvents();
        TableView<ConsumesBean> table = lookup("#tableConsumes").query();
        if (table.getItems().isEmpty()) {
            testC_AddConsumes();
        }
        int initialSize = table.getItems().size();

        clickOn(table.lookup(".table-row-cell"));
        rightClickOn(table.lookup(".table-row-cell"));
        clickOn("#miDelete");
        clickOn("Yes");
        WaitForAsyncUtils.waitForFxEvents();

        int finalSize = table.getItems().size();
        assertEquals("Table size should decrease by 1", initialSize - 1, finalSize);
    }

    @Test
    public void testI_AddConsumesInvalidInput() {
        WaitForAsyncUtils.waitForFxEvents();
        TableView<ConsumesBean> table = lookup("#tableConsumes").query();
        int initialSize = table.getItems().size();

        clickOn("#btnAdd");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(table.lookup(".table-row-cell"));

        doubleClickOn(table.lookup(".table-cell"));
        write("abc"); 

        push(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        int finalSize = table.getItems().size();
        assertEquals("Table size should remain the same", initialSize, finalSize);
    }

    @Test
    public void testJ_UpdateConsumesInvalidInput() {
        WaitForAsyncUtils.waitForFxEvents();
        TableView<ConsumesBean> table = lookup("#tableConsumes").query();
        if (table.getItems().isEmpty()) {
            testC_AddConsumes();
        }

        clickOn(table.lookup(".table-row-cell"));
        doubleClickOn(table.lookup(".table-cell"));
        write("xyz"); 
        push(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();


    }

    @Test
    public void testK_SearchInvalidDateRange() {
        clickOn("#comboSearch");
        clickOn("Date");

        DatePicker dpFrom = lookup("#dpSearchFrom").query();
        DatePicker dpTo = lookup("#dpSearchTo").query();

        dpFrom.setValue(java.time.LocalDate.of(2024, 02, 10));

        dpTo.setValue(java.time.LocalDate.of(2024, 02, 05)); 


        clickOn("#btnSearch");
      
    }}

    
