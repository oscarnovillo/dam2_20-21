package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import servicios.ServiciosTest;

public class Principal {
  @FXML
  private Button boton;

  private Alert alert;

  public Alert getAlert() {
    return alert;
  }

  @FXML
  private void click() {
    ServiciosTest st = new ServiciosTest();
    alert = new Alert(Alert.AlertType.INFORMATION);
    alert.getDialogPane().lookupButton(ButtonType.OK).setId("alertOK");
    alert.setContentText("hola "+st.dameNombre(1)+" "+st.dameNumero());
    alert.showAndWait();
  }
}
