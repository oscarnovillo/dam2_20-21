import com.gluonhq.ignite.dagger.DaggerContext;
import dagger.Provides;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import servicios.ModuleServicios;
import servicios.ServiciosComponent;


import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;

public class MainFX extends Application {

    @Inject
    FXMLLoader fxmlLoader;

    DaggerContext context = new DaggerContext(this,() -> Arrays.asList(ModuleServicios.class));

    @Override
    public void start(Stage stage) throws Exception {
        try {
            context.init();
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream("/fxml/principal.fxml"));
            stage.setScene(new Scene(fxmlParent, 300, 100));
            stage.setTitle("Hello World FXML and JavaFX");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
