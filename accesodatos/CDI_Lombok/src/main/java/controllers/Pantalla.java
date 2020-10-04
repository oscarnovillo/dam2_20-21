package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import servicios.ServiciosTest;

import javax.inject.Inject;


public class Pantalla {


    public void setP(Principal p) {
        this.p = p;
    }

    private Principal p ;
    @Inject
    private ServiciosTest st;

    @FXML
    public Button boton;

    @FXML
    private void click(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("alertOK");
        alert.setContentText("hola "+st.dameNombre(1)+" "+st.dameNumero());
        alert.showAndWait();
        p.cargarPantalla2();

    }
}
