package controllers;

import dao.modelo.Lector;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import servicios.ServiciosLectores;

import java.time.LocalDate;

public class Lectores {
    public TextField textUsuario;
    public PasswordField txtPassword;
    public TextField txtMail;
    public TextField txtNombre;
    public DatePicker fechaNacimiento;
    public ListView listLectores;

    private ServiciosLectores sl = new ServiciosLectores();
    private Alert alert;

    public void borrarLector(ActionEvent actionEvent) {


    }

    public void addLector(ActionEvent actionEvent) {
        Lector l = new Lector("ho","h","kk","kokkkkkzxczxc", LocalDate.now());

        boolean correcto = sl.addLector(l);
        var error = "usuario añadido corractamente";

        if (!correcto)
        {
            error = " error añadiendoi uysuario";
        }
        else
        {
            listLectores.getItems().add(l);
        }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("alertOK");
        alert.setContentText(error);
        alert.showAndWait();



    }

    public void actualizar(ActionEvent actionEvent) {
        listLectores.getItems().clear();
        listLectores.getItems().addAll(sl.getLectores());
    }
}
