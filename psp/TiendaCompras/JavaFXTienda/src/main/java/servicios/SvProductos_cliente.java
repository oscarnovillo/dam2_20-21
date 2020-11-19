package servicios;

import dao.DaoProducto_cliente;
import io.vavr.control.Either;

import java.util.List;

public class SvProductos_cliente {
    public Either<String, List<String>> getTodosProductos() {
        DaoProducto_cliente daoProducto_cliente = new DaoProducto_cliente();
        return daoProducto_cliente.getTodosProductos();
    }

    public Either<String, List<String>> addCesta(List list_productos) {
        DaoProducto_cliente daoProducto_cliente = new DaoProducto_cliente();
        return daoProducto_cliente.addCesta(list_productos);
    }

    public Either<String, List<String>> verCesta() {
        DaoProducto_cliente daoProducto_cliente = new DaoProducto_cliente();
        return daoProducto_cliente.verCesta();
    }

    public String buyCesta() {
        DaoProducto_cliente daoProducto_cliente = new DaoProducto_cliente();
        return daoProducto_cliente.buyCesta();
    }

    public String clearCesta() {
        DaoProducto_cliente daoProducto_cliente = new DaoProducto_cliente();
        return daoProducto_cliente.clearCesta();
    }
}
