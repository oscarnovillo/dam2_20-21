package dao.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UsuarioLoginDTO {

    private String id;

    @NotEmpty
    private String name;

    private String password;
}
