package main;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainSimple {

    @SneakyThrows
    public static void main(String[] args) {
        OkHttpClient clientOK = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://www.marca.es")
                .build();

        Response resp = clientOK.newCall(request).execute();

        System.out.println(resp.code());
        System.out.println(resp.message());
        System.out.println(resp.body().string());

        clientOK.connectionPool().evictAll();
    }
}
