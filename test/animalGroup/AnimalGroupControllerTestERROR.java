/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalGroup;

import DTO.AnimalGroupBean;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author Ander
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnimalGroupControllerTestERROR extends ApplicationTest {

    private static final Logger logger = Logger.getLogger(AnimalGroupControllerTestERROR.class.getName());
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
    public void testA_OpenAnimalGroupView() {
        logger.info("Iniciando test Open");
        clickOn("#tfUsername").write("ander@paia.com");
        clickOn("#pfPasswd").write("12345678");
        clickOn("#btnSignIn");

        verifyThat("#tbAnimalGroup", isVisible());
        loggedIn = true;
        
        // For this test at this point server must be shut down
        sleep(10000);
    }
    
    @Test
    public void testB_GetAnimalGroupsERROR() {
        clickOn("#btnSearch");
        verifyThat("Please contact with support", isVisible());
        clickOn("Aceptar");
    }

    @Test
    public void testC_AddAnimalGroupERROR() {
        clickOn("#btnCreate");
        verifyThat("Please contact with support", isVisible());
        clickOn("Aceptar");
    }

    @Test
    public void testD_DeleteAnimalGroup() {
        TableRow<AnimalGroupBean> row = lookup(".table-row-cell").nth(0).query();
        
        rightClickOn(row);
        clickOn("#miDelete");
        clickOn("Sí");
        verifyThat("Please contact with support", isVisible());
        clickOn("Aceptar");
    }

    @Test
    public void testE_UpdateAnimalGroup() {
        TableRow<String> row = lookup(".table-row-cell").nth(0).query();

        Node cell = from(row).lookup(".table-cell").nth(1).query();

        clickOn(cell);

        write("New description");
        push(KeyCode.ENTER);
        verifyThat("Please contact with support", isVisible());
        clickOn("Aceptar");
    }
}
