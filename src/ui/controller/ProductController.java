/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import DTO.ProductBean;
import DTO.ProviderBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import businessLogic.product.ProductManagerFactory;
import businessLogic.provider.ProviderManagerFactory;
import ui.cellFactories.DatePickerTableCell;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Cargar los elementos en el ComboBox de búsqueda
        comboSearch.getItems().addAll("Product", "Created date");
        // Establecer por defecto "Product"
        comboSearch.setValue("Product");
        // Añadir listener para el ComboBox
        comboSearch.valueProperty().addListener(this::handleComboBoxChange);
        // Enfocar el campo de búsqueda
        tfSearch.requestFocus();
        // Habilitar el Botón btnSearch.
        btnSearch.setDisable(false);
        // Habilitar el Botón btnAdd.
        btnAdd.setDisable(false);
        // Ocultar el Label lblInfo.
        lblInfo.setVisible(false);

        // Cargar la tabla:
        // Establecer como editables las columnas Product, Price, Stock y Providers.
        // Llamar al método de lógica getAllProducts() y obtener una lista con todos los
        // registros almacenados de los productos.
        // Cargar los datos obtenidos en la Table View tableProduct, que tiene las
        // siguientes columnas:
        // Product: String | Editable (como TextFieldtableCell)
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcName.setCellFactory(TextFieldTableCell.<ProductBean>forTableColumn());
        tcName.setOnEditCommit(event -> handleEditCommit(event, "name"));

        // Price: Float | Editable (como SpinnerTableCell)
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        // TODO cambiar a factoria de celda de Spinner
        tcPrice.setCellFactory(
                TextFieldTableCell.<ProductBean, Float>forTableColumn(new FloatStringConverter()));
        tcPrice.setOnEditCommit(event -> handleEditCommit(event, "price"));

        // Mensual consume: Consume() | No editable
        tcMonthlyConsume.setCellValueFactory(new PropertyValueFactory<>("monthlyConsume"));
        tcMonthlyConsume.setEditable(false);

        // Stock: Integer | Editable (como SpinnerTableCell)
        tcStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        // TODO cambiar a factoria de celda de Spinner
        tcStock.setCellFactory(
                TextFieldTableCell.<ProductBean, Integer>forTableColumn(new IntegerStringConverter()));
        tcStock.setOnEditCommit(event -> handleEditCommit(event, "stock"));

        // Providers: List<Providers> | Editable (como ComboBox)
        try {
            tcProviders.setCellValueFactory(new PropertyValueFactory<>("providerId"));
            List<ProviderBean> providerList = ProviderManagerFactory.get().getAllProviders(new GenericType<List<ProviderBean>>() {
            });
            if (providerList == null) {
                providerList = new ArrayList<>();
            }
            ObservableList<ProviderBean> providerData = FXCollections.observableArrayList(providerList);
            tcProviders.setCellFactory(ComboBoxTableCell.forTableColumn(providerData));
            tcProviders.setOnEditCommit(event -> handleEditCommit(event, "providers"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Created date: Date Picker | No editable
        tcCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        tcCreatedDate.setCellFactory(new Callback<TableColumn<ProductBean, Date>, TableCell<ProductBean, Date>>() {
            @Override
            public TableCell<ProductBean, Date> call(TableColumn<ProductBean, Date> param) {
                return new DatePickerTableCell<>(param);
            }
        });
        tcCreatedDate.setStyle("-fx-alignment: center;");
        tcCreatedDate.setEditable(false);

        // Mostrar la ventana.
        // stage.show();
        // En caso de producirse alguna excepción, se mostrará un mensaje al usuario con
        // el texto de la misma en el Label lblInfo.
        showAllProducts();
    }

    private <T> void handleEditCommit(CellEditEvent<ProductBean, T> event, String fieldName) {
        try {
            TablePosition<ProductBean, T> pos = event.getTablePosition();
            T newValue = event.getNewValue();

            if (newValue == null || (newValue instanceof String && ((String) newValue).trim().isEmpty())) {
                throw new IllegalArgumentException("El valor ingresado no es válido.");
            }

            int row = pos.getRow();
            ProductBean product = event.getTableView().getItems().get(row);
            ProductBean productCopy = product.clone();

            // Actualiza el objeto original y su copia, y luego actualiza la capa lógica con la copia modificada
            switch (fieldName) {
                case "name":
                    if (newValue instanceof String) {
                        productCopy.setName((String) newValue);
                        ProductManagerFactory.get().updateProduct(productCopy);
                        product.setName((String) newValue);
                    }
                    break;
                case "price":
                    if (newValue instanceof Float) {
                        productCopy.setPrice((Float) newValue);
                        ProductManagerFactory.get().updateProduct(productCopy);
                        product.setPrice((Float) newValue);
                    }
                    break;
                case "stock":
                    if (newValue instanceof Integer) {
                        productCopy.setStock((Integer) newValue);
                        ProductManagerFactory.get().updateProduct(productCopy);
                        product.setStock((Integer) newValue);
                    }
                    break;
                case "providers":
                    if (newValue instanceof ProviderBean) {
                        productCopy.setProviderId((ProviderBean) newValue);
                        ProductManagerFactory.get().updateProduct(productCopy);
                        product.setProviderId((ProviderBean) newValue);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Campo desconocido: " + fieldName);
            }

            event.getTableView().refresh();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            event.consume();
        }
    }

    private void handleComboBoxChange(ObservableValue<? extends String> observable, Object oldValue, Object newValue) {
        if ("createDate".equals(newValue)) {
            // Ocultar el campo de búsqueda de texto
            tfSearch.setVisible(false);
            tfSearch.clear();

            // Mostrar los campos de fecha
            //dpSearch.setVisible(true);
        } else if ("Product".equals(newValue)) {
            // Ocultar los campos de fecha
            //dpSearch.setVisible(false);
            // Mostrar el campo de búsqueda de texto
            tfSearch.setVisible(true);
            tfSearch.clear();
        }
    }

    private void onSearchButtonClicked(ActionEvent event) {
        String searchType = comboSearch.getValue().toString();
        try {
            List<ProductBean> productList = null;

            switch (searchType) {
                case "Product":
                    // Comprobar si el campo de texto no está vacío antes de buscar productos por nombre
                    String searchText = tfSearch.getText();
                    if (searchText != null && !searchText.trim().isEmpty()) {
                        productList = ProductManagerFactory.get().getProductByName(new GenericType<List<ProductBean>>() {
                        }, searchText.trim());
                    } else {
                        // Mostrar todos los productos si el campo está vacío
                        showAllProducts();
                    }
                    break;

                case "Created date":
                    // Obtener la fecha del DatePicker y comprobar si no está vacía
                    if (dpSearch.getValue() != null) {
                        String date = dpSearch.getValue().toString();
                        if (!date.isEmpty()) {
                            productList = ProductManagerFactory.get().getProductsByCreatedDate(new GenericType<List<ProductBean>>() {
                            }, date);
                        } else {
                            showAllProducts();
                        }
                    } else {
                        // Mostrar todos los productos si no se selecciona una fecha
                        showAllProducts();
                    }
                    break;

                default:
                    // Caso por defecto para manejar valores inesperados en searchType
                    System.out.println("Opción de búsqueda no válida: " + searchType);
                    showAllProducts();
                    break;
            }

            if (productList != null) {
                ObservableList<ProductBean> productData = FXCollections.observableArrayList(productList);
                tbProduct.setItems(productData);
            }
        } catch (WebApplicationException e) {
            System.err.println("Error fetching animals: " + e.getMessage());
        }
    }

    private void showAllProducts() {
        try {
            List<ProductBean> allProducts = ProductManagerFactory.get()
                    .getAllProducts(new GenericType<List<ProductBean>>() {
                    });
            ObservableList<ProductBean> productData = FXCollections.observableArrayList(allProducts);
            tbProduct.setItems(productData);
        } catch (WebApplicationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Error al cargar los productos: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
