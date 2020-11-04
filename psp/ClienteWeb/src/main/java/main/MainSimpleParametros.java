package main;

import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainSimpleParametros {

    @SneakyThrows
    public static void main(String[] args) {
        OkHttpClient clientOK = new OkHttpClient();


        HttpUrl.Builder urlBuilder
                = HttpUrl.parse("http://datos.gob.es/apidata/catalog/dataset").newBuilder();
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
