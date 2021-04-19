package tutorial;

import dao.DBConnection;
import utils.ConstantesDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Logger;

public class PrimeraTransaccion {

    public static final String DELETE_TABLE_FECHAS_WHERE_ID =
            "delete from table_fechas2 where id = ?";
    public static final String DELETE_TABLE_FK_FECHAS_WHERE_ID =
            "delete from table_fk where id_fechas = ?";


    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        var hayError = false;

        try {
            con = db.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement
                    (DELETE_TABLE_FK_FECHAS_WHERE_ID);
            stmt.setInt(1,
                    14);
            int numeroFilas = stmt.executeUpdate();


            stmt = con.prepareStatement
                    (DELETE_TABLE_FECHAS_WHERE_ID);
            stmt.setInt(1,
                    14);
            numeroFilas = stmt.executeUpdate();

            System.out.println(ConstantesDB.NUMERO_DE_FILAS_AFECTO + numeroFilas);

            con.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            Logger.getLogger("Main").info("INTE!" + e.getMessage());
            db.rollbackCon(con);
        } catch (SQLException e) {
            db.rollbackCon(con);
            if (e.getMessage().contains("foreign key")) {
                System.out.println(e.getErrorCode());
            }
            Logger.getLogger("Main").info("SQL" + e.getMessage());
        } catch (Exception e) {
            Logger.getLogger("Main").info(e.getMessage());
            db.rollbackCon(con);
        } finally {

            db.cerrarStatement(stmt);
            db.cerrarConexion(con);
        }
    }
}
