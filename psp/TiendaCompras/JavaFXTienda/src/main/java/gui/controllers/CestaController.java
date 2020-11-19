package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import servicios.SvProductos_cliente;
import utils.Constantes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CestaController implements Initializable {
    private Alert alert;
    private SvProductos_cliente sv_productos;
    private PrincipalController principalController;
    @FXML
    private ListView viewProductosCesta;

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        sv_productos = new SvProductos_cliente();

    }

    @FXML
    private void clickComprar(ActionEvent actionEvent) {
        String buy = sv_productos.buyCesta();
        if (buy.equals(Constantes.COMPRA_EXITOSA)) {
            viewProductosCesta.getItems().clear();
        }
        alert.setContentText(buy);
        alert.showAndWait();
    }

    @FXML
    private void clickVaciar(ActionEvent actionEvent) {
        String clear = sv_productos.clearCesta();
        if (viewProductosCesta.getItems() != null) {
            viewProductosCesta.getItems().clear();
        }
        alert.setContentText(clear);
        alert.showAndWait();
    }

    @FXML
    private void clickVolver(ActionEvent actionEvent) {
        principalController.clickProductos();
    }

    public void cargaViewCesta() {
        viewProductosCesta.getItems().clear();
        sv_productos.verCesta()
                .peek(strings -> viewProductosCesta.getItems().addAll(strings))
                .peekLeft(s -> {
                    alert.setContentText(s);
                    alert.showAndWait();
                });

    }
}
