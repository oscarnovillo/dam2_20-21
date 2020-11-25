package EE.servlets;

import dao.modelo.Producto;
import io.vavr.control.Either;
import servicios.SvProductos;
import utils.Constantes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ServletProductos", urlPatterns = {"/productos"})
public class ServletProductos extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //SELECT

        String op = request.getParameter("op");

        switch (op) {
            case "1":
                break;
            case "3":
                break;
        }


        verProductos(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //INSERTAR
        insertarProducto((Producto) request.getAttribute(Constantes.PARAM_PRODUCTO));
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //update
        Producto p = (Producto) req.getAttribute(Constantes.PARAM_PRODUCTO);


        // ir a BD a Actualizar, llamadno a Servicios que llama al DAO.

        req.setAttribute(Constantes.PARAM_RESPUESTA, p);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //update
        Producto p = (Producto) req.getAttribute(Constantes.PARAM_PRODUCTO);


        // ir a BD a Actualizar, llamadno a Servicios que llama al DAO.

        req.setAttribute(Constantes.PARAM_RESPUESTA, p);

    }

    private int insertarProducto(Producto p) {
        return 0;
    }

    protected void verProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SvProductos svProductos = new SvProductos();
        String paginaDestino;
        Either<String, List<Producto>> productos = svProductos.getTodosProductos();
        if (productos.isRight()) {
            request.setAttribute(Constantes.PARAM_RESPUESTA, productos.get());
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute(Constantes.PARAM_ERROR, productos.getLeft());
        }

    }
}
