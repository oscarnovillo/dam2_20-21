package gui;

import dao.DaoArmas;
import dao.modelo.Arma;


public class Main {

    public static void main(String[] args) {
        DaoArmas dao = new DaoArmas();
        dao.getById(3)
                .peek(System.out::println)
                .peekLeft(System.out::println);

        dao.getByNombre("sdf")
                .peek(System.out::println)
                .peekLeft(System.out::println);

        dao.getAll()
                .peek(System.out::println)
                .peekLeft(System.out::println);

        Arma a = new Arma();
        a.setPrecio(20.0);
        a.setNombre("test");

        dao.addArma(a);

        System.out.println(a);



    }
}
