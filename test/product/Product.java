/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product;

import ui.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 *
 * @author InigoFreire
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Product extends ApplicationTest{
    
    public Product() {
    }
    
    /**
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
  
    private TableView table;
    private Button btModificar;
    private Button btCrear;
    private ComboBox cbDepartamentos;
    
    @FXML
    private ComboBox comboSearch;

    @FXML
    private DatePicker dpSearch;

    @FXML
    private TextField tfSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView tbProduct;

    @FXML
    private TableColumn<ProductBean, String> tcName;

    @FXML
    private TableColumn<ProductBean, Float> tcPrice;

    @FXML
    private TableColumn tcMonthlyConsume;

    @FXML
    private TableColumn<ProductBean, Integer> tcStock;

    @FXML
    private TableColumn<ProductBean, ProviderBean> tcProviders;

    @FXML
    private TableColumn tcCreatedDate;

    @FXML
    private Label lblInfo;

    @FXML
    private Button btnAdd;
    
    @Override 
    public void start(Stage stage) throws Exception {
        //start JavaFX application to be tested    
        new Application().start(stage);
        //lookup for some nodes to be used in testing
        table=lookup("#tbProduct").queryTableView();
        rbUser=(RadioButton)lookup("#rbUsuario").query();
        rbAdmin=(RadioButton)lookup("#rbAdmin").query();
        cbDepartamentos=lookup("#cbDepartamentos").queryComboBox();
    }
    
    /**
     * Test that user is deleted as ok button is clicked on confirmation dialog.
     */
    @Test
    //@Ignore
    public void testA_deleteUser() {
        //get row count
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and click it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat("#eliminar", isEnabled());//note that id is used instead of fx:id
        clickOn("#eliminar");
        verifyThat("¿Borrar la fila seleccionada?\n"
                                    + "Esta operación no se puede deshacer.",
                    isVisible());    
        clickOn(isDefaultButton());
        assertEquals("The row has not been deleted!!!",
                    rowCount-1,table.getItems().size());
        verifyThat(tfLogin,  (TextField t) -> t.isFocused());
    }
    
    /**
     * Test user creation.
      */
    @Test
    //@Ignore
    public void testB_createUser() { 
        //get row count
        int rowCount=table.getItems().size();
        //get an existing login from random generator
        String login="username"+new Random().nextInt();
        //write that login on text field
        tfLogin.clear();
        clickOn(tfLogin);
        write(login);
        doubleClickOn(tfNombre);
        write("anyname");
        clickOn(btCrear);
        assertEquals("The row has not been added!!!",rowCount+1,table.getItems().size());
        //look for user in table data model
        List<UserBean> users=table.getItems();
        assertEquals("The user has not been added!!!",
                users.stream().filter(u->u.getLogin().equals(login)).count(),1);
        
    }
    
    /**
     * Test user modification.
      */
    @Test
    //@Ignore
    public void testC_modifyUser() { 
        //get row count
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and click it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        //get selected item and index from table
        UserBean selectedUser=(UserBean)table.getSelectionModel()
                                     .getSelectedItem();
        int selectedIndex=table.getSelectionModel().getSelectedIndex();
        //Modify user data
        UserBean modifiedUser=new UserBean();

        modifiedUser.setLogin(selectedUser.getLogin()+new Random().nextInt());
        tfLogin.clear();
        clickOn(tfLogin);
        write(modifiedUser.getLogin());

        modifiedUser.setNombre(selectedUser.getNombre()+new Random().nextInt());
        tfNombre.clear();
        doubleClickOn(tfNombre);
        write(modifiedUser.getNombre());

        if(rbUser.isSelected()){
            rbAdmin.setSelected(true);
            modifiedUser.setPerfil(Profile.ADMIN);
        }
        else{
            rbUser.setSelected(true);
            modifiedUser.setPerfil(Profile.USER);         
        }
        
        clickOn(cbDepartamentos);
        int departmentCount=cbDepartamentos.getItems().size();
        if (departmentCount>1){
            if(selectedUser.getDepartamento().equals(cbDepartamentos.getItems().get(0))) 
                press(KeyCode.DOWN);
            else
                press(KeyCode.UP);
        }
        modifiedUser.setDepartamento((DepartmentBean)cbDepartamentos
                                    .getSelectionModel().getSelectedItem());
        clickOn(btModificar);
        //Assert modification.
        assertEquals("The user has not been modified!!!",
                     modifiedUser,
                     (UserBean)table.getItems().get(selectedIndex));
        verifyThat("#tfLogin",  (TextField t) -> t.isFocused());
    }
}
