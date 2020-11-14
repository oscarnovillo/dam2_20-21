package main;

import lombok.SneakyThrows;

//import okhttp3.JavaNetCookieJar;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.CookieManager;
import java.net.CookiePolicy;

public class MainConSession {





    @SneakyThrows
    public static void main(String[] args) {

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);



        OkHttpClient clientOK = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:8080/TestSessionFiltros_war/visitas")
                .build();

        Response resp = clientOK.newCall(request).execute();

        System.out.println(resp.code());
        System.out.println(resp.message());
        System.out.println(resp.body().string());
        for (int i=0; i< resp.headers().size();i++)
        {
            System.out.println(resp.headers().name(i)+ " :::: "+resp.headers().value(i));
        }

        clientOK = new OkHttpClient.Builder()
                //.cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
        resp = clientOK.newCall(request).execute();

        System.out.println(resp.code());
        System.out.println(resp.message());
        System.out.println(resp.body().string());
        for (int i=0; i< resp.headers().size();i++)
        {
            System.out.println(resp.headers().name(i)+ " :::: "+resp.headers().value(i));
        }


        clientOK = null;
//        clientOK.connectionPool().evictAll();
    }
}
