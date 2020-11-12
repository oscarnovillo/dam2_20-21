package config;


import lombok.Getter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Singleton
public class ExampleSingleton {

    private LocalDateTime now =null;

    public ExampleSingleton() {

        now = LocalDateTime.now();
    }
}
