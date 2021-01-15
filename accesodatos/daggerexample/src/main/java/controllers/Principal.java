package controllers;

import javafx.event.ActionEvent;
import servicios.DaggerServiciosComponent;
import servicios.ServiciosMain;

public class Principal {
    public void click(ActionEvent actionEvent) {

        ServiciosMain m = DaggerServiciosComponent.create().getServiciosMain();

        System.out.println(m.numeros());
    }
}
