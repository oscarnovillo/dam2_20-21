package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;


@Data
public class Alumno {

    private final String nombre;
    private final int edad;
    private final LocalDate birth;
}
