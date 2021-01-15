package main;

import com.github.javafaker.Faker;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static com.mongodb.client.model.Filters.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class TutorialDelete {
    public static Faker faker = new Faker();


    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323");


        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoDatabase db = mongo.getDatabase("oscar");

        MongoCollection<Document> est = db.getCollection("est");


        System.out.println(
                est.deleteOne(eq("name", "")).getDeletedCount());

//        System.out.println(est.deleteOne(Document.parse("{ _id : ObjectId(\"" + p.get_id() + "\") }")).getDeletedCount());
//        System.out.println(p.get_id());
    }
}
