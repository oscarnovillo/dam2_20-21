package EE.servlets;

import dao.DaoPeriodicos;
import modelo.Periodico;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "ServletBorrarPeriodico", urlPatterns = {"/delperiodicos"})
public class ServletBorrarPeriodico extends HttpServlet {

    @Inject
    DaoPeriodicos dao;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        hacerAlgo(request, response);
    }

    private void hacerAlgo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nombre = request.getParameter("nombre");


        Periodico p = new Periodico(nombre,"0");

        dao.delPeriodicos(p);
        List<Periodico> periodicos = dao.getPeriodicos();


        request.setAttribute("alert","periodico borrado");
        request.setAttribute("mostraralert",true);
        request.setAttribute("periodicos",periodicos);
        request.getRequestDispatcher("periodicos.jsp").forward(request,response);
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        hacerAlgo(request, response);
    }
}
