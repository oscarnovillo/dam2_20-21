package modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {
    private String idEjemplar;

    private String dniLector;
    private LocalDate fechaPrestado;
    private LocalDate fechaDevolucion;
}
