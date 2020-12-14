package gui;

import dao.modelo.Espia;
import dao.utils.HibernateUtilsSingleton;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLIntegrityConstraintViolationException;

@Log4j2
public class MainInsEspias {

    public static void main(String[] args) {

        Espia e = new Espia();

        e.setNombre("jj");
        e.setRaza("jj");
        System.out.println(e);

        try (Session session = HibernateUtilsSingleton.getInstance().getSession()) {

            session.save(e);

        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        System.out.println(e);

        e.setNombre("actualizado");
        e.setId(2);
        try (Session session = HibernateUtilsSingleton.getInstance().getSession()) {
            session.beginTransaction();
            session.update(e);
            session.getTransaction().commit();

        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        System.out.println(e);
    e.setId(1);
        try (Session session = HibernateUtilsSingleton.getInstance().getSession()) {
            session.beginTransaction();
            session.delete(e);
            session.getTransaction().commit();

        }
        catch (ConstraintViolationException ex)
        {
            if (ex.getCause() instanceof SQLIntegrityConstraintViolationException)
                System.out.println("espia tiene datos relacionados");
            else
                System.out.println(ex.getMessage());
        }
        catch (Exception ex) {

            log.error(ex.getMessage(), ex);
        }

    }
}
