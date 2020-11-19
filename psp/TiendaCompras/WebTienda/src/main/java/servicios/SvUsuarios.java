package servicios;

import dao.DaoUsuario;
import dao.modelo.Usuario;
import io.vavr.control.Either;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.stream.Collectors;

public class SvUsuarios {
    public Either<String, Usuario> usuarioLogin(Usuario usuarioTemp) {
        Either<String, Usuario> resultado;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        String error = validator.validate(usuarioTemp).stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));

        if (error.isEmpty()) {
            DaoUsuario daoUsuario = new DaoUsuario();
            HashPassword hashP = new HashPassword();
            usuarioTemp.setPassword(hashP.hashPassword(usuarioTemp.getPassword()));
            resultado = daoUsuario.getUsuarioLogin(usuarioTemp);
        } else {
            resultado = Either.left(error);
        }
        return resultado;
    }
}
