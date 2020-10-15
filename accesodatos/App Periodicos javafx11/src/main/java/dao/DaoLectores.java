package dao;

import dao.modelo.Lector;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

@Log4j2
public class DaoLectores {


    private static String QUERY_INSERTAR_USUARIO=
            "insert into usuarios  (user,password,primera_vez,mail) " +
                    "values(?,?,true,?) ";

    private static String QUERY_INSERTAR_LECTOR=" insert into lectores " +
            "(id, nombre, fechaNacimiento) VALUES (?,?,?)";

    public boolean addLector(Lector l)
    {
        var añadido = false;
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = db.getConnection();
            stmt = con.prepareStatement
                    (QUERY_INSERTAR_USUARIO,
                            Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, l.getUsuario());
            stmt.setString(2, l.getPassword());
            stmt.setString(3, l.getMail());


            int numeroFilas = stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();

            long id = 0;
            if (rs != null && rs.next()) {
                id = rs.getLong(1);
            }

            l.setId(id);
            stmt = con.prepareStatement
                    (QUERY_INSERTAR_LECTOR,
                            Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, (int)l.getId());
            stmt.setString(2, l.getNombre());
           stmt.setDate(3,
                    java.sql.Date.valueOf(l.getBirth()));
            numeroFilas = stmt.executeUpdate();
            añadido = true;

        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            db.cerrarResultSet(rs);
            db.cerrarStatement(stmt);
            db.cerrarConexion(con);
        }



        return añadido;
    }
}
