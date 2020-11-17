package dao.utils;

import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.*;


@Log4j2
public class DBConnection {

    public DBConnection() {

    }

    public Connection getConnection() throws Exception {

        Connection connection = null;

        // solo hace falta en web.
        Class.forName("org.h2.Driver");

//        connection = DriverManager.getConnection(
//                "jdbc:mysql://dam2.mysql.iesquevedo.es:3335/profesor_periodico",
//                "root",
//                "quevedo2020");

        connection = DBConnectionPool.getInstance().getConnection();
        return connection;
    }

    public DataSource getDataSource()  {
        //        MysqlDataSource mysql = new MysqlConnectionPoolDataSource();
//        mysql.setUrl(Configuration.getInstance().getUrlDB());
//        mysql.setUser(Configuration.getInstance().getUserDB());
//        mysql.setPassword(Configuration.getInstance().getPassDB());
        return DBConnectionPool.getInstance().getDataSource();
    }

    public void cerrarPool()
    {
        DBConnectionPool.getInstance().cerrarPool();
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
            ex.printStackTrace();
        }
    }


}
