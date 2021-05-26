package main;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import modelo.Persona;
import modelo.Things;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class TutorialUpdate {

  public static void main(String[] args) {
    MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323");

    MongoDatabase db = mongo.getDatabase("oscar");
    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    MongoCollection<Persona> est = db.getCollection("persona", Persona.class)
            .withCodecRegistry(pojoCodecRegistry);


    List<Things> cosas = new ArrayList<>();
    cosas.add(Things.builder().nombre("lasdasdl").cantidad(5).build());
    cosas.add(Things.builder().nombre("lasdasdl").cantidad(1).build());
    cosas.add(Things.builder().nombre("lasdasd").cantidad(2).build());


    //     System.out.println(est.updateMany(eq("name","cambiar")
//            ,
//             combine(set("name","nuevo2"),set("fecha",2),pushEach("cosas",cosas)))
//            .getModifiedCount());

//     System.out.println(est.updateMany(eq("name","cambiar")
//            ,
//             combine(set("name","nuevo2"),set("fecha",2),set("cosas",cosas)))
//            .getModifiedCount());

//    System.out.println(est.updateMany(eq("name","cambiar")
//            ,
//            combine(unset("cosas")))
//            .getModifiedCount());
//
//    System.out.println(est.updateMany(eq("name","cambiar")
//            ,
//            combine(set("cosas",cosas)))
//            .getModifiedCount());


    //
//    System.out.println(est.updateOne(
//            eq("name", "estoy tonto"),
//            set("zapatilla.nombre2","nike"))
//            .getModifiedCount());

//
//    System.out.println(est.updateOne(
//            eq("name", "estoy tonto"),
//            push("cosas",
//                    new Document("nombre","nuevo2").append("cantidad",10)))
//            .getModifiedCount());

//    System.out.println(est.updateMany(
//            eq("name", "cambiar"),
//            pull("cosas",
//                    eq("cantidad",1)))
//            .getModifiedCount());

//    est.updateMany(
//        eq("name", "jj"),
//        Updates.combine(set("cosas.0.cantidad", 2),set("cosas.1.cantidad", 210)));
//
// $[] cambio a todos
// n cambia posicion n

//    System.out.println(est.updateOne(
//        eq("name", "cambiar"),
//        set("cosas.$[].nombre", "test")).getModifiedCount());
//
//
//
//
    System.out.println(est.updateOne(eq("name", "cambiar"),
       set("cosas.$[oo].cantidad",7),
        new UpdateOptions().arrayFilters(
            List.of(and(eq("oo.nombre","test"),
                    lt("oo.cantidad",9))))).getModifiedCount());

//    System.out.println(est.updateMany(
//        eq("name","kkl"),
//        set("cosas.$.cantidad","1")).getModifiedCount());

    // updates $pull, $push, $[<identifier>] update options

  }
}
