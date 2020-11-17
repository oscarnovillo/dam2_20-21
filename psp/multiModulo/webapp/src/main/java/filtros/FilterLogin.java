package filtros;

import servlet.Login;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "FilterLogin",urlPatterns = {"/cesta","/productos","/privado/*"})
public class FilterLogin implements Filter {


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
// codigo para comprobar session usuario

        Login g = (Login)((HttpServletRequest)req).getSession().getAttribute("usuario");
        if (Math.random()>0.5)
            chain.doFilter(req, resp);
        else
            req.getRequestDispatcher("/errorFiltro.html").forward(req,resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
