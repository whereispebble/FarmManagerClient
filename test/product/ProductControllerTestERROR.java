/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product;

import DTO.ProductBean;
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
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Ander
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTestERROR extends ApplicationTest {

    private static final Logger logger = Logger.getLogger(ProductControllerTestERROR.class.getName());
    private static boolean loggedIn = false;

    @Override
    public void start(Stage stage) throws Exception {
        if (!loggedIn) {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/view/Product.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @Test
    public void testB_GetProductERROR() {
        clickOn("#searchField").write("Product");
        clickOn("#btnSearch");
        verifyThat("Please contact with support", isVisible());
        sleep(500);
        clickOn("Aceptar");
    }

    @Test
    public void testC_AddProductERROR() {
        clickOn("#btnCreate");
        verifyThat("Please contact with support", isVisible());
        sleep(500);
        clickOn("Aceptar");
    }

    @Test
    public void testD_UpdateProductERROR() {
        TableView<ProductBean> table = lookup("#tbProduct").query();
        ObservableList<ProductBean> items = table.getItems();
        ProductBean testProduct = new ProductBean("Error Product");
        interact(() -> items.add(testProduct));

        int lastIndex = items.size() - 1;

        TableRow<ProductBean> row = lookup(".table-row-cell").nth(lastIndex).query();

        Node cell = from(row).lookup(".table-cell").nth(1).query();

        doubleClickOn(cell);

        write("New error test description");
        push(KeyCode.ENTER);
        verifyThat("Please contact with support", isVisible());
        sleep(500);
        clickOn("Aceptar");
    }

    @Test
    public void testE_ProductERROR() {
        TableView<ProductBean> table = lookup("#tbProduct").query();
        ObservableList<ProductBean> items = table.getItems();

        ProductBean testProduct = new ProductBean("Error Product");

        interact(() -> items.add(testProduct));

        int lastIndex = items.size() - 1;

        TableRow<ProductBean> row = lookup(".table-row-cell").nth(lastIndex).query();

        rightClickOn(row);

        clickOn("#miDelete");
        clickOn("Sí");

        verifyThat("Please contact with support", isVisible());

        clickOn("Aceptar");
    }
}
