/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animal;

import DTO.AnimalBean;
import DTO.AnimalGroupBean;
import animalGroup.AnimalGroupControllerTestERROR;
import java.util.Date;
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
import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author Aitziber
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnimalControllerErrorTest extends ApplicationTest{
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
        sleep(10000);
        // Deten el servidor
    }

    @Test
    public void testB_SearchServerError() {   
        clickOn("#btnSearch");
        verifyThat("Error", isVisible());
        sleep(500);
        clickOn("Aceptar");
    }
    
    @Test
    public void testC_AddServerError() {   
        clickOn("#btnAdd");
        sleep(1000);
        verifyThat(".dialog-pane", isVisible());
        sleep(500);
        clickOn("Aceptar");
    }
    
    @Test
    public void testD_DeleteServerError() { 
        TableView<AnimalBean> table = lookup("#tbAnimal").query();
        TableRow<AnimalBean> row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);

        AnimalBean selectedAnimal=(AnimalBean)table.getSelectionModel().getSelectedItem();
        rightClickOn(row);
        clickOn("#miDelete");
        clickOn("Sí");
        
        verifyThat("Error", isVisible());
        sleep(500);
        clickOn("Aceptar");
    } 
}
