package EE.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletTodos"
        , urlPatterns = {"/miTodos"})
public class ServletTodos extends javax.servlet.http.HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            response.getWriter().println("<html><body>HOLA SEAS QUIEN SEAS</body></html>");
        } else {
            response.getWriter().println("<html><body>NO ERES USUARIO</body></html>");
        }

    }
}
