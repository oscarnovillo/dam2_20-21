package EE.filtros;

import EE.errores.ApiError;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.time.LocalDateTime;


@Provider
@Filtered
//@PreMatching
public class ServerFilterJAX  implements ContainerRequestFilter {


  @Context
  private HttpServletRequest httpServletRequest;

  @Override
  public void filter(ContainerRequestContext containerRequestContext) throws IOException {
    if (httpServletRequest.getSession().getAttribute("kk")==null) {
      //httpServletRequest.getSession().setAttribute("kk",1);
      containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
              .entity(ApiError.builder().message("error de filtro").fecha(LocalDateTime.now()).build())
              .type(MediaType.APPLICATION_JSON_TYPE).build());
    }
  }
}
