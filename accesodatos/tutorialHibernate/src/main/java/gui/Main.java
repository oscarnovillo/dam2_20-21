package gui;

import dao.DaoArmas;
import dao.DaoArmasFacciones;
import dao.modelo.Arma;
import dao.modelo.ArmasFacciones;
import dao.modelo.Faccion;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;

import java.time.LocalDate;

@Log4j2
public class Main {




    public static void main(String[] args) {
        DaoArmas dao = new DaoArmas();
        dao.getById(7)
                .peek(System.out::println)
                .peekLeft(System.out::println);

        log.info("asdfasd");

//
//        dao.getByNombre("sdf")
//                .peek(System.out::println)
//                .peekLeft(System.out::println);
//
//        dao.getAll()
//                .peek(System.out::println)
//                .peekLeft(System.out::println);
//
//        Arma a = new Arma();
//        a.setPrecio(20.0);
//        a.setNombre("test");
//
//        dao.addArma(a).peek(System.out::println).peekLeft(System.out::println);
//
//        System.out.println(a);
//
//        Faccion f = new Faccion();
//        f.setNombre("no lo se");
//        f.setContacto("contacto nuevo");
//        f.setNumeroSistemasControlados(2);
//        f.setUltimaCompra(LocalDate.now());
//        f.setPlanetaBase("jjj");
//
//        Arma b = new Arma();
//        b.setId(7);
//        b.setNombre(a.getNombre());
//        b.setPrecio(30.90);
//        dao.updateArma(b).peek(System.out::println)
//                .peekLeft(System.out::println);
//
//        ArmasFacciones af = new ArmasFacciones();
//        af.setArma(b);
//        af.setFaccion(f);
//
//
//        DaoArmasFacciones daf = new DaoArmasFacciones();
//        daf.addArmaFaccion(af)
//                .peek(System.out::println)
//                .peekLeft(System.out::println);

//        a = new Arma();
//        a.setId(7);
//        a.setPrecio(20.0);
//        a.setNombre("test");
//
//        dao.delArma(a)
//                .peek(System.out::println)
//                .peekLeft(System.out::println);


    }
}
