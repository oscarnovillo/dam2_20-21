package gui.controllers;

import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import servicios.SvUsuario_cliente;
import utils.Constantes;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

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

//        var tarea = new Task<String>() {
//            public StringProperty test;
//            @Override
//            protected String call() throws Exception {
//                HttpUrl.Builder urlBuilder
//                        = HttpUrl.parse(Constantes.BASE_URL + "/visitas").newBuilder();
//
//                String url = urlBuilder.build().toString();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .build();
//
//
//                Call call = clientOK.newCall(request);
//                Response response = call.execute();
//                CifradoCesar cf = new CifradoCesar();
//                cf.descifra(response.body().string(),1);
//
//                return response.body().string();
//                //return "OK";
//            }
//        };
//
//
//        fxText.textProperty().bind(tarea.valueProperty());
//        tarea.setOnSucceeded(workerStateEvent -> {
//            fxText.setText(tarea.getValue());
//        });
//        tarea.setOnFailed(workerStateEvent ->
//                Logger.getLogger("PantallaInicio")
//                        .log(Level.SEVERE, "error ",
//                                workerStateEvent.getSource().getException()));
//        new Thread(tarea).start();
//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        executorService.submit(tarea);



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
