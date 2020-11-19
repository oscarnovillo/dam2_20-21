package dao;

import dao.modelo.Producto;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoProducto {

    private static final String QUERY_GET_PRODUCTOS = "select * from productos";

    public Either<String,List<Producto>> getTodosProductos() {
        Either<String,List<Producto>> resultado;
        List<Producto> listaProductos = new ArrayList<>();
        Connection con = null;
        DBConnection db = new DBConnection();
        Statement stmt = null;
        PreparedStatement pst;
        ResultSet rs = null;
        try {
            con = db.getConnection();
            stmt = con.createStatement();
            pst = con.prepareStatement(QUERY_GET_PRODUCTOS);
            rs = pst.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto(rs.getString("nombre"), rs.getDouble("precio"));
                producto.setId_producto(rs.getLong("id_producto"));
                listaProductos.add(producto);
            }

            resultado = Either.right(listaProductos);

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            resultado = Either.left("error en la bd");
        } finally {
            db.cerrarResultSet(rs);
            db.cerrarStatement(stmt);
            db.cerrarConexion(con);
        }
        return resultado;
    }
}
