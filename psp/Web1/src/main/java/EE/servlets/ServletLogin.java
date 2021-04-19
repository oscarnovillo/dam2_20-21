package EE.servlets;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "ServletLogin"
        ,urlPatterns = {"/login"})
public class ServletLogin extends javax.servlet.http.HttpServlet{


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        Random r = new Random();
        String user = "admin";

        if (r.nextInt() % 3 == 0)
            user ="lector";
        else if (r.nextInt() % 3 == 1)
            user ="super";


        request.getSession().setAttribute("user",user);

        response.getWriter().println("<html><body>HOLA "+user+"</body></html>");
    }
}
