package EE.servlets;

import dao.modelo.Producto;
import io.vavr.control.Either;
import utils.Constantes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ServletCesta", urlPatterns = {"/cesta"})
public class ServletCesta extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cesta(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cesta(request, response);
    }

    protected void cesta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int code=HttpServletResponse.SC_OK;
        List<Producto> productosCesta = (List<Producto>) request.getSession().getAttribute("listaCesta");
        if ( productosCesta == null) {
            productosCesta = new ArrayList<>();
        }

        var opcion = request.getParameter("opcion");
        if (opcion != null) {
            switch (opcion) {
                case "add":
                    var itemsCesta = (List<Producto>)request.getAttribute(Constantes.PARAM_PRODUCTOS);
                    if (itemsCesta != null) {
                        productosCesta.addAll(itemsCesta);
                    }
               case "ver":
                    if (!productosCesta.isEmpty()) {
                        request.setAttribute(Constantes.PARAM_RESPUESTA,productosCesta);
                    }
                    else{
                        request.setAttribute(Constantes.PARAM_RESPUESTA,"No hay productos en la cesta");
                        code = HttpServletResponse.SC_SERVICE_UNAVAILABLE;
                    }
                    break;
                case "buy":
                    if (!productosCesta.isEmpty()) {
                        productosCesta.clear();
                        request.setAttribute(Constantes.PARAM_RESPUESTA,"Productos comprados correctamente. \nGracias por comprar en nuestra tienda");
                    } else {
                        code = HttpServletResponse.SC_SERVICE_UNAVAILABLE;
                        request.setAttribute(Constantes.PARAM_RESPUESTA,"La lista esta vacia no puedes comprar");
                    }
                    break;
                case "clear":
                    if (!productosCesta.isEmpty()) {
                        productosCesta.clear();
                    }
                    request.setAttribute(Constantes.PARAM_RESPUESTA,"Has vaciado la cesta correctamente");
                    break;
            }
        } else {
            request.setAttribute(Constantes.PARAM_ERROR,"Parametros incorrectos");
            code = HttpServletResponse.SC_BAD_REQUEST;
        }

        request.getSession().setAttribute("listaCesta",productosCesta);
        response.setStatus(code);


    }
}
