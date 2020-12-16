package dao.utils;

import lombok.extern.log4j.Log4j2;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Log4j2
public class ConfigurationSingleton_OkHttpClient {
    private static OkHttpClient clientOK;

    private ConfigurationSingleton_OkHttpClient() {
    }

    public static OkHttpClient getInstance() {
        if (clientOK == null) {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            clientOK = new OkHttpClient.Builder()
                    .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .addInterceptor(chain -> {
                        Request original = chain.request();

                        Request.Builder builder1 = original.newBuilder()
                                .header("X-Auth-Token", "2deee83e549c4a6e9709871d0fd58a0a")
                                .url(original.url().newBuilder().addQueryParameter("headToken","adfsdf").build());
                        Request request = builder1.build();
                        return chain.proceed(request);}
                    )
                    .cookieJar(new JavaNetCookieJar(cookieManager))
                    .build();

        }
        return clientOK;
    }

}
