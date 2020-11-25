package EE.filters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.modelo.Producto;
import dao.modelo.Usuario;
import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import utils.Constantes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@WebFilter(filterName = "FilterJson", urlPatterns = {"/productos", "/cesta", "/bienvenida"})
public class FilterJson implements Filter {


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        AtomicBoolean seguir = new AtomicBoolean(true);

        Gson json = new Gson();
        Optional.ofNullable(req.getParameter(Constantes.PARAM_PRODUCTO))
                .ifPresent(o -> {
                    Try.of(() -> json.fromJson(o, Producto.class))
                            .onSuccess(producto ->
                                    req.setAttribute(Constantes.PARAM_PRODUCTO, producto))
                            .onFailure(throwable -> {
                                log.error(throwable.getMessage(), throwable);
                                ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                seguir.set(false);
                            });

                });

        Optional.ofNullable(req.getParameter("usuario"))
                .ifPresent(o -> {
                    Try.of(() -> json.fromJson(o, Usuario.class))
                            .onSuccess(producto ->
                                    req.setAttribute("usuario", producto))
                            .onFailure(throwable -> {
                                log.error(throwable.getMessage(), throwable);
                                ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                seguir.set(false);
                            });

                });
        Optional.ofNullable(req.getParameter(Constantes.PARAM_PRODUCTOS))
                .ifPresent(sProductos -> {
                    Try.of(() -> json.fromJson(sProductos,
                            new TypeToken<List<Producto>>() {}.getType()))
                            .onSuccess(producto -> req.setAttribute(Constantes.PARAM_PRODUCTOS, producto))
                            .onFailure(throwable -> {
                                log.error(throwable.getMessage(), throwable);
                                ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                seguir.set(false);
                            });

                });

        if (seguir.get()) {
            chain.doFilter(req, resp);

            AtomicReference<String> respuesta = new AtomicReference<>();
            Optional.ofNullable(
                    req.getAttribute(Constantes.PARAM_RESPUESTA))
                    .ifPresentOrElse(
                            o -> respuesta.set(json.toJson(o))
                            , () -> {
                                respuesta.set(
                                        Optional.ofNullable(
                                                req.getAttribute(Constantes.PARAM_ERROR))
                                                .map(o -> (String)o)
                                                .orElseGet(() -> null));
                                ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            });
            resp.getWriter().print(respuesta.get());
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
