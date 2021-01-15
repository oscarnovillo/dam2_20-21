package main;

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import modelo.PersonaConverter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Projections.*;

public class TutorialPRimeraConexion {





    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");

        MongoDatabase db = mongo.getDatabase("oscarkoko");

        MongoCollection<Document> est = db.getCollection("estlllll");

//        PersonaConverter pc = new PersonaConverter();
        List<Document> personas = new ArrayList<>();

        est.find().into(personas).forEach(System.out::println);


//        est.find().skip(5).limit(5).into(personas);
//
//        personas.stream().map(document -> pc.convertDocumentPersona(document)).forEach(System.out::println);
//        System.out.println("find con expresion regular");
//
//        est.find(new Document("name",new Document("$regex","i.*"))).into(new ArrayList()).forEach(System.out::println);
//
//
//        System.out.println("find con expresion regular y or");
//        est.find(or(eq("name","kk"),regex("name","^L.*H.*"))).into(new ArrayList()).forEach(System.out::println);
//
//
//        System.out.println("find con columna nested");
//        est.find(gt("cosas.cantidad",3)).into(new ArrayList()).forEach(System.out::println);
//
//
//
////    $size,$elemMatch,
//        System.out.println("find con size de un array");
//        est.find(size("cosas",1)).into(new ArrayList()).forEach(System.out::println);
//
//
//// $expr
////    est.find(expr("cosas",1)).into(new ArrayList()).forEach(System.out::println);
//
//
//        //distinct
//        System.out.println("find con size de un array");
//        est.distinct("cosas.nombre",String.class).into(new ArrayList()).forEach(System.out::println);
//
//
//        //projections
//        System.out.println("projection");
//        est.find().projection(new Document("name",1).append("cosas.nombre",1)).into(new ArrayList()).forEach(System.out::println);
//
//        est.find().projection(new Document("name",1).append("_id",0).append("cosas.nombre",1)).into(new ArrayList()).forEach(System.out::println);
//
//        System.out.println("projection de solo algun elemento de un array");
//
//        est.find(Document.parse("{ \"cosas.cantidad\": { $exists: true}}")).into(new ArrayList()).forEach(System.out::println);
//
//        est.find(Document.parse("{ \"cosas.cantidad\": { $exists: true}}"))
//                .projection(new Document("name",1).append("_id",0).append("cosas",new Document("$elemMatch",
//                        new Document("$gt",new Document("cantidad",1)))))
//                .into(new ArrayList()).forEach(System.out::println);
//
//
//
//        System.out.println("ordenando ");
//
//        est.find().sort(new Document("name",1)).forEach((Consumer<Document>) o -> System.out.println(o));
//
//
//        System.out.println("ordenando y filtrado");
//
//        List<Document> cosas = (List)est.find()
//                .sort(Sorts.ascending("name"))
//                .projection(fields(include("name"), excludeId(),
//                        Projections.elemMatch("cosas",gt("cantidad",1))))
//                .into(new ArrayList());
//
//        cosas.forEach(System.out::println);



    }
}
