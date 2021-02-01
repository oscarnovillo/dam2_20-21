package modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;



@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private ObjectId id;

    private String user;

    private String password;
    private String mail;

    private String dni;
    private boolean multa = false;

    @Override
    public String toString() {
        return "Usuario: " + user;
    }
}
