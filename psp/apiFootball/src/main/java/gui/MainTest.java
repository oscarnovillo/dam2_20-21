package gui;

import com.google.gson.*;
import dao.modelo.ApiError;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

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
        }).create();
        System.out.println(g.fromJson("{\"fecha\":\"2021-01-21T19:21:26.11391\",\"message\":\"must not be empty\"}",
                ApiError.class));
    }
}
