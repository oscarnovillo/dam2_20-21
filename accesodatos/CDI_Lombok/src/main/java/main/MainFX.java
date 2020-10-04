package main;

import gui.StartupScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

public class MainFX  {
  @Inject
  FXMLLoader fxmlLoader;

  public void start(@Observes @StartupScene Stage stage) {
    try {
      //fxmlLoader = new FXMLLoader();
      //URL arquivoFXML = getClass().getResource("./hello-world.fxml");
//			SeContainerInitializer initializer = SeContainerInitializer.newInstance();
//			final SeContainer container = initializer.initialize();
//			FXMLLoader loaderMenu = new FXMLLoader(
//					getClass().getResource("/hello-world.fxml"));
//			loaderMenu.setController(container.select(FXController.class).get());
      Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream("/fxml/principal.fxml"));
      stage.setScene(new Scene(fxmlParent, 300, 100));
      stage.setTitle("Hello World FXML and JavaFX");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
