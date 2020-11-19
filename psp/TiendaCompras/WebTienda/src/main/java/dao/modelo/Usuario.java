package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data @AllArgsConstructor
public class Usuario implements Serializable {
    private long idUsuario;
    @NotEmpty (message = "El username no puede estar vacio")
    private final String username;
    @NotEmpty (message = "La contraseña no puede estar vacia")
    private String password;


    public Usuario(@NotEmpty(message = "El username no puede estar vacio") String username
            , @NotEmpty(message = "La contraseña no puede estar vacia") String password) {
        this.username = username;
        this.password = password;
    }
}
