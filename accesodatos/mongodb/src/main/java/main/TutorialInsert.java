package main;

import com.github.javafaker.Faker;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import modelo.Persona;
import modelo.PersonaConverter;
import modelo.Things;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class TutorialInsert {
    public static Faker faker = new Faker();


    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323");


        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoDatabase db = mongo.getDatabase("oscar");

        MongoCollection<Document> est = db.getCollection("est");

        //primera manera
        est.insertOne(Document.parse("{ \"kk\":"+8+",\"ll\":\"ll\"}"));

        //segunda manera
        Document d = new Document();
        d.put("kk",90);
        d.put("ll","ll");
        d.put("cosas",Document.parse("{ \"kk\":9,\"ll\":\"ll\"}"));

        est.insertOne(d);


        // tercera manera
        Persona p = new Persona();
        p.setName(faker.name().name());
        p.setFecha(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        List<Things> cosas;
        cosas = new ArrayList<>();
        cosas.add(Things.builder().nombre(faker.food().dish()).cantidad(faker.number().numberBetween(10, 20)).build());
        p.setCosas(cosas);
//
        PersonaConverter pc = new PersonaConverter();
        Document d1 = pc.convertPersonaDocument(p);
        est.insertOne(d1);
        p.setId(d.getObjectId("_id"));
        System.out.println(p);


        MongoCollection<Persona> col = db.getCollection("est", Persona.class).withCodecRegistry(pojoCodecRegistry);
        Persona p1 = new Persona();
        p1.setName(null);

        cosas = new ArrayList<>();
        cosas.add(Things.builder().nombre("temp").cantidad(2).build());
        p1.setCosas(cosas);
        col.insertOne(p1);

        System.out.println(p1);


        col.find().into(new ArrayList()).forEach(System.out::println);
        //
//        // System.out.println(est.deleteOne(new Document("_id", p.get_id())).getDeletedCount());
//        // System.out.println(est.deleteOne(Document.parse("{ _id : ObjectId(\""+p.get_id()+"\") }")).getDeletedCount());
//        System.out.println(p.get_id());
    }
}
