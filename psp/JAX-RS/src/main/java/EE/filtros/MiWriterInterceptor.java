package EE.filtros;

import dao.modelo.Usuario;
import dao.modelo.UsuarioGetDTO;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import javax.json.bind.Jsonb;

@Provider
@Writer
public class MiWriterInterceptor implements WriterInterceptor {

    @Inject
    private Jsonb json;

    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        Object entity = context.getEntity();
        if (entity instanceof UsuarioGetDTO) {
            UsuarioGetDTO u = (UsuarioGetDTO) context.getEntity();
            u.setName("secreto");
            context.setEntity(u);
//                .write((json.toJson(u)).getBytes());
        }
        context.proceed();
    }
}
