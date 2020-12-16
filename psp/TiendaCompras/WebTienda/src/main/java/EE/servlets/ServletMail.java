package EE.servlets;

import utils.PasswordHash;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletMail",urlPatterns = {"/mail"})
public class ServletMail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servicios.MandarMail mail = new servicios.MandarMail();

        try {
            mail.generateAndSendEmail("oscar.novillo@gmail.com", "<html>generado <a href=\"www.marca.com\" >marca</a> " + Utils.randomBytes() + "</html>"
                    , "mail de prueba");
            response.getWriter().println("correo enviado");
        } catch (Exception e) {
            response.getWriter().println(e.getMessage());

        }


    }
}
