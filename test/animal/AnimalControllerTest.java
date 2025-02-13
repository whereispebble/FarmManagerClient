/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animal;

import DTO.AnimalBean;
import DTO.AnimalGroupBean;
import DTO.SpeciesBean;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableCell;
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
        sleep(500);
    }
 
    @Test
    public void testB_UpdateAnimal() {
        TableView<AnimalBean> table = lookup("#tbAnimal").query();
        
        TableRow<AnimalBean> row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Integer tablerow = table.getSelectionModel().getSelectedIndex();
        
        //Name
        Node tcName = lookup("#tcName").nth(tablerow + 1).query();
        doubleClickOn(tcName);
        write("TEST");
        push(KeyCode.ENTER);

       //Birthdate
        Node tcBirthdate = lookup("#tcBirthdate").nth(tablerow + 1).query();
        doubleClickOn(tcBirthdate);
        clickOn(tcBirthdate);
        write("01/02/2025");
        push(KeyCode.ENTER);

        //AnimalGroup
        Node tcAnimalGroup = lookup("#tcAnimalGroup").nth(tablerow + 1).query();
        doubleClickOn(tcAnimalGroup);
        ComboBox<AnimalGroupBean> comboBoxAG = lookup("#tcAnimalGroup .combo-box").queryAs(ComboBox.class);
        Node optionAG = lookup(".list-view .list-cell").nth(0).query();
        clickOn(optionAG);
        AnimalGroupBean newValueAG = comboBoxAG.getValue();

        //Subespecies
        Node tcSubespecies = lookup("#tcSubespecies").nth(tablerow + 1).query();
        doubleClickOn(tcSubespecies);
        write("SUB");
        push(KeyCode.ENTER);

        //Species
        Node tcSpecies = lookup("#tcSpecies").nth(tablerow + 1).query();
        doubleClickOn(tcSpecies);
        ComboBox<SpeciesBean> comboBoxS = lookup("#tcSpecies .combo-box").queryAs(ComboBox.class);
        Node optionS = lookup(".list-view .list-cell").nth(0).query();
        clickOn(optionS);
        SpeciesBean newValueS = comboBoxS.getValue();

        AnimalBean updatedAnimal = (AnimalBean) table.getSelectionModel().getSelectedItem();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String modifiedBirthdate = sdf.format(updatedAnimal.getBirthdate());
        assertEquals("The name of the animal should be 'TEST'", "TEST", updatedAnimal.getName());
        assertEquals("The birthdate of the animal should be '01/02/2025'", "01/02/2025", modifiedBirthdate);
        assertEquals("The animal group of the animal should be "+newValueAG.getName(), newValueAG.getName(), updatedAnimal.getAnimalGroup().getName());
        assertEquals("The subespecies of the animal should be 'SUB'", "SUB", updatedAnimal.getSubespecies());
        assertEquals("The species of the animal should be " + newValueS.getName(), newValueS.getName(), updatedAnimal.getSpecies().getName());
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
}
