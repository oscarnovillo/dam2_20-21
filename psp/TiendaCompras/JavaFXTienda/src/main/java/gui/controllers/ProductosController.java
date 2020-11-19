package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import servicios.SvProductos_cliente;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductosController implements Initializable {
    private Alert alert;
    private PrincipalController principalController;
    private SvProductos_cliente sv_productos;
    @FXML
    private ListView<String> viewProductos;

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
        sv_productos.addCesta(viewProductos.getSelectionModel().getSelectedItems())
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
                    .peek(strings ->
                            viewProductos.getItems().addAll(strings))
                    .peekLeft(s -> {
                        alert.setContentText(s);
                        alert.showAndWait();
                    });
        }
    }
}
