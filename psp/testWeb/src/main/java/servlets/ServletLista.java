package servlets;

import dto.Filtro;
import dto.FiltroException;
import servicios.ServiciosAlumnos;
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

        String error = null;
        Filtro filtro = null;

        try {
            filtro = v.validarFiltro(request.getParameter("jjj"),
                    request.getParameterValues("cabecera"),
                    request.getParameter("columnas"),
                    request.getParameter("inferior"));

            ServiciosAlumnos s = new ServiciosAlumnos();



            request.setAttribute("color", "#EEEEEE");
            request.setAttribute("numList", s.getAlumnos(filtro));
            request.setAttribute("jjj", filtro.getJjj());
            request.setAttribute("cabeceras", filtro.getCabeceras());
            request.setAttribute("veces",filtro.getColumnas());

            request.getRequestDispatcher("jsp/jstl.jsp").forward(request, response);

        } catch (FiltroException e) {
            request.setAttribute("errorcito",e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }

    }
}
