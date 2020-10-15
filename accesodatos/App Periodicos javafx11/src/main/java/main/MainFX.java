package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainFX extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loaderMenu = new FXMLLoader(
        getClass().getResource("/fxml/principal.fxml"));
    BorderPane root = loaderMenu.load();
    Scene scene = new Scene(root);
    primaryStage.setTitle("IES Quevedo");
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setResizable(false);
  }
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}
