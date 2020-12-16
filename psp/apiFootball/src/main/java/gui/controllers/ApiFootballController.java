package gui.controllers;

import dao.DaoAreas;
import dao.modelo.Area;
import dao.modelo.Competition;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ApiFootballController implements Initializable {
    public ListView<Area> listAreas;
    public ListView listCompetitions;
    private Alert alert;

    private PrincipalController principalController;


    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.INFORMATION);

    }

    @FXML
    private void clickEntrar(ActionEvent actionEvent) {

        var tarea = new Task<Either<String, List<Area>>>() {
            //public StringProperty test;
            @Override
            protected Either<String, List<Area>> call() throws Exception {

                DaoAreas dao = new DaoAreas();

                Thread.sleep(5000);
                return dao.getAreas();
            }
        };


//        fxText.textProperty().bind(tarea.valueProperty());
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(areas -> listAreas.getItems().addAll(areas))
                    .peekLeft(s -> {
                        alert.setContentText(s);
                        alert.showAndWait();
                    }))
                    .onFailure(throwable -> {
                        alert.setContentText(throwable.getMessage());
                        alert.showAndWait();
                    });
            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        tarea.setOnFailed(workerStateEvent -> {
            alert.setContentText(workerStateEvent.getSource().getException().getMessage());
            alert.showAndWait();
            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        new Thread(tarea).start();
//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        executorService.submit(tarea);
        this.principalController.getPantallaPrincipal().setCursor(Cursor.WAIT);


    }


    public void cargarCompeticiones(ActionEvent actionEvent) {
        var tarea = new Task<Either<String, List<Competition>>>() {
            //public StringProperty test;
            @Override
            protected Either<String, List<Competition>> call() throws Exception {

                DaoAreas dao = new DaoAreas();

                Thread.sleep(5000);
                return dao.getCompetitions(listAreas.getSelectionModel().getSelectedItem());
            }
        };


//        fxText.textProperty().bind(tarea.valueProperty());
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(competitions -> listCompetitions.getItems().addAll(competitions))
                    .peekLeft(s -> {
                        alert.setContentText(s);
                        alert.showAndWait();
                    }))
                    .onFailure(throwable -> {
                        alert.setContentText(throwable.getMessage());
                        alert.showAndWait();
                    });
            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        tarea.setOnFailed(workerStateEvent -> {
            alert.setContentText(workerStateEvent.getSource().getException().getMessage());
            alert.showAndWait();
            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        new Thread(tarea).start();
//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        executorService.submit(tarea);
        this.principalController.getPantallaPrincipal().setCursor(Cursor.WAIT);


    }
}
