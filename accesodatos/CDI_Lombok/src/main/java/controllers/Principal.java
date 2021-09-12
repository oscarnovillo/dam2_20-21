package controllers;

import config.ExampleSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.SneakyThrows;
import servicios.ServiciosTest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

public class Principal {

    @FXML
    private BorderPane root;
    @FXML
    private Button boton;

    private Alert alert;

    public Alert getAlert() {
        return alert;
    }

    private final ServiciosTest st;


    private final ExampleSingleton eps;


    private FXMLLoader fxmlloaderPantalla;
    private AnchorPane panePantalla;


    private FXMLLoader fxmlloaderPantalla2;
    private AnchorPane panePantalla2;


    @Inject
    public Principal(ServiciosTest st, ExampleSingleton eps, FXMLLoader fxmlloaderPantalla, FXMLLoader fxmlloaderPantalla2) {

        this.st = st;
        this.eps = eps;
        this.fxmlloaderPantalla = fxmlloaderPantalla;
        this.fxmlloaderPantalla2 = fxmlloaderPantalla2;
    }



    @SneakyThrows
    public void cargarPantalla2() {
       // ServiciosTest sat = new ServiciosTest();
        if (panePantalla2 == null) {
            panePantalla2 = fxmlloaderPantalla2.load(getClass().getResourceAsStream("/fxml/pantalla2.fxml"));
            Pantalla2 pantall = fxmlloaderPantalla2.getController();
            pantall.boton.setText("funciona "+eps.getNow());
        }
        root.setCenter(panePantalla2);
    }


    @SneakyThrows
    public void cargarPantalla1() {
        if (panePantalla == null) {
            panePantalla = fxmlloaderPantalla.load(getClass().getResourceAsStream("/fxml/pantalla.fxml"));
            Pantalla pantall = fxmlloaderPantalla.getController();

            pantall.boton.setText("conseguido2");
            pantall.setP(this);

        }
        root.setCenter(panePantalla);
    }

    @FXML
    private void click() {
        // ServiciosTest st = new ServiciosTest();
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("alertOK");
        alert.setContentText(eps.getNow()+"hola " + st.dameNombre(1) + " " + st.dameNumero());
        alert.showAndWait();
        cargarPantalla1();
    }

    public void clickMenuItem(ActionEvent actionEvent) {


    }
}
