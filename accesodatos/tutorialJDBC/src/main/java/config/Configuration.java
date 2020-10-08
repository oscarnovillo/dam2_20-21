package config;

import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class Configuration {
    private static Configuration config = null;

    private Configuration() {

    }

    public static Configuration getInstance() {

        if (config == null) {
            try {
                Yaml yaml = new Yaml();
                config = yaml.loadAs(new FileInputStream("config/config.yaml"),
                        Configuration.class);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }




    private String ruta;
    private String user;
    private String password;

}
