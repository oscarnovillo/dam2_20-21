package main;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainErrores {

    @SneakyThrows
    public static void main(String[] args) {
        OkHttpClient clientOK = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:8080/TestSessionFiltros_war/errores")
                .build();

        Response resp = clientOK.newCall(request).execute();


        if (resp.isSuccessful()) {
            System.out.println(resp.code());

            System.out.println(resp.body().string());
        }
        else
        {
            System.out.println("HA HABIDO ERROR "+resp.code());

            System.out.println(resp.body().string());
        }

        clientOK.connectionPool().evictAll();
    }
}
