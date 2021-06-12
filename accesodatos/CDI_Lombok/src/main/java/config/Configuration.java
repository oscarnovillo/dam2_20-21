/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import config.modelo.ConfigDB;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dam2
 */
@Getter
@Setter
@Singleton
public class Configuration {


    public Configuration() {

        Yaml yaml = new Yaml();

        Iterable<Object> it = yaml
                .loadAll(yaml.getClass().getResourceAsStream("/config/config.yaml"));

        Map<String,String> m = (Map)it.iterator().next();
        this.ruta = m.get("ruta");
        this.user = m.get("user");
        this.password = m.get("password");
        this.ruta = Optional.ofNullable(System.getenv("ruta")).orElseGet(() ->  m.get("ruta"));

    }



    private String ruta;
    private String user;
    private String password;

}
