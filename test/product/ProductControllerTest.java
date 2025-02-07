package product;

import DTO.ProductBean;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 * Test class for ProductController.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest extends ApplicationTest {

    private TableView table;
    private TableColumn tcStock;
    private ProductBean selectedProduct;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/view/SignIn.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        table = lookup("#tbProduct").query();
    }

    @Test
    public void testA_OpenProductView() {

        clickOn("#tfUsername").write("manager1@example.com");
        verifyThat("#tfUsername", hasText("manager1@example.com"));

        clickOn("#pfPasswd").write("manager123");
        verifyThat("#pfPasswd", hasText("manager123"));

        clickOn("#btnSignIn");

        verifyThat("#menuBar", isVisible());
        clickOn("#menuNavigateTo");
        clickOn("#miProduct");
    }

    @Test
    public void testB_GetProducts() {
        table = lookup("#tbProduct").query();

        assertNotNull("Product is null; ", table);

        ObservableList<ProductBean> items = table.getItems();

        assertNotNull("Product list must be null; ", items);
        assertNotEquals("Product table must not be null; ", 0, items.size());

        for (Object item : items) {
            assertNotNull("Table cannot be null; ", item);
            assertEquals("Table elements must be ProductBean type; ", ProductBean.class, item.getClass());
        }
    }

    @Test
    public void testC_DeleteProduct() {
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data; ", rowCount, 0);
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Selected row is null; ", row);
        clickOn(row);

        ProductBean selectedProduct = (ProductBean) table.getSelectionModel()
                .getSelectedItem();
        rightClickOn(row);
        clickOn("#miDelete");
        verifyThat("Are you sure you want to delete the selected row?\n"
                + "This operation cannot be undone.",
                isVisible());
        clickOn(isDefaultButton());
        assertEquals("The row has not been deleted!!!",
                rowCount - 1, table.getItems().size());
        verifyThat("#tfSearch", (TextField t) -> t.isFocused());
    }

    @Ignore
    @Test
    public void testD_EditProduct() {
        //get row count
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //look for 1st row in table view and click it
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        //get selected item and index from table
        selectedProduct = (ProductBean) table.getSelectionModel()
                .getSelectedItem();
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        //Modify product data
        ProductBean modifiedProduct = new ProductBean();

        modifiedProduct.setStock(new Random().nextInt());

        doubleClickOn("#tcStock");
        write(modifiedProduct.getStock().toString());

        // Uncomment and adapt the following lines to modify other fields (e.g., Providers)
//        doubleClickOn("#tcProviders");
//        int providerCount = tcProviders.getItems().size();
//        if (providerCount > 1) {
//            if (selectedProduct.getProvider().equals(cbProviders.getItems().get(0))) {
//                press(KeyCode.DOWN);
//            } else {
//                press(KeyCode.UP);
//            }
//        }
//        modifiedProduct.setProvider((ProviderBean) cbProviders
//                .getSelectionModel().getSelectedItem());
//        clickOn(btModify);
//        Assert modification.
//        assertEquals("The product has not been modified!!!",
//                modifiedProduct,
//                (ProductBean) table.getItems().get(selectedIndex));
//        verifyThat("#tfSearch", (TextField t) -> t.isFocused());
    }
}
