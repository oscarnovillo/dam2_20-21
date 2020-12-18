package gui;

import dao.modelo.Arma;
import dao.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class MainQuerys {

    public static void main(String[] args) {
        Session session = HibernateUtils.getSession();

        Query<Arma> q = session.createQuery("from Arma ");

//        q.stream().forEach(System.out::println);
//
//
//        List<Arma> armas = q.getResultList();
//
//        armas.stream().forEach(System.out::println);


        q = session.createQuery("select distinct(a) from Arma a " +
                "inner join a.armasFaccionesById as af " +
                "inner join af.faccion as f " +
                "where f.numeroSistemasControlados > 0 ",Arma.class);

        q.stream().forEach(System.out::println);


        System.out.println();
        q = session.createQuery("select distinct(af.arma) from ArmasFacciones af " +
                " inner join af.faccion f "+
                "where f.numeroSistemasControlados > 0 ",Arma.class);


        q.stream().forEach(o -> System.out.println(o.toStringTodo()));

        Query q1 = session.createQuery("select af.arma,af.faccion.contacto from ArmasFacciones af " +
                " inner join af.faccion f "+
                "where f.numeroSistemasControlados > :numero ")
                .setParameter("numero",0);


        q1.stream().forEach(o -> {
            Object[] resultado = (Object[])o;
            System.out.println(resultado[0]+" {"+resultado[1]+"}");


        });

        q1 = session.createQuery("select a.id,a.nombre from Arma a " +
                "left join a.armasFaccionesById as af inner join af.faccion as f " +
                "where f.numeroSistemasControlados > 0 ");

        q1.stream().forEach(o -> System.out.println(((Object[])o)[0]));

//
//        q = session.createQuery("select b from Batalla b " +
//                "inner join a.armasFaccionesById as af inner join af.faccion as f " +
//                "where f.numeroSistemasControlados > 0 ");

//        List<Teacher> teachers = q.getResultList();
//        teachers.forEach(teacher -> teacher.getSubjectsByIdteacher().forEach(subject -> subject.getName()));
//        session.close();
//
//        teachers.forEach(teacher -> teacher.getSubjectsByIdteacher().forEach(System.out::println));
//
//        session = HibernateUtils.getSession();
//         q = session.createQuery("select t from Teacher as t inner join t.subjectsByIdteacher as s " +
//                " where s.name = :name " );
//        q.setParameter("name","PSP");
//        Teacher teacher = (Teacher)q.getSingleResult();
//            System.out.println(teacher);
//            teacher.getSubjectsByIdteacher().forEach(System.out::println);
//        session.close();
//
//        session = HibernateUtils.getSession();
//         q = session.createQuery("select s.name from Subject as s   " +
//                " where " +
//                " s.teacherByIdteacher.idteacher = :idTeacher " );
//        q.setParameter("idTeacher",17);
//        q.stream().forEach(System.out::println);
//        session.close();

//        session = HibernateUtils.getSession();
//        q = session.createQuery("select s.name from Subject as s  " +
//                " where " +
//                " s.teacher.start_date > :fecha " );
//        q.setParameter("fecha", LocalDate.now().minus(1, ChronoUnit.YEARS));
//        q.stream().forEach(System.out::println);
//        session.close();
//
//        session = HibernateUtils.getSession();
//        q = session.createQuery("select t,count(s.name) from Subject as s inner join s.teacherByIdteacher as t " +
//                " where " +
//                " t.startDate < :fecha ");
//        q.setParameter("fecha", LocalDate.now().minus(1, ChronoUnit.YEARS));
//        q.stream().forEach(o -> {
//            Object[] result = (Object[])o;
//
//            Arrays.stream(result).forEach(System.out::println);
//
//        });
//        session.close();

//        session = HibernateUtils.getSession();
//        q = session.createNativeQuery("select count(s.name) from subject as s inner join teacher as t on s.idteacher = t.idteacher " +
//                " where " +
//                " t.start_date > :fecha " );
//        q.setParameter("fecha", LocalDate.now().minus(1, ChronoUnit.YEARS));
//        q.stream().forEach(System.out::println);
//        session.close();
//
//
//        session = HibernateUtils.getSession();
//        q = session.createQuery("select count(s.name) as count from Subject as s inner join s.teacher as t " +
//        " group by t " );
//        q.stream().forEach(o -> System.out.println(o));
        session.close();

    }

}


