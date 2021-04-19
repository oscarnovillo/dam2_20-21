package EE.filtro;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "FilterLector",
        urlPatterns = {"/miLector"})
public class FilterLector implements Filter {


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        // antes del servlet

        HttpServletRequest request = (HttpServletRequest) req;

        if (request.getSession().getAttribute("user") != null
                && (request.getSession().getAttribute("user").equals("lector")
                || request.getSession().getAttribute("user").equals("super")
        )) {
            chain.doFilter(req, resp);
        } else {
            resp.getWriter().println("<html><body>NO ERES LECTOR</body></html>");
        }


        // despues del servlet
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
