package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import servicios.SvUsuario_cliente;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {
    private Alert alert;

    @Getter
    @Setter
    private String nameUsuario;

    private AnchorPane pantalla_login;
    private LoginController controller_login;

    private AnchorPane pantalla_bienvenida;
    private BienvenidaController controller_bienvenida;

    private AnchorPane pantalla_productos;
    private ProductosController controller_productos;

    private AnchorPane pantalla_cesta;
    private CestaController controller_cesta;

    @FXML
    private BorderPane pantallaPrincipal;

    @FXML
    private MenuBar menuTienda;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        cargarLogin();
        menuTienda.setVisible(false);
    }

    @SneakyThrows
    public void cargarLogin() {
        if (pantalla_login == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/loginUser.fxml"));
            pantalla_login = fxmlLoader.load();
            controller_login = fxmlLoader.getController();
            controller_login.setPrincipalController(this);
        }
        pantallaPrincipal.setCenter(pantalla_login);
    }

    @SneakyThrows
    public void cargarBienvenida() {
        if (pantalla_bienvenida == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/bienvenida.fxml"));
            pantalla_bienvenida = fxmlLoader.load();
            controller_bienvenida = fxmlLoader.getController();
        }
        menuTienda.setVisible(true);
        controller_bienvenida.bienvenidaUsuario(nameUsuario);
        pantallaPrincipal.setCenter(pantalla_bienvenida);
    }

    @SneakyThrows
    public void clickProductos() {
        if (pantalla_productos == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/productos.fxml"));
            pantalla_productos = fxmlLoader.load();
            controller_productos = fxmlLoader.getController();
            controller_productos.setPrincipalController(this);
        }
        controller_productos.cargarView();
        pantallaPrincipal.setCenter(pantalla_productos);
    }

    @SneakyThrows
    public void clickCesta() {
        if (pantalla_cesta == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/cesta.fxml"));
            pantalla_cesta = fxmlLoader.load();
            controller_cesta = fxmlLoader.getController();
            controller_cesta.setPrincipalController(this);
        }
        controller_cesta.cargaViewCesta();
        pantallaPrincipal.setCenter(pantalla_cesta);
    }

    @SneakyThrows
    public void clickSalir(ActionEvent actionEvent) {
        SvUsuario_cliente sv_usuario = new SvUsuario_cliente();
        alert.setContentText(sv_usuario.logOut());
        alert.showAndWait();
        limpiarPantallas();
        cargarLogin();

    }

    public void limpiarPantallas() {

        pantalla_productos = null;
        controller_login.limpiarCamposLogin();
        menuTienda.setVisible(false);
    }
}
