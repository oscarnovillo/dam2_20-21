package main;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import modelo.Persona;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class MongoConnecion {

    private MongoClient mongo = MongoClients.create("mongodb://dam2.mysql.iesquevedo.es:2323");
    
    public  MongoCollection<Persona> getCollectionPersona(){

        MongoDatabase db = mongo.getDatabase("oscar");

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder()
                        //.conventions(Arrays.asList(ANNOTATION_CONVENTION))
                        .automatic(true).build()));

        MongoCollection<Persona> col = db.getCollection("persona", Persona.class).withCodecRegistry(pojoCodecRegistry);

        return col;
    }



}
