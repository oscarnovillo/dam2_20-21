package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import servicios.SvUsuario_cliente;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Alert alert;
    private SvUsuario_cliente sv_usuario;
    private PrincipalController principalController;
    @FXML
    private TextField textUsuario;
    @FXML
    private PasswordField textPassword;

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        sv_usuario = new SvUsuario_cliente();
    }

    @FXML
    private void clickEntrar(ActionEvent actionEvent) {
        String entrar;
        entrar = sv_usuario.loginUsuario(textUsuario.getText(), textPassword.getText());
        alert.setContentText(entrar);
        if (entrar.isEmpty()) {
            principalController.setNameUsuario(textUsuario.getText());
            principalController.cargarBienvenida();
        } else {
            alert.showAndWait();
        }
    }

    public void limpiarCamposLogin() {
        textUsuario.clear();
        textPassword.clear();
    }

}
