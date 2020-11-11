package main;

import lombok.SneakyThrows;
import okhttp3.*;

public class MainSimplePost {

    @SneakyThrows
    public static void main(String[] args) {
        OkHttpClient clientOK = new OkHttpClient();


        HttpUrl.Builder urlBuilder
                = HttpUrl.parse("http://localhost:8080/TestSessionFiltros_war/rest").newBuilder();

        String url = "http://localhost:8080/TestSessionFiltros_war/rest";

        RequestBody formBody = new FormBody.Builder()
                .add("nombre", "test1")
                .add("nombre", "test")
                .build();

        FormBody.Builder b = new FormBody.Builder();

        b.add("ff","iik");


        formBody = b.build();


        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response resp = clientOK.newCall(request).execute();

        System.out.println(resp.code());
        System.out.println(resp.message());
        System.out.println(resp.body().string());

        clientOK.connectionPool().evictAll();
    }
}
