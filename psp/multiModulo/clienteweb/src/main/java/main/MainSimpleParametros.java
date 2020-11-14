package main;

import lombok.SneakyThrows;
import okhttp3.*;

import java.util.Arrays;

public class MainSimpleParametros {

    @SneakyThrows
    public static void main(String[] args) {
        OkHttpClient clientOK = new OkHttpClient.Builder()
        //.connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT,ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
                .build();


        HttpUrl.Builder urlBuilder
                = HttpUrl.parse("https://www.google.es").newBuilder();
        urlBuilder.addQueryParameter("_pageSize", "1")
                    .addQueryParameter("_page","0");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response resp = clientOK.newCall(request).execute();

        System.out.println(resp.code());
        System.out.println(resp.message());
        System.out.println(resp.body().string());

        clientOK.connectionPool().evictAll();
    }
}
