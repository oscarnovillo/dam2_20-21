package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class Lector {
    private long id;
    private final String usuario;
    private final String password;
    private final String mail;
    private final String nombre;
    private final LocalDate birth;



}
