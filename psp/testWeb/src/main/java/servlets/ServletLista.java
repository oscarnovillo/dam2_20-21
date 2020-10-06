package servlets;

import servicios.Validador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletLista",urlPatterns = {"/lista"})
public class ServletLista extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        hacerAlgo(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        hacerAlgo(request, response);
    }

    private void hacerAlgo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Validador v = new Validador();

        var error = v.validarParametros(request.getParameter("jjj"));

        if (error==null) {
            request.setAttribute("test", "probando");
            request.setAttribute("numList", List.of(1, 2, 3, 4, 5, 6, 7));
            request.setAttribute("jjj", request.getParameter("jjj"));
            request.setAttribute("cabeceras", request.getParameterValues("cabecera"));
            request.setAttribute("veces",3);

            request.getRequestDispatcher("jsp/jstl.jsp").forward(request, response);
        }
        else
        {
            request.setAttribute("error",error);
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
    }
}
