package eu.rapasoft.controller;


import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class MainController {
    public static final String BLOCKING = "Blocking";
    public static final String NON_BLOCKING_OLD = "Non-Blocking JavaFx";
    public static final String NON_BLOCKING = "Non-Blocking RxJavaFx";
    private static final int NUMBER_OF_TASKS = 10;
    @FXML
    public ComboBox<String> selection;
    @FXML
    public ListView<String> listOfTasks;
    public TextField text;

    @FXML
    public void initialize() {
        ObservableList<String> observableList = FXCollections.observableList(new ArrayList<>());
        listOfTasks.setItems(observableList);
        selection.setItems(FXCollections.observableArrayList("-", BLOCKING, NON_BLOCKING_OLD, NON_BLOCKING));
        selection.valueProperty().addListener((observable, oldValue, value) -> {

            if (value != null)
                switch (value) {
                    case BLOCKING:
                        runTasksJavaFx(observableList);
                        break;
                    case NON_BLOCKING_OLD:
                        runTasksLaterJavaFx(observableList);
                        break;
                    case NON_BLOCKING:
                        try {
                            runTasksRxJavaFx(observableList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
        });
    }

    private void runTasksJavaFx(ObservableList<String> observableList) {
//        try {
//
//            IntStream.range(1, NUMBER_OF_TASKS)
//                    .mapToObj(this::runTask)
//                    .map(result -> result.time > 500 ? new Result(result.name + " (slow)", result.time) : result)
//                    .forEach(result -> observableList.add(result.toString()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void runTasksLaterJavaFx(ObservableList<String> observableList) {
        IntStream.range(1, NUMBER_OF_TASKS)
                .forEach(i -> Platform.runLater(() -> {
                    Result result = null;
                    try {
                        result = runTask(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result.time > 500) {
                        result = new Result(result.name + " (slow)", result.time);
                    }
                    observableList.add(result.toString());
                }));
    }

    private void runTasksRxJavaFx(ObservableList<String> observableList) throws Exception {

        selection.setDisable(true);
        Single.just(runTask(20))
                .subscribeOn(Schedulers.computation())
                .doFinally(() -> selection.setDisable(false))
                .map(result -> result.time > 500 ? new Result(result.name + " (slow)", result.time) : result)
                .map(Result::toString)
                .concatWith(
                        Single.fromCallable(() -> {
                            Thread.sleep(10000); //  imitate expensive computation
//            if (Math.random() > 0.5)
//            throw new Exception("eror");
                            return "Done";
                        })
                )
                .observeOn(JavaFxScheduler.platform())
                .subscribe(result -> observableList.add(result),
                        throwable -> observableList.add(throwable.toString()))


        ;


        Single.fromCallable(() -> {
            Thread.sleep(5000); //  imitate expensive computation
//            if (Math.random() > 0.5)
//            throw new Exception("eror");
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())

                //.doFinally(() -> selection.setDisable(false))
                //.doOnError(Throwable::printStackTrace)
                .subscribe(s -> {
                            observableList.add(s);
                            selection.getItems().clear();
//                            Platform.runLater(() -> {
//                                observableList.add(s);
//                            });
                            text.setText("hola");
                        },
                        s -> {
                            observableList.add(s.toString());
                        });

        Observable<Result> ob = Observable.range(1, NUMBER_OF_TASKS)
                .subscribeOn(Schedulers.single())

                .map(this::runTask)
                .map(result -> result.time > 500 ? new Result(result.name + " (slow)", result.time) : result);

                ob.observeOn(JavaFxScheduler.platform())
                .doOnError(throwable -> observableList.add(throwable.toString()))
                .forEach(result -> observableList.add(result.toString()));
    }

    private Result runTask(Integer i) throws Exception {
        long currentTime = System.currentTimeMillis();

        String name = "Task" + i;
        long sleepDuration = (long) (Math.random() * 1000);

        if (i == 8) {
            throw new Exception("(slow)");
        }

        try {
            Thread.sleep(sleepDuration);
            return new Result(name, sleepDuration);
        } catch (Exception e) {
            return new Result("-", 0);
        } finally {
            System.out.println(name + " took " + (System.currentTimeMillis() - currentTime) + " ms");
        }
    }

    static class Result {
        public final String name;
        public final long time;

        public Result(String name, long time) {
            this.name = name;
            this.time = time;
        }

        @Override
        public String toString() {
            return name + " (" + time + " ms)";
        }
    }

}
