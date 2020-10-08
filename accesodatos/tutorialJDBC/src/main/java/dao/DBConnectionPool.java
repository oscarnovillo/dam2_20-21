package dao;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author oscar
 */
public class DBConnectionPool {

    private static DBConnectionPool dbconection = null;

    private DataSource hirakiDatasource = null;

    private DBConnectionPool() {
        hirakiDatasource = getDataSourceHikari();
    }

    public static DBConnectionPool getInstance() {
        if (dbconection == null) {
            dbconection = new DBConnectionPool();
        }

        return dbconection;
    }

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection;

        connection = hirakiDatasource.getConnection();

        return connection;
    }

    public DataSource getDataSourceFromServer() throws NamingException {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jdbc/db4free");
        return ds;

    }

    private DataSource getDataSourceHikari() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://dam2.mysql.iesquevedo.es:3335/netflisssss?serverTimeZone=GMT+2");
        config.setUsername("root");
        config.setPassword("root");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setMaximumPoolSize(50);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource datasource = new HikariDataSource(config);

        return datasource;
    }

    public DataSource getDataSource() {
        // Creates a new instance of DriverManagerDataSource and sets
        // the required parameters such as the Jdbc Driver class,
//        MysqlDataSource mysql = new MysqlConnectionPoolDataSource();
//        mysql.setUrl(Configuration.getInstance().getUrlDB());
//        mysql.setUser(Configuration.getInstance().getUserDB());
//        mysql.setPassword(Configuration.getInstance().getPassDB());

        // Jdbc URL, database user name and password.
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName(Configuration.getInstance().getDriverDB());
//        dataSource.setUrl(Configuration.getInstance().getUrlDB());
//        dataSource.setUsername(Configuration.getInstance().getUserDB());
//        dataSource.setPassword(Configuration.getInstance().getPassDB());

        //return mysql;
        return hirakiDatasource;
    }

    public void cerrarConexion(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

