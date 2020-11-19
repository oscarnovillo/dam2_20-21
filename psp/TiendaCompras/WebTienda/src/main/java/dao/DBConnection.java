package dao;

import config.ConfigurationSingleton;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DBConnection {

    public Connection getConnection() throws SQLException, ClassNotFoundException {

        Connection connection;

        // solo hace falta en web.
        Class.forName("com.mysql.cj.jdbc.Driver");


        Class.forName(ConfigurationSingleton.getInstance().getDriver());

        connection = DriverManager.getConnection(
                ConfigurationSingleton.getInstance().getRuta(),
                ConfigurationSingleton.getInstance().getUser(),
                ConfigurationSingleton.getInstance().getPassword());

        return connection;
    }

    public void cerrarConexion(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException ex) {
            log.error("no se ha podido cerrar conexion", ex);
        }
    }

    public void cerrarStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            log.error("", ex);
        }
    }

    public void cerrarResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            log.error("", ex);
        }
    }

    public void rollbackCon(Connection con) {
        try {
            if (con != null)
                con.rollback();
        } catch (SQLException ex) {
            log.error("", ex);
        }
    }


}
