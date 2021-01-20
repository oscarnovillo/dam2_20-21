package EE.errores;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.LocalDate;


@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {

  public Response toResponse(CustomException exception) {
    ApiError apiError = new ApiError(exception.getMessage(), LocalDate.now());
    return Response.status(exception.getCodigo()).entity(apiError)
        .type(MediaType.APPLICATION_JSON_TYPE).build();
  }

}
