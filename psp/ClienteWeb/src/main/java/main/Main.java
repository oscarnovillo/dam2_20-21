package main;

import okhttp3.*;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

public class Main {


    public static void main(String[] args) {

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

//        OkHttpClient clientOK = new OkHttpClient.Builder()
//                .cookieJar(new JavaNetCookieJar(cookieManager))
//
//                .addInterceptor(chain -> {
//                    Request original = chain.request();
//
//                    Request.Builder builder1 = original.newBuilder()
//                            .header("X-Auth-Token", "2deee83e549c4a6e9709871d0fd58a0a")
//                            .url(original.url().newBuilder().addQueryParameter("headToken","adfsdf").build());
//                    Request request = builder1.build();
//                    return chain.proceed(request);}
//                )
//                .build();

       OkHttpClient clientOK = new OkHttpClient();

        HttpUrl.Builder urlBuilder
                = HttpUrl.parse("/ex/bars").newBuilder();
        urlBuilder.addQueryParameter("id", "1");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()

                .url("http://www.marca.es")
                .build();
        RequestBody rb = RequestBody.create("hola", MediaType.get("application/json"));
        try {
            Response resp = clientOK.newCall(request).execute();
            System.out.println(resp.code());
            System.out.println(resp.message());
            System.out.println(resp.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(clientOK.connectionPool().connectionCount());
        clientOK.connectionPool().evictAll();
    }
}
