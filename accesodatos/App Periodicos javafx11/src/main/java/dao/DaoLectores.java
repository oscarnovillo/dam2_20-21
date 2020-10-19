package dao;

import dao.modelo.Lector;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

@Log4j2
public class DaoLectores {


    private static final String SELECT_LECTORES =
            "select * from lectores l inner join usuarios u on l.id = u.id";
    private static String QUERY_INSERTAR_USUARIO =
            "insert into usuarios  (user,password,primera_vez,mail) " +
                    "values(?,?,true,?) ";

    private static String QUERY_INSERTAR_LECTOR = " insert into lectores " +
            "(id, nombre, fechaNacimiento) VALUES (?,?,?)";

    public boolean addLector(Lector l) {
        var añadido = false;
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = db.getConnection();
            con.setAutoCommit(false);
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
            stmt.setInt(1, (int) l.getId());
            stmt.setString(2, l.getNombre());
            stmt.setDate(3,
                    java.sql.Date.valueOf(l.getBirth()));
            numeroFilas = stmt.executeUpdate();
            añadido = true;
            con.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            db.rollbackCon(con);
        } finally {
            db.cerrarResultSet(rs);
            db.cerrarStatement(stmt);
            db.cerrarConexion(con);
        }


        return añadido;
    }

    public List<Lector> getLectores() {
        List<Lector> lectores = new ArrayList<>();
        Connection con = null;
        DBConnection db = new DBConnection();
        Statement stmt = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            con = db.getConnection();
            stmt = con.createStatement();

            pst = con.prepareStatement(
                    SELECT_LECTORES);
            rs = pst.executeQuery();
            while (rs.next()) {
                Lector l = new Lector(rs.getString("user")
                        , rs.getString("password")
                        , rs.getString("mail")
                        , rs.getString("nombre")
                        , rs.getDate("fechaNacimiento").toLocalDate());

                l.setId(rs.getLong("l.id"));
                lectores.add(l);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            db.cerrarResultSet(rs);
            db.cerrarStatement(stmt);
            db.cerrarConexion(con);
        }

        return lectores;
    }
}
