package main;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import modelo.Persona;
import modelo.PersonaConverter;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.pojo.Conventions.ANNOTATION_CONVENTION;


public class TutorialFind {

  public static void main(String[] args) {
    MongoClient mongo = MongoClients.create("mongodb://dam2.mysql.iesquevedo.es:2323");

    MongoDatabase db = mongo.getDatabase("oscar");

    MongoCollection<Document> est = db.getCollection("est");

    PersonaConverter pc = new PersonaConverter();
    List<Document> personas = new ArrayList<>();

    est.find().into(personas);

    personas.stream()
            .map(document -> pc.convertDocumentPersona(document))
            .forEach(System.out::println);

    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder()
                    //.conventions(Arrays.asList(ANNOTATION_CONVENTION))
                    .automatic(true).build()));

    MongoCollection<Persona> col = db.getCollection("est", Persona.class).withCodecRegistry(pojoCodecRegistry);

    List<Persona> personas1 =

    col.find().into(new ArrayList());

    personas1.stream().forEach(System.out::println);


    System.out.println("find");
    String valor = "Becki Feest";

    est.find(Document.parse("{\"name\" : \""+valor+"\"}")).into(new ArrayList()).forEach(System.out::println);
    col.find(new Document("name",valor)).into(new ArrayList()).forEach(System.out::println);

    col.find(eq("name",valor))
            .into(new ArrayList()).forEach(System.out::println);

    LocalDate l = LocalDate.of(1984,11,6);
    col.find(and(eq("name",valor),eq("fecha",l)))
            .into(new ArrayList()).forEach(System.out::println);

    col.find(and(eq("_id",new ObjectId("5fef79cd8fc55d1e0a233fc4"))))
            .into(new ArrayList()).forEach(System.out::println);

//    col.find(exists("name",false))
//            .into(new ArrayList()).forEach(System.out::println);

//    System.out.println("find con expresion regular");
//
//    est.find(new Document("name",new Document("$regex","i.*"))).into(new ArrayList()).forEach(System.out::println);
//
//    est.find(regex("name","i.*")).into(new ArrayList()).forEach(System.out::println);

//    System.out.println("find con expresion regular y or");
//    est.find(or(eq("name","kk"),regex("name","^L.*H.*"))).into(new ArrayList()).forEach(System.out::println);
//
//
//    System.out.println("find con columna nested");
//    col.find(gt("cosas.cantidad",3))
//            .into(new ArrayList()).forEach(System.out::println);
//
//    System.out.println("find con columna nested");
//    col.find(size("cosas",1))
//            .into(new ArrayList()).forEach(System.out::println);
//
//    System.out.println("find con columna nested");
//    col.find(exists("cosas.0",true))
//            .into(new ArrayList()).forEach(System.out::println);
//
//    System.out.println("find con columna nested");
//    col.find(gt("cosas.0.cantidad",13))
//            .into(new ArrayList()).forEach(System.out::println);



//
//
////    $size,$elemMatch,
//    System.out.println("find con size de un array");
//    est.find(size("cosas",1)).into(new ArrayList()).forEach(System.out::println);
//
//
//// $expr
////    est.find(expr("cosas",1)).into(new ArrayList()).forEach(System.out::println);
//
//
//    //distinct
//    System.out.println("find con size de un array");
//    est.distinct("cosas.nombre",String.class).into(new ArrayList()).forEach(System.out::println);
//
//
//    //projections
//    System.out.println("projection");
//    est.find().projection(new Document("name",1).append("cosas.nombre",1)).into(new ArrayList()).forEach(System.out::println);
//
//    est.find().projection(new Document("name",1).append("_id",0).append("cosas.nombre",1)).into(new ArrayList()).forEach(System.out::println);
//
//    System.out.println("projection de solo algun elemento de un array");
//
//    est.find(Document.parse("{ \"cosas.cantidad\": { $exists: true}}")).into(new ArrayList()).forEach(System.out::println);
//
//    est.find(Document.parse("{ \"cosas.cantidad\": { $exists: true}}"))
//            .projection(new Document("name",1).append("_id",0).append("cosas",new Document("$elemMatch",
//        new Document("$gt",new Document("cantidad",1)))))
//            .into(new ArrayList()).forEach(System.out::println);
//
//
//
//    System.out.println("ordenando ");
//
//    est.find().sort(new Document("name",1)).forEach((Consumer<Document>)o -> System.out.println(o));
//
//
//    System.out.println("ordenando y filtrado");
//
    List<Document> cosas = (List)est.find()
        .sort(Sorts.ascending("name"))
        .projection(fields(include("name"), excludeId(),
            Projections.elemMatch("cosas",gt("cantidad",3))))
        .into(new ArrayList());

    cosas.forEach(System.out::println);

//    Arrays.asList(unwind("$ejemplares"), unwind("$ejemplares.prestamos"), match(eq("ejemplares.prestamos.nombre", "alvaro")), addFields(new Field("fecha",
//            new Document("$dayOfMonth",
//                    new Document("$toDate",
//                            new Document("$subtract", Arrays.asList("$$NOW", "$ejemplares.prestamos.fecha_prestamo")))))))

  }
}
