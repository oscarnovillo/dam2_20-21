package dao;

import dao.modelo.Arma;
import dao.utils.HibernateUtils;
import dao.utils.HibernateUtilsSingleton;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Log4j2
public class DaoArmas {


    public Either<String, Arma> getById(int id) {
        Either<String, Arma> resultado = null;
        try (Session session = HibernateUtilsSingleton.getInstance().getSession()) {

            Arma a = session.get(Arma.class, id);
            if (a == null)
                resultado = Either.left("arma no encontrada");
            else resultado = Either.right(a);


        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(e.getMessage());
        }

        return resultado;
    }

    public Either<String, Arma> getByNombre(String nombre) {
        Either<String, Arma> resultado = null;
        try (Session session = HibernateUtilsSingleton.getInstance().getSession()) {

            Arma a = session.bySimpleNaturalId(Arma.class).load(nombre);

//            session.byNaturalId(Arma.class)
//                    .using("nombre","iojlo")
//                   .load();
            if (a == null)
                resultado = Either.left("arma no encontrada");
            else resultado = Either.right(a);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(e.getMessage());
        }
        return resultado;
    }

    public Either<String, List<Arma>> getAll() {
        Either<String,  List<Arma>> resultado = null;
        try (Session session = HibernateUtilsSingleton.getInstance().getSession()) {

            List<Arma> a = session.createQuery("select Arma from Arma",Arma.class).getResultList();
            resultado = Either.right(a);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(e.getMessage());
        }
        return resultado;
    }

    public Either<String, Arma> addArma(Arma a) {
        Either<String,  Arma> resultado = null;
        try (Session session = HibernateUtilsSingleton.getInstance().getSession()) {
            session.beginTransaction();
            session.save(a);
            session.getTransaction().commit();

            resultado = Either.right(a);

        }catch (ConstraintViolationException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException)
                resultado = Either.left("arma ya existe");
            else
                resultado = Either.left(e.getMessage());
            log.error(e.getMessage(), e);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(e.getMessage());
        }
        return resultado;
    }

    public Either<String, Arma> updateArma(Arma a) {
        Either<String,  Arma> resultado = null;
        try (Session session = HibernateUtils.getSession()) {

            session.beginTransaction();
            session.update(a);
            session.getTransaction().commit();
            resultado = Either.right(a);

        }catch (ConstraintViolationException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException)
                resultado = Either.left("arma ya existe");
            else
                resultado = Either.left(e.getMessage());
            log.error(e.getMessage(), e);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(e.getMessage());
        }
        return resultado;
    }

    public Either<String, Arma> delArma(Arma a) {
        Either<String,  Arma> resultado = null;
        try (Session session = HibernateUtils.getSession()) {

            session.beginTransaction();
            session.delete(a);
            session.getTransaction().commit();
            resultado = Either.right(a);

        }catch (PersistenceException e) {
//            if (e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException)
                resultado = Either.left("arma tiene datos asociados");
//            else
//                resultado = Either.left(e.getMessage());
            log.error(e.getMessage(), e);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(e.getMessage());
        }
        return resultado;
    }



}
