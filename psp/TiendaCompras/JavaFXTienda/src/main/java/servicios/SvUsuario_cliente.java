package servicios;

import dao.DaoUsuario_cliente;
import lombok.SneakyThrows;

public class SvUsuario_cliente {
    @SneakyThrows
    public String loginUsuario(String usuario, String password) {
        DaoUsuario_cliente daoUsuario_cliente = new DaoUsuario_cliente();
        return daoUsuario_cliente.usuario_login(usuario, password);
    }

    @SneakyThrows
    public String logOut() {
        DaoUsuario_cliente daoUsuario_cliente = new DaoUsuario_cliente();
        return daoUsuario_cliente.logOut();
    }
}
