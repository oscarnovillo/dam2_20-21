package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.SneakyThrows;
import servicios.ServiciosTest;


public class Principal {
  @FXML
  private Button boton;

  @FXML
  private BorderPane root;

  private String usuario;

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  private Alert alert;

  public Alert getAlert() {
    return alert;
  }

  @FXML
  private void click() {
    ServiciosTest st = new ServiciosTest();

    cargarLectores();

//    alert = new Alert(Alert.AlertType.INFORMATION);
//    alert.getDialogPane().lookupButton(ButtonType.OK).setId("alertOK");
//    alert.setContentText("hola "+st.dameNombre(1)+" "+st.dameNumero());
//    alert.showAndWait();
  }


  FXMLLoader fxmlloaderPantalla2;
  AnchorPane panePantalla2;


  @SneakyThrows
  public void cargarLectores() {
    if (panePantalla2 == null) {
      fxmlloaderPantalla2 = new FXMLLoader();
      panePantalla2 = fxmlloaderPantalla2.load(getClass().getResourceAsStream("/fxml/lectores.fxml"));
      Lectores pantall = fxmlloaderPantalla2.getController();
      fxmlloaderPantalla2.setRoot(null);
      fxmlloaderPantalla2.setController(null);
      panePantalla2 = fxmlloaderPantalla2.load(getClass().getResourceAsStream("/fxml/lectores.fxml"));
       pantall = fxmlloaderPantalla2.getController();

    }
    root.setCenter(panePantalla2);
  }

}
