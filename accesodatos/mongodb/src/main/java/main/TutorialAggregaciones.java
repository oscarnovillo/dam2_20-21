package main;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import org.bson.BsonNull;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;


public class TutorialAggregaciones {

    public static void main(String[] args) {
        MongoClient mongo = MongoClients.create("mongodb://dam2.mysql.iesquevedo.es:2323");

        MongoDatabase db = mongo.getDatabase("oscar");

        MongoCollection<Document> b = db.getCollection("biblioteca");

//        b.aggregate(
//                Arrays.asList(
//                        unwind("$ejemplares"),
//                        unwind("$ejemplares.prestamos"),
//                        match(eq("ejemplares.prestamos.nombre", "alvaro")),
//                        addFields(new Field("fecha",
//                                new Document("$dayOfMonth",
//                                        new Document("$toDate",
//                                                new Document("$subtract", Arrays.asList("$$NOW", "$ejemplares.prestamos.fecha_prestamo"))))))))
//                .into(new ArrayList()).forEach(System.out::println);


//        db.getCollection("libros").aggregate(Arrays.asList(addFields(new Field("tam",
//                new Document("$size", "$ejemplares"))), group(new BsonNull(), sum("numero", "$tam"))))
//                .into(new ArrayList<>()).forEach(document -> System.out.println(document.get("numero")));


        MongoCollection<Document> col = db.getCollection("games");

        col.aggregate(Arrays.asList(
                match(and(eq("platform", "Wii"), ne("precio", "0"))),
                addFields(new Field("precio",
                                new Document("$toDouble", "$eu_sales")),
                        new Field("precio1",
                                new Document("$toDouble", "$eu_sales"))),
                group(new BsonNull(), sum("cantidad", 1L),
                        avg("precio", "$precio"),
                        max("preciomaximo", "$precio"))))

                .into(new ArrayList<>()).forEach(System.out::println);


    }
}
