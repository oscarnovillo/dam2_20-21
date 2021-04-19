package EE.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletLector"
        , urlPatterns = {"/miLector"})
public class ServletLector extends javax.servlet.http.HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        response.getWriter().println("<html><body>HOLA LECTOR</body></html>");

    }
}
