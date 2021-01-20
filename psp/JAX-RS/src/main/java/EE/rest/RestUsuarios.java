package EE.rest;


import EE.errores.ApiError;
import dao.modelo.Usuario;
import servicios.ServiciosUsuarios;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuarios {

    @Inject
    private ServiciosUsuarios su;

    //para todos los metodos
    //@Context HttpServletRequest request;

    @GET
    @Path("/uno")
    public Response getUsuario(@QueryParam("id") String id,
                               @Context HttpServletRequest request) {

        AtomicReference<Response> r = new AtomicReference();
        su.dameUno(id)
                .peek(usuario -> r.set(Response.ok(usuario).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.NOT_FOUND)
                        .entity(apiError)
                        .build()));

        return r.get();

    }

    @GET
    @Path("/{id}")
    public Response getUnUsuario(@PathParam("id") String id,
                                @HeaderParam("kk") String head) {
        AtomicReference<Response> r = new AtomicReference();
        su.dameUno(id)
                .peek(usuario -> r.set(Response.ok(usuario).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiError("error not found", LocalDate.now()))
                        .build()));

        return r.get();
    }

    @GET
    public List<Usuario> getAllUsuario() {
        return su.dameTodos().get();
    }

    @POST
    public Usuario addUsuario(Usuario user) {
        return su.addUser(user).get();
    }
}
