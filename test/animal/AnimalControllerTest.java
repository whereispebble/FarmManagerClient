/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animal;

import DTO.AnimalBean;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;



/**
 *
 * @author Aitziber
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnimalControllerTest extends ApplicationTest{
    
    private static final Logger logger = Logger.getLogger(AnimalControllerTest.class.getName());
    
//    AnimalBean dummy = new AnimalBean();

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("iniciando desde animaltest");
        
        Parent root = FXMLLoader.load(getClass().getResource("/ui/view/SignIn.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();    
    }

    @Test
    public void testA_OpenAnimalView() {
        logger.info("Probando ingreso de credenciales y cambio de ventana...");

        clickOn("#tfUsername").write("manager1@example.com");
        verifyThat("#tfUsername", hasText("manager1@example.com"));

        clickOn("#pfPasswd").write("manager123");
        verifyThat("#pfPasswd", hasText("manager123"));

        clickOn("#btnSignIn");

        verifyThat("#menuBar", isVisible());
        clickOn("#menuNavigateTo");
        clickOn("#miAnimal");
    }
       
    @Test
    public void testB_GetAnimals() {
        TableView<AnimalBean> table = lookup("#tbAnimal").query();

        assertNotNull("La tabla de animales no debería ser nula", table);

        ObservableList<AnimalBean> items = table.getItems();

        assertNotNull("La lista de animales no debería ser nula", items);
        assertNotEquals("La tabla de animales no debería estar vacía", 0, items.size());

        for (Object item : items) {
            assertNotNull("El elemento en la tabla no debería ser nulo", item);
            assertEquals("El elemento de la tabla debería ser de tipo AnimalBean", AnimalBean.class, item.getClass());
        }
    }
    
    @Test
    public void testC_DeleteAniaml() {
        logger.info("Probando eliminación de animal...");

        TableView<AnimalBean> table = lookup("#tbAnimal").query();

    //    AnimalBean itemToDelete = table.getItems().get(0);
//
//        Node row=lookup(".table-row-cell").nth(0).query();
//        clickOn(row);

        TableRow<AnimalBean> row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);

        AnimalBean selectedAnimal=(AnimalBean)table.getSelectionModel()
                                         .getSelectedItem();
        rightClickOn(row);
//        clickOn("#miDelete");
//
//        ObservableList<AnimalBean> items = table.getItems();
//        boolean isDeleted = items.stream().noneMatch(item -> item.equals(selectedAnimal));
//
//        assertTrue("El item eliminado debería ya no estar presente en la tabla", isDeleted);
    }
    
}
