package gui.controllers;

import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import servicios.SvProductos_cliente;

import java.net.URL;
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

        Either<String, String> respuesta = sv_productos.buyCesta();
        respuesta
                .peek(s -> {
                    viewProductosCesta.getItems().clear();
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                })
                .peekLeft(s -> {
                    alert.setAlertType(Alert.AlertType.ERROR);
                });
        alert.setContentText(respuesta.isRight() ? respuesta.get() : respuesta.getLeft());
        alert.showAndWait();
    }

    @FXML
    private void clickVaciar(ActionEvent actionEvent) {
        Either<String, String> respuesta = sv_productos.clearCesta();
        respuesta
                .peek(s -> {
                    viewProductosCesta.getItems().clear();
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                })
                .peekLeft(s -> {
                    alert.setAlertType(Alert.AlertType.ERROR);
                });
        alert.setContentText(respuesta.isRight() ? respuesta.get() : respuesta.getLeft());
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
