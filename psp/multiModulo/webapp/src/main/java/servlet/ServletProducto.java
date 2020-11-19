package servlet;

import servicios.ServiciosProductos;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletProducto",urlPatterns = {"/productos"})
public class ServletProducto extends HttpServlet {

    @Inject
    private ServiciosProductos sp;




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var usuario = (String)request.getSession().getAttribute("usuario");


//        jsonb.fromJson(request.getReader(),ServiciosProductos.class);
//        Type listOfMyClassObject = new TypeToken<ArrayList<MyClass>>() {}.getType();

        sp.test();

        if (usuario !=null )
        {
            // ha hecho login


        }
        else
            response.getWriter().println("error no has hecho login");
    }
}
