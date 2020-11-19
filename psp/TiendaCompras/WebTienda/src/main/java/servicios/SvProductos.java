package servicios;

import dao.DaoProducto;
import dao.modelo.Producto;
import io.vavr.control.Either;

import java.util.List;

public class SvProductos {

    public Either<String,List<Producto>> getTodosProductos() {
        DaoProducto daoProducto = new DaoProducto();
        return daoProducto.getTodosProductos();
    }
}
