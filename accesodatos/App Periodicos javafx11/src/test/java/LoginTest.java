

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginTest {

   // private ControllerLogin controllerLogin;
    private AnchorPane pantallLogin;

//    private ControllerPrincipal controllerPrincipal;
    private BorderPane pantallaPrincipal;


    @Start
    private void start(Stage primaryStage) throws IOException {
        try {
            if (pantallaPrincipal == null) {
                FXMLLoader loaderMenu = new FXMLLoader(LoginTest.class.getResource("/fxml/principal.fxml"));
                pantallaPrincipal = loaderMenu.load();
                //controllerPrincipal = loaderMenu.getController();
                Scene scene = new Scene(pantallaPrincipal);
//                loaderMenu = new FXMLLoader(
//                        ControllerLogin.class.getResource("/fxml/login.fxml"));
//                pantallaPrincipal.setCenter(loaderMenu.load());
//                controllerPrincipal.setControllerlogin(loaderMenu.getController());
//                controllerPrincipal.getControllerlogin().setControllerPrincipal(controllerPrincipal);
                primaryStage.setScene(scene);
            }
        } catch (Exception ex) {
            log.error("",ex);
        }
        primaryStage.show();
    }

    @Test
    @Order(1)
    @DisplayName("Entrar con Usuario")
    void hacerLogin(FxRobot robot) {
        robot.clickOn("#boton");
//        assertNotNull(controllerPrincipal.getU());
        ((TextField) robot.lookup("#textUsuario").query()).setText("ELPLATON");
        ((TextField) robot.lookup("#txtPassword").query()).setText("root");


    }

}
