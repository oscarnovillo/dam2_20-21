package tutorial;

import dao.DBConnection;
import lombok.extern.log4j.Log4j2;
import utils.ConstantesDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


@Log4j2
public class PrimerDelete {


    public static void main(String[] args) {


        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            log.info("conectando");
            con = db.getConnection();
            stmt = con.prepareStatement
                    (ConstantesDB.DELETE_TABLE_FECHAS_WHERE_ID);
            stmt.setInt(1,
                    14);

            int numeroFilas = stmt.executeUpdate();

            System.out.println("numero de filas afecto " + numeroFilas);
        } catch (SQLIntegrityConstraintViolationException e) {
            log.info("INTE!" + e.getMessage());
        } catch (SQLException e) {

            if (e.getMessage().contains("foreign key")) {
                System.out.println(e.getErrorCode());
            }
            log.info("SQL" + e.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            db.cerrarStatement(stmt);
            db.cerrarConexion(con);
        }
    }
}
