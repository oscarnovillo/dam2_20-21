package EE.filtro;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "FilterLector",
        urlPatterns = {"/miTodos"})
public class FiltroTodos implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;


        if (request.getSession().getAttribute("user") != null) {
            chain.doFilter(req, response);
        } else {
            response.getWriter().println("<html><body>NO ERES USUARIO</body></html>");
        }
    }
}
