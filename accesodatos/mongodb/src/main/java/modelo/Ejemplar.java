package modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ejemplar {

    private ObjectId id;
    private String estado;
    private List<Prestamo> prestamos;
}
