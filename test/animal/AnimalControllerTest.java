/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animal;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
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
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnimalControllerTest extends ApplicationTest{
    
    private static final Logger logger = Logger.getLogger(AnimalControllerTest.class.getName());

    @Override
    public void start(Stage stage) throws Exception {

        logger.info("iniciando desde animaltest");
        
        Parent root = FXMLLoader.load(getClass().getResource("/ui/view/SignIn.fxml"));
        

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
//    @BeforeClass
      @Test
    public void dummyTest() {
      
    }
    
}
