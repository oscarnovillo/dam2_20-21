package modelo;

import lombok.Data;

import java.util.Objects;

@Data
public class Periodico {

    private final String nombre;
    private final String precio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periodico periodico = (Periodico) o;
        return nombre.equals(periodico.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
