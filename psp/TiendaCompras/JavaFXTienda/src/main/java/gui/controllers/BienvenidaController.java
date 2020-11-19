package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class BienvenidaController implements Initializable {
    private PrincipalController principalController;
    @FXML
    private Label textBienvenida;

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void bienvenidaUsuario(String name){
        textBienvenida.setText("Bienvenido " + name );
    }

}
