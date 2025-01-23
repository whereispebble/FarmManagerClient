/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.ProductBean;

/**
 * FXML Controller class
 *
 * @author InigoFreire
 */
public class ProductController implements Initializable {

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
    private TableColumn tcName;

    @FXML
    private TableColumn tcPrice;

    @FXML
    private TableColumn tcMonthlyConsume;

    @FXML
    private TableColumn tcStock;

    @FXML
    private TableColumn tcProviders;

    @FXML
    private TableColumn tcCreatedDate;

    @FXML
    private Label lblInfo;

    @FXML
    private Button btnAdd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//      Establecer los siguientes parámetros de la ventana:
//          Título: “Product”
//          Dimensiones: 1024x720
//        stage.setTitle("Product");
//        stage.setWidth(1024);
//        stage.setHeight(720);

//        La ventana no debe ser redimensionable.
//        stage.setResizable(false);
//        Habilitar el Botón btnAdd.
        btnAdd.setDisable(false);

//      Cargar la tabla:
//      Establecer como editables las columnas Product, Price, Stock y Providers.
//      Llamar al método de lógica getAllProducts() y obtener una lista con todos los registros almacenados de los productos.
//      Cargar los datos obtenidos en la Table View tableProduct, que tiene las siguientes columnas:
//      Product: String | Editable (como TextFieldtableCell)
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcName.setCellFactory(TextFieldTableCell.<ProductBean>forTableColumn());
//        tcName.setOnEditCommit((CellEditEvent<ProductBean, String> event) -> { LAMBDA
//                TablePosition<ProductBean, String> pos = event.getTablePosition();
//                String newName = event.getNewValue();
//                int row = pos.getRow();
//                ProductBean product = event.getTableView().getItems().get(row);
//                product.setName(newName);
//
//                ProductManagerFactory.get().updateProduct(product);
//
//                event.getTableView().refresh();
//            });
            
        tcName.setOnEditCommit(new EventHandler<CellEditEvent<ProductBean, String>>() { //REFERENCIA DE CLASE
            @Override
            public void handle(CellEditEvent<ProductBean, String> event) {
                TablePosition<ProductBean, String> pos = event.getTablePosition();
                String newName = event.getNewValue();
                int row = pos.getRow();
                ProductBean product = event.getTableView().getItems().get(row);
                product.setName(newName);
                ProductManagerFactory.get().updateProduct(product);
                event.getTableView().refresh();
            }
        });

//      Price: Float | Editable (como SpinnerTableCell)
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcPrice.setCellFactory(TextFieldTableCell.<ProductBean, Float>forTableColumn(new FloatStringConverter()));
//      Mensual consume: Consume() | No editable
        tcMonthlyConsume.setCellValueFactory(new PropertyValueFactory<>("monthlyConsume"));
        tcMonthlyConsume.setEditable(false);
//      Stock: Integer | Editable (como SpinnerTableCell)
        tcStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tcStock.setCellFactory(TextFieldTableCell.<ProductBean, Integer>forTableColumn(new IntegerStringConverter()));
//      Providers: List<Providers> | Editable (como ComboBox)
        tcProviders.setCellValueFactory(new PropertyValueFactory<>("providerId"));
        tcProviders.setCellFactory(ComboBoxTableCell.<ProductBean, String>forTableColumn(FXCollections.observableArrayList("Provider 1", "Provider 2", "Provider 3")));
//      Created date: Date Picker | No editable
        tcCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        tcCreatedDate.setEditable(false);

//      Mostrar la ventana.
//        stage.show();
//      En caso de producirse alguna excepción, se mostrará un mensaje al usuario con el texto de la misma en el Label lblInfo.
    }

}
