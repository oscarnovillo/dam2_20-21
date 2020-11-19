package gui.controllers;

import dao.modelo.Producto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import servicios.SvProductos_cliente;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductosController implements Initializable {
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtNombre;
    private Alert alert;
    private PrincipalController principalController;
    private SvProductos_cliente sv_productos;

    private Producto productoActual;
    @FXML
    private ListView<Producto> viewProductos;

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        sv_productos = new SvProductos_cliente();
        viewProductos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void clickAdd(ActionEvent actionEvent) {
        sv_productos.addCesta(viewProductos.getSelectionModel().getSelectedItems().stream().collect(Collectors.toList()))
                .peekLeft(s -> {
                    alert.setContentText(s);
                    alert.showAndWait();
                });
        principalController.clickCesta();
    }

    public void cargarView() {
        if (viewProductos.getItems() == null) {
            alert.setContentText("No hay productos disponibles");
            alert.showAndWait();
        } else {
            sv_productos.getTodosProductos()
                    .peek(productos ->
                            viewProductos.getItems().addAll(productos))
                    .peekLeft(s -> {
                        alert.setContentText(s);
                        alert.showAndWait();
                    });
        }
    }

    @FXML
    private void editarProducto(ActionEvent actionEvent) {
    }

    @FXML
    private void addProducto(ActionEvent actionEvent) {
    }

    @FXML
    private void delProducto(ActionEvent actionEvent) {
    }

    @FXML
    private void cogerProductoListView(ActionEvent actionEvent) {
        productoActual = viewProductos.getSelectionModel().getSelectedItem();
        txtNombre.setText(productoActual.getNombreProducto());
        txtPrecio.setText(""+productoActual.getPrecioProducto());
    }
}
