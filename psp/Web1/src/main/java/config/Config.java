package config;

import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
@Singleton
public class Config {

    private String ruta;
    private String user;
    private String password;
    private String driverDB;

    public Config() {
        Yaml yaml = new Yaml();
        Config config = yaml.loadAs(yaml.getClass().getResourceAsStream("/config/config.yaml"),
                Config.class);
        this.ruta = config.ruta;
        this.user = config.user;
        this.password = config.password;
        this.driverDB = config.driverDB;


    }

}


