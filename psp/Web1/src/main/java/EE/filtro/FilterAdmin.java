package EE.filtro;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "FilterAdmin",
        urlPatterns = {"/miAdmin"})
public class FilterAdmin implements Filter {


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        // antes del servlet

        HttpServletRequest request = (HttpServletRequest) req;

        if (request.getSession().getAttribute("user") != null
                &&  ( request.getSession().getAttribute("user").equals("admin")
        ||request.getSession().getAttribute("user").equals("super")
        )) {
            chain.doFilter(req, resp);
        } else {
            resp.getWriter().println("<html><body>NO ERES ADMIN</body></html>");
        }


        // despues del servlet
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
