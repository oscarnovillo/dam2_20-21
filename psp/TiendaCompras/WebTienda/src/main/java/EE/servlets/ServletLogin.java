package EE.servlets;

import dao.modelo.Usuario;
import io.vavr.control.Either;
import servicios.SvUsuarios;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletLogin", urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(request, response);
    }

    @Inject
    public SvUsuarios svUsuarios;

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // SvUsuarios svUsuarios = new SvUsuarios();


        svUsuarios.usuarioLogin(new Usuario(request.getParameter("user"),
                request.getParameter("password")))
                .peek(usuario -> {
                    request.getSession().setAttribute("usuarioLogin", usuario);
                    request.getSession().setAttribute("nombreUsuario", usuario.getUsername());
                    response.setStatus(HttpServletResponse.SC_OK);
                })
                .peekLeft(s -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED));


        // no devuelve nada, con el code se controla todo
    }
}
