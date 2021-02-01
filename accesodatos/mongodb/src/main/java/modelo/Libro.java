package modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;


import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    private ObjectId id;

    private String titulo;

    private String autor;

    private String genero;
    private List<Ejemplar> ejemplares = new ArrayList<>();

    @Override
    public String toString() {
        return "Titulo: " + titulo;
    }
}
