package dao;

import dao.modelo.Usuario;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Log4j2
public class DaoUsuario {
    private static final String QUERY_GET_USUARIO = "select * from users where username=? and password=?";

    public Either<String,Usuario> getUsuarioLogin(Usuario usuarioTempLogin) {
        Either<String,Usuario> resultado;
        Connection con = null;
        DBConnection db = new DBConnection();
        Statement stmt = null;
        PreparedStatement pst;
        ResultSet rs = null;
        try {
            con = db.getConnection();
            stmt = con.createStatement();
            pst = con.prepareStatement(QUERY_GET_USUARIO);
            pst.setString(1, usuarioTempLogin.getUsername());
            pst.setString(2, usuarioTempLogin.getPassword());
            rs = pst.executeQuery();
            if (rs.next()) {
                Usuario usuarioLogin = new Usuario(rs.getLong("id_user"), rs.getString("username"), rs.getString("password"));
                resultado =  Either.right(usuarioLogin);
            }
            else
                resultado = Either.left("usuario no valido");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            resultado =  Either.left("Error en la BD");
        } finally {
            db.cerrarResultSet(rs);
            db.cerrarStatement(stmt);
            db.cerrarConexion(con);
        }
        return resultado;
    }
}
