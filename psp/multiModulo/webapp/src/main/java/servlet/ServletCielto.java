package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Cielito",urlPatterns = {"/cielito"})
public class ServletCielto extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var cielito = (String)request.getSession().getAttribute("cielito");

        if (cielito !=null )
            response.getWriter().println(cielito);
        else
            response.getWriter().println("error");
    }
}
