package gui;

import com.google.gson.*;
import dao.modelo.ApiError;
import dao.modelo.Usuario;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainTest {

    public static void main(String[] args) {


        Jsonb jsonb = JsonbBuilder.create();

        System.out.println(jsonb.fromJson("{\"fecha\":\"2021-01-21T19:21:26.11391\",\"message\":\"must not be empty\"}",
                ApiError.class));

        Gson g = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>(){
            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return new JsonPrimitive(formatter.format(localDateTime));
            }


                }

        ).create();
        System.out.println(g.fromJson("{\"fecha\":\"2021-01-21T19:21:26.11391\",\"message\":\"must not be empty\"}",
                ApiError.class));

        Gson g1 = new Gson();
        System.out.println(g1.toJson(new Usuario(null, "nombre", LocalDateTime.now())));
        System.out.println(g.toJson(new Usuario(null, "nombre", LocalDateTime.now())));

    }
}
