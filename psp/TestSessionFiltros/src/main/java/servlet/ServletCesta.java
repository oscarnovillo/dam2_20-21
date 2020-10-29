package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Cesta",urlPatterns = {"/cesta"})
public class ServletCesta extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            // ha hecho login
            var operador = (String)request.getSession().getAttribute("op");


            switch(operador)
            {
                case "add":

                    break;

                case "limpiar":

                    break;

            }

            var cielito = (String)request.getSession().getAttribute("cielito");

            if (cielito !=null )
                response.getWriter().println(cielito+"desde cesta");
            else
                response.getWriter().println("error");



    }
}
