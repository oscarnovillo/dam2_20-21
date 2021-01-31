package main;

import com.mongodb.client.*;
import com.mongodb.client.model.Field;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;



public class TutorialAggregaciones {

    public static void main(String[] args) {
        MongoClient mongo = MongoClients.create("mongodb://dam2.mysql.iesquevedo.es:2323");

        MongoDatabase db = mongo.getDatabase("oscar");

        MongoCollection<Document> b = db.getCollection("biblioteca");

        b.aggregate(
                Arrays.asList(
                        unwind("$ejemplares"),
                        unwind("$ejemplares.prestamos"),
                        match(eq("ejemplares.prestamos.nombre", "alvaro")),
                        addFields(new Field("fecha",
                                new Document("$dayOfMonth",
                                        new Document("$toDate",
                                                new Document("$subtract", Arrays.asList("$$NOW", "$ejemplares.prestamos.fecha_prestamo"))))))))
                .into(new ArrayList()).forEach(System.out::println);
    }
}
