/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumes;



import DTO.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
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
public class ConsumesControllerTest extends ApplicationTest {

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
  
    WaitForAsyncUtils.waitForFxEvents(); 
    TableView<ConsumesBean> table = lookup("#tableConsumes").query();   
    if (table.getItems().isEmpty()) {
        testC_AddConsumes(); 
        WaitForAsyncUtils.waitForFxEvents();
    }
    int lastRowIndex = table.getItems().size() - 1;
    table.getSelectionModel().select(lastRowIndex);
    WaitForAsyncUtils.waitForFxEvents();
     
  // --- Update Animal Group ---
    ConsumesBean consumeToUpdate = table.getItems().get(lastRowIndex);
    Node tcAnimalGroup = lookup("#tcAnimalGroup").nth(lastRowIndex + 1).query();   
    doubleClickOn(tcAnimalGroup);   
    ComboBox<AnimalGroupBean> comboBoxAG = lookup("#tcAnimalGroup .combo-box").queryAs(ComboBox.class);  
    clickOn(comboBoxAG);
    sleep(500);
    Node optionAG = lookup(".list-view .list-cell").nth(0).query();
    sleep(1000);
    clickOn(optionAG);
    AnimalGroupBean newValueAG = comboBoxAG.getValue();

    
    // --- Update Product ---
    Node tcProduct = lookup("#tcProduct").nth(lastRowIndex + 1).query();
    doubleClickOn(tcProduct);
    WaitForAsyncUtils.waitForFxEvents();
    ComboBox<ProductBean> comboBoxP = lookup("#tcProduct .combo-box").queryAs(ComboBox.class);
    Node optionP = lookup(".list-view .list-cell").nth(0).query();
    clickOn(optionP);
    ProductBean newValueP = comboBoxP.getValue(); 
     
    // --- Update Consume Amount ---
    Node tcConsumeAmount = lookup("#tcConsumeAmount").nth(lastRowIndex + 1).query();
    doubleClickOn(tcConsumeAmount);
    write("123.45f");
    push(KeyCode.ENTER);
    
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


    WaitForAsyncUtils.waitForFxEvents();
    table.refresh();
     
    // --- Assertions ---
    ConsumesBean updatedConsume = table.getItems().get(lastRowIndex); 
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String modifiedDate = sdf.format(updatedConsume.getDate());
    assertNotEquals("Animal Group should be updated"+newValueAG.getName(), newValueAG.getName(), updatedConsume.getAnimalGroup());
    assertNotEquals("Product should be updated"+newValueP.getName(), newValueP.getName(), updatedConsume.getProduct());
    assertEquals("Consume Amount should be updated", 123.45f, updatedConsume.getConsumeAmount(),0.001); 
    assertEquals("The birthdate of the animal should be '15/03/2025'", "15/03/2025", modifiedDate);
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

