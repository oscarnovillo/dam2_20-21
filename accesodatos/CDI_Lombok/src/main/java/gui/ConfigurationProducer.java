package gui;

import config.Configuration;
import javafx.fxml.FXMLLoader;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

public class ConfigurationProducer {


    @Produces
    public Configuration createConfiguration() {

        return Configuration.getInstance();
    }

}
