package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor
public class Lector {
    private long id;
    private final String usuario;
    private final String password;
    private final String mail;
    private final String nombre;
    private final LocalDate birth;



    public Lector(String usuario, String password, String mail, String nombre, LocalDate birth) {
        this.usuario = usuario;
        this.password = password;
        this.mail = mail;
        this.nombre = nombre;
        this.birth = birth;
    }
}
