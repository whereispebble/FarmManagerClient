/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animal;

import DTO.AnimalBean;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;



/**
 *
 * @author Aitziber
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnimalControllerTest extends ApplicationTest{
    
    private static final Logger logger = Logger.getLogger(AnimalControllerTest.class.getName());
    private static boolean loggedIn = false;
    
    @Override
    public void start(Stage stage) throws Exception {
        if (!loggedIn) {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/view/SignIn.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @Test
    public void testA_OpenAnimalView() {
        clickOn("#tfUsername").write("aitziber@paia.com");
        verifyThat("#tfUsername", hasText("aitziber@paia.com"));

        clickOn("#pfPasswd").write("12345678");
        verifyThat("#pfPasswd", hasText("12345678"));

        clickOn("#btnSignIn");
        loggedIn = true;

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
    public void testC_AddAnimal() {
        TableView<AnimalBean> table = lookup("#tbAnimal").query();
        int initialSize = table.getItems().size();
        clickOn("#btnAdd");
        
        write("NEW"); 
        push(KeyCode.ENTER);

        ObservableList<AnimalBean> items = table.getItems();
        boolean newAnimalAdded = items.stream().anyMatch(item -> "NEW".equals(item.getName()));
        assertTrue("El animal con el nombre 'NEW' debería estar presente en la tabla", newAnimalAdded);

        assertEquals("La tabla debería tener una fila más", initialSize + 1, table.getItems().size());

    }
 
    @Test
    public void testB_UpdateAnimal() {
        TableView<AnimalBean> table = lookup("#tbAnimal").query();

        ObservableList<AnimalBean> items = table.getItems();
        AnimalBean animalToUpdate = items.stream()
                                         .filter(item -> "NEW".equals(item.getName()))
                                         .findFirst()
                                         .orElse(null);

        assertNotNull("No se encontró el animal con el nombre 'NEW'", animalToUpdate);

        TableRow<AnimalBean> row = lookup(".table-row-cell").nth(items.indexOf(animalToUpdate)).query();
        Node cell = from(row).lookup(".table-cell").nth(0).query();
        clickOn(cell); 
         clickOn(cell);
        write("TEST");
        push(KeyCode.ENTER);

        items = table.getItems();
        boolean animalUpdated = items.stream().anyMatch(item -> "TEST".equals(item.getName()));
        assertTrue("El animal con el nombre 'TEST' debería estar presente en la tabla", animalUpdated);
    }

    @Test
    public void testE_DeleteAniaml() {
        TableView<AnimalBean> table = lookup("#tbAnimal").query();
        TableRow<AnimalBean> row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);

        AnimalBean selectedAnimal=(AnimalBean)table.getSelectionModel().getSelectedItem();
        rightClickOn(row);
        clickOn("#miDelete");
        clickOn("Sí");
        
        ObservableList<AnimalBean> items = table.getItems();
        boolean isDeleted = items.stream().noneMatch(item -> item.equals(selectedAnimal));

        assertTrue("El item eliminado no debería estar presente en la tabla", isDeleted);
    }
    
    @Test
    public void testC_DeleteAnimal() {
        TableView<AnimalBean> table = lookup("#tbAnimal").query();

        ObservableList<AnimalBean> items = table.getItems();
        AnimalBean animalToDelete = items.stream()
                                        .filter(item -> "TEST".equals(item.getName()))
                                        .findFirst()
                                        .orElse(null);

        assertNotNull("No se encontró el animal con el nombre 'TEST'", animalToDelete);

        TableRow<AnimalBean> row = lookup(".table-row-cell").nth(items.indexOf(animalToDelete)).query();
        clickOn(row);

        rightClickOn(row);
        clickOn("#miDelete");
        clickOn("Sí");

        items = table.getItems();
        boolean animalDeleted = items.stream().noneMatch(item -> "TEST".equals(item.getName()));
        assertTrue("El animal con el nombre 'TEST' debería haber sido eliminado de la tabla", animalDeleted);
    }
}
