package servicios;

import dao.DaoProducto_cliente;
import dao.modelo.Producto;
import io.vavr.control.Either;

import java.util.List;

public class SvProductos_cliente {
    public Either<String, List<Producto>> getTodosProductos() {
        DaoProducto_cliente daoProducto_cliente = new DaoProducto_cliente();
        return daoProducto_cliente.getTodosProductos();
    }

    public Either<String, List<Producto>> addCesta(List<Producto> list_productos) {
        DaoProducto_cliente daoProducto_cliente = new DaoProducto_cliente();
        return daoProducto_cliente.addCesta(list_productos);
    }

    public Either<String, Producto> editarProducto(Producto producto) {
        DaoProducto_cliente daoProducto_cliente = new DaoProducto_cliente();
        return daoProducto_cliente.editarProducto(producto);
    }


    public Either<String, List<Producto>> verCesta() {
        DaoProducto_cliente daoProducto_cliente = new DaoProducto_cliente();
        return daoProducto_cliente.verCesta();
    }

    public Either<String,String> buyCesta() {
        DaoProducto_cliente daoProducto_cliente = new DaoProducto_cliente();
        return daoProducto_cliente.buyCesta();
    }

    public Either<String,String>  clearCesta() {
        DaoProducto_cliente daoProducto_cliente = new DaoProducto_cliente();
        return daoProducto_cliente.clearCesta();
    }
}
