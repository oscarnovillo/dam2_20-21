package dao.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Data
public class UsuarioGetDTO {

    @NotEmpty
    private String name;
}
