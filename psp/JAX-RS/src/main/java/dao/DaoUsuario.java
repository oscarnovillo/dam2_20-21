package dao;

import EE.errores.ApiError;
import EE.errores.CustomException;
import dao.modelo.Usuario;
import io.vavr.control.Either;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoUsuario {

    public static List<Usuario> usuarios = new ArrayList<>();

    public Either<ApiError, Usuario> dameUno(String id)
    {
        Usuario u = usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst().orElse(null);
        if (u!=null) {
            return Either.right(u);
        }
        else
        {
            return Either.left(new ApiError("error not found", LocalDate.now()));
        }
    }

    public Either<ApiError, List<Usuario>> dameTodos()
    {
        if ( usuarios.size()==0)
        {
            throw new CustomException("lista vacia",Response.Status.NOT_FOUND);
        }
        return Either.right(usuarios);
    }

    public Either<ApiError, Usuario> addUser(Usuario user)
    {
        user.setId("" + usuarios.size());
        usuarios.add(user);
        return Either.right(user);
    }
}
