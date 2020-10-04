/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dam2
 */
public class Configuration {

    private static Configuration config;

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

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
