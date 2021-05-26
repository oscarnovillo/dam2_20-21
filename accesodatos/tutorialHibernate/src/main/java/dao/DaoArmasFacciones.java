package dao;

import dao.modelo.ArmasFacciones;
import dao.utils.HibernateUtilsSingleton;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;

@Log4j2
public class DaoArmasFacciones {

    public Either<String, ArmasFacciones> addArmaFaccion(ArmasFacciones a) {
        Either<String, ArmasFacciones> resultado = null;
        Session session = HibernateUtilsSingleton.getInstance().getSession();
        try
        {
            session.save(a);
            resultado = Either.right(a);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(e.getMessage());
        }
        finally{
            session.close();
        }
        return resultado;
    }
}
