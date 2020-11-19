package EE.servlets;

import dao.modelo.Producto;
import io.vavr.control.Either;
import servicios.SvProductos;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        verProductos(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        verProductos(request, response);
    }

    protected void verProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SvProductos svProductos = new SvProductos();
        String paginaDestino;
        Either<String,List<Producto>> productos = svProductos.getTodosProductos();
        if (productos.isRight()) {
            String productosResponse = productos.get().stream()
                    .map(Producto::getNombreProducto).collect(Collectors.joining(","));
            response.getWriter().println(productosResponse);
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(productos.getLeft());
        }

    }
}
