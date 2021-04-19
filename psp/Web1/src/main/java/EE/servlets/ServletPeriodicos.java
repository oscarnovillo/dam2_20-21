package EE.servlets;

import modelo.Periodico;
import servicios.ServiciosPeriodicos;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletPeriodicos", urlPatterns = {"/periodicos"})
public class ServletPeriodicos extends HttpServlet {

    @Inject
    private ServiciosPeriodicos serviciosPeriodicos;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        hacerAlgo(request, response);
    }

    private void hacerAlgo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        List<Periodico> periodicos = serviciosPeriodicos.getPeriodicos();
        request.setAttribute("periodicos",periodicos);
        request.getRequestDispatcher("periodicos.jsp").forward(request,response);
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        hacerAlgo(request, response);
    }


}
