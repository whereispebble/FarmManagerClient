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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;
import org.testfx.api.FxAssert;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import org.testfx.util.WaitForAsyncUtils;
import ui.controller.ConsumesController;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConsumesControllerTest extends ApplicationTest {

    private static final Logger LOGGER = Logger.getLogger(ConsumesControllerTest.class.getName());
    private static boolean loggedIn = false;
    private ConsumesController controller;
    private Stage primaryStage; // Store the primary stage

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage; // Store the stage
        fxmlLoader = new FXMLLoader();
        if (!loggedIn) {
            fxmlLoader.setLocation(getClass().getResource("/ui/view/SignIn.fxml")); // Correct path
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            loadConsumesView();
        }
    }

    private FXMLLoader fxmlLoader;

    private void loadConsumesView() throws Exception {
        fxmlLoader.setLocation(getClass().getResource("/ui/view/Consumes.fxml")); // Correct path
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene); // Use the stored stage
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
            loadConsumesView(); // Load the ConsumesView after successful login

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

//    @Test
//    public void testD_UpdateConsumes() {
//        WaitForAsyncUtils.waitForFxEvents();
//        TableView<ConsumesBean> table = lookup("#tableConsumes").query();
//        if (table.getItems().isEmpty()) {
//            testC_AddConsumes(); // Add a consume if the table is empty
//        }
//        ConsumesBean consumeToUpdate = table.getItems().get(0);
//
//        clickOn(table.lookup(".table-row-cell"));
//        doubleClickOn(table.lookup(".table-cell")); // Animal Group
//
//        // Simulate selection from ComboBox (replace with your actual selection method)
//        // Example: clickOn("New Animal Group Name");
//        push(KeyCode.ENTER);
//        WaitForAsyncUtils.waitForFxEvents();
//
//        ConsumesBean updatedConsume = table.getItems().get(0);
//        assertNotEquals("Animal Group should be updated", consumeToUpdate.getAnimalGroup(), updatedConsume.getAnimalGroup());
//
//        
//    }
        @Test
    public void testD_UpdateConsumes() {
        WaitForAsyncUtils.waitForFxEvents(); 
        TableView<ConsumesBean> table = lookup("#tableConsumes").query();

        if (table.getItems().isEmpty()) {
            testC_AddConsumes(); 
        }

        ObservableList<ConsumesBean> consumes = table.getItems();
        int lastRowIndex = consumes.size() - 1;

        if (lastRowIndex < 0) {
            fail("Table is empty, cannot update.");
            return;
        }

        ConsumesBean consumeToUpdate = consumes.get(lastRowIndex);

        // --- Update Animal Group ---
        AnimalGroupBean newAnimalGroup = new AnimalGroupBean(); 
        newAnimalGroup.setName("Male Sheeps"); 
        consumeToUpdate.setAnimalGroup(newAnimalGroup);
        table.refresh(); 

        // --- Update Product ---
        ProductBean newProduct = new ProductBean();
        newProduct.setName("Apples");
        consumeToUpdate.setProduct(newProduct);
        table.refresh();

        // --- Update Consume Amount ---
        consumeToUpdate.setConsumeAmount(123.45f);
        table.refresh();

        // --- Update Date ---
        java.time.LocalDate localDate = java.time.LocalDate.of(2024,01, 15);
        java.util.Date newDate = java.util.Date.from(localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
        consumeToUpdate.setDate(newDate);
        table.refresh();

        WaitForAsyncUtils.waitForFxEvents();

        // --- Assertions ---
        ConsumesBean updatedConsume = consumes.get(lastRowIndex); 

        assertNotEquals("Animal Group should be updated", newAnimalGroup, updatedConsume.getAnimalGroup());
        assertNotEquals("Product should be updated", newProduct, updatedConsume.getProduct());
        assertEquals("Consume Amount should be updated", 123.45f, updatedConsume.getConsumeAmount(), 0.001); 
        assertEquals("Date should be updated", newDate, updatedConsume.getDate());


    }


  @Test
    public void testE_DeleteConsumesDirectly() {
        LOGGER.info("Iniciando test Delete");
        WaitForAsyncUtils.waitForFxEvents();
        TableView<ConsumesBean> table = lookup("#tableConsumes").query();

        
        if (table.getItems().isEmpty()) {
            testC_AddConsumes(); 
            WaitForAsyncUtils.waitForFxEvents(); 
            table = lookup("#tableConsumes").query(); 
        }

        int selectedIndex = 0; 
        table.getSelectionModel().select(selectedIndex);
        WaitForAsyncUtils.waitForFxEvents();

        ConsumesBean selectedConsume = table.getSelectionModel().getSelectedItem();
        assertNotNull(selectedConsume.toString(), "No item was selected.");

        TableRow<ConsumesBean> row = lookup(".table-row-cell").nth(selectedIndex).query();

        rightClickOn(row);
        clickOn("#itemDelete");
        clickOn("Yes");
        WaitForAsyncUtils.waitForFxEvents();

        ObservableList<ConsumesBean> items = table.getItems();
        boolean isDeleted = items.stream().noneMatch(item -> item.equals(selectedConsume));
        if (isDeleted){System.out.println("test E succeeded");
        assertTrue("The deleted item should not be in the table", isDeleted);
        }
    }
}
