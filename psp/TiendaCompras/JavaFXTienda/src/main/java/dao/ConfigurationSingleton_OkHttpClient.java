package dao;

import lombok.extern.log4j.Log4j2;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;

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
                    .cookieJar(new JavaNetCookieJar(cookieManager))
                    .build();
        }
        return clientOK;
    }

}
