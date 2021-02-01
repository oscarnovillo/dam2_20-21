package main;

import com.github.javafaker.Faker;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import modelo.Ejemplar;
import modelo.Libro;
import modelo.Prestamo;
import modelo.Usuario;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class RellenoMasivo {

    private static int numUsuarios = 100000;
    private static int numLibros = 1000000;
    private static int numMaxEjemplares = 10;
    private static int numMaxPrestamos = 100;


    public static void main(String[] args) {
        Faker f = new Faker();

        MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323");


        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoDatabase db = mongo.getDatabase("oscar");

        MongoCollection<Usuario> col = db.getCollection("usuarios", Usuario.class).withCodecRegistry(pojoCodecRegistry);

        List<Usuario> usus = new ArrayList<>();
        for (int i = 0; i < numUsuarios; i++) {
            String number = f.random().hex(12);
            Usuario u = Usuario.builder()
                    .dni(number)
                    .mail("mail " + number)
                    .password("pass" + number)
                    .user(f.funnyName().name() + " " + number)
                    .build();
            usus.add(u);
        }
        col.insertMany(usus);

        for (int o = 0; o<10;o++) {
            List<Libro> libros = new ArrayList<>();
            for (int i = 0; i < numLibros / 10; i++) {

                int numEjemplares = f.random().nextInt(0, numMaxEjemplares);
                List<Ejemplar> ejemplares = new ArrayList<>();
                for (int k = 0; k < numEjemplares; k++) {

                    List<Prestamo> prestamos = new ArrayList<>();

                    int numPrestamos = f.random().nextInt(0, numMaxPrestamos);
                    for (int j = 0; j < numPrestamos; j++) {
                        LocalDate fechaPrestamo = f.date().past(365, 10, TimeUnit.DAYS).toInstant().atZone(ZoneId.of("UTC")).toLocalDate();
                        LocalDate fechaDevolucion = null;
                        if (f.random().nextBoolean())
                            fechaDevolucion = fechaPrestamo.plusDays(f.random().nextInt(1, 60));

                        Prestamo p = Prestamo.builder()
                                .dniLector(usus.get(f.random().nextInt(0, usus.size() - 1)).getDni())
                                .fechaPrestado(fechaPrestamo)
                                .fechaDevolucion(fechaDevolucion)
                                .build();
                        prestamos.add(p);
                    }
                    Ejemplar e = Ejemplar.builder()
                            .estado(f.color().name())
                            .prestamos(prestamos)
                            .build();
                    ejemplares.add(e);
                }
                Libro l = Libro.builder()
                        .autor(f.book().author())
                        .titulo(f.book().title() + " parte " + f.random().nextInt(1, 6000))
                        .genero(f.book().genre())
                        .ejemplares(ejemplares)
                        .build();
                libros.add(l);
            }
            MongoCollection<Libro> libroMongo = db.getCollection("libros", Libro.class).withCodecRegistry(pojoCodecRegistry);
            libroMongo.insertMany(libros);
            System.out.println(o);
        }

    }
}
