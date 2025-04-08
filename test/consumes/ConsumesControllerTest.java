/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumes;



import DTO.*;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import java.time.Duration; // <-- Necesitas importar esto
import businessLogic.animalGroup.AnimalGroupFactory;
import businessLogic.consumes.ConsumesManagerFactory;
import businessLogic.product.ProductManagerFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javafx.scene.Node;
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
    private FXMLLoader fxmlLoader;
    
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
    
    @Test
    public void testB_LoadConsumes() {
        WaitForAsyncUtils.waitForFxEvents();
        TableView<ConsumesBean> table = lookup("#tableConsumes").query();
        assertNotNull("Consumes table should not be null", table);
        assertFalse("Consumes table should not be empty", table.getItems().isEmpty());
        LOGGER.log(Level.FINE, "Loading consumes ok");
        
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
    // Esperar a que la interfaz se actualice
    WaitForAsyncUtils.waitForFxEvents(); 
    LOGGER.log(Level.INFO, "1");
    // Obtener la tabla
    TableView<ConsumesBean> table = lookup("#tableConsumes").query();
     LOGGER.log(Level.INFO, "2");
    // Asegurarnos de que hay elementos en la tabla
    if (table.getItems().isEmpty()) {
        testC_AddConsumes(); 
        WaitForAsyncUtils.waitForFxEvents();
    }
     LOGGER.log(Level.INFO, "3");
    // Seleccionar explícitamente la última fila
    int lastRowIndex = table.getItems().size() - 1;
    table.getSelectionModel().select(lastRowIndex);
    WaitForAsyncUtils.waitForFxEvents();
     LOGGER.log(Level.INFO, "4");
    // Obtener el consumo a actualizar
    ConsumesBean consumeToUpdate = table.getItems().get(lastRowIndex);
     LOGGER.log(Level.INFO, "5");
    // --- Update Animal Group ---
    Node tcAnimalGroup = lookup("#tcAnimalGroup").nth(lastRowIndex + 1).query();
    
 LOGGER.log(Level.INFO, "5.1");
       
doubleClickOn(tcAnimalGroup);
    
LOGGER.log(Level.INFO, "5.2");
       
ComboBox<AnimalGroupBean> comboBoxAG = lookup("#tcAnimalGroup .combo-box").queryAs(ComboBox.class);
     
LOGGER.log(Level.INFO, "5.3");

// Primero hacemos clic en el ComboBox para abrirlo
clickOn(comboBoxAG);

// Esperamos a que se despliegue completamente
sleep(500);
        
// Ahora buscamos la opción en la lista desplegada
Node optionAG = lookup(".list-view .list-cell").nth(0).query();

LOGGER.log(Level.INFO, "5.4");
           
// Esperamos un poco más para asegurarnos de que el menú siga abierto
sleep(1000);

// Finalmente hacemos clic en la opción
clickOn(optionAG);
           LOGGER.log(Level.INFO, "5.5");
        AnimalGroupBean newValueAG = comboBoxAG.getValue();
   LOGGER.log(Level.INFO, "6");
    
     LOGGER.log(Level.INFO, "7");
    // --- Update Product ---
    Node tcProduct = lookup("#tcProduct").nth(lastRowIndex + 1).query();
    doubleClickOn(tcProduct);
    WaitForAsyncUtils.waitForFxEvents();
     LOGGER.log(Level.INFO, "8");
    ComboBox<ProductBean> comboBoxP = lookup("#tcProduct .combo-box").queryAs(ComboBox.class);
    Node optionP = lookup(".list-view .list-cell").nth(0).query();
    clickOn(optionP);
    ProductBean newValueP = comboBoxP.getValue(); 
     LOGGER.log(Level.INFO, "9");
    // --- Update Consume Amount ---
    Node tcConsumeAmount = lookup("#tcConsumeAmount").nth(lastRowIndex + 1).query();
    doubleClickOn(tcConsumeAmount);
    write("123.45f");
    push(KeyCode.ENTER);
   LOGGER.log(Level.INFO, "10");
// --- Update Date ---
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
        write("15/03/2025");
        push(KeyCode.ENTER);

// Esperar a que la interfaz se actualice
WaitForAsyncUtils.waitForFxEvents();
table.refresh();
     LOGGER.log(Level.INFO, "12");
    // --- Assertions ---
    ConsumesBean updatedConsume = table.getItems().get(lastRowIndex); 
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String modifiedDate = sdf.format(updatedConsume.getDate());
     LOGGER.log(Level.INFO, "13");
    assertNotEquals("Animal Group should be updated"+newValueAG.getName(), newValueAG.getName(), updatedConsume.getAnimalGroup());
    assertNotEquals("Product should be updated"+newValueP.getName(), newValueP.getName(), updatedConsume.getProduct());
    assertEquals("Consume Amount should be updated", 123.45f, updatedConsume.getConsumeAmount(),0.001); 
    assertEquals("The birthdate of the animal should be '15/03/2025'", "15/03/2025", modifiedDate);
     LOGGER.log(Level.INFO, "14");
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
        clickOn(".dialog-pane .button-bar .button:text('Yes')");
        WaitForAsyncUtils.waitForFxEvents();

        ObservableList<ConsumesBean> items = table.getItems();
        boolean isDeleted = items.stream().noneMatch(item -> item.equals(selectedConsume));
        if (isDeleted){System.out.println("test E succeeded");
        assertTrue("The deleted item should not be in the table", isDeleted);
        }
    }


    }

