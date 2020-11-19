package EE.servlets;

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

        String respuesta = "";
        int code=HttpServletResponse.SC_OK;
        List<String> productosCesta = (List<String>) request.getSession().getAttribute("listaCesta");
        if ( productosCesta == null) {
            productosCesta = new ArrayList<>();
        }

        var opcion = request.getParameter("opcion");
        if (opcion != null) {
            switch (opcion) {
                case "add":
                    var itemsCesta = request.getParameterValues("producto");
                    if (itemsCesta != null) {
                        productosCesta.addAll(Arrays.asList(request.getParameterValues("producto")));
                    }
               case "ver":
                    if (!productosCesta.isEmpty()) {
                        respuesta = productosCesta.stream().collect(Collectors.joining(","));
                    }
                    else{
                        respuesta = "No hay productos en la cesta";
                        code = HttpServletResponse.SC_SERVICE_UNAVAILABLE;
                    }
                    break;
                case "buy":
                    if (!productosCesta.isEmpty()) {
                        productosCesta.clear();
                        respuesta = "Productos comprados correctamente. \nGracias por comprar en nuestra tienda";
                    } else {
                        code = HttpServletResponse.SC_SERVICE_UNAVAILABLE;
                        respuesta = "La lista esta vacia no puedes comprar";
                    }
                    break;
                case "clear":
                    if (!productosCesta.isEmpty()) {
                        productosCesta.clear();
                    }
                    respuesta = "Has vaciado la cesta correctamente";
                    break;
            }
        } else {
            respuesta = "Parametros incorrectos";
            code = HttpServletResponse.SC_BAD_REQUEST;
        }

        request.getSession().setAttribute("listaCesta",productosCesta);
        response.setStatus(code);
        response.getWriter().println(respuesta);

    }
}
