package modelo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletAdmin"
        , urlPatterns = {"/miAdmin"})
public class ServletAdmin extends javax.servlet.http.HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.getWriter().println("<html><body>HOLA ADMIN</body></html>");

    }
}
