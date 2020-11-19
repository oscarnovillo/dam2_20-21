package dao;

import config.ConfigurationSingleton_Client;
import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import utils.Constantes;

import java.io.IOException;
import java.net.ConnectException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public class DaoUsuario_cliente {
    public String usuario_login(String usuario, String password) {
        String mensaje = null;
        try {
            OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();

            //Por POST
            String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.LOGIN;

            RequestBody formBody = new FormBody.Builder()
                    .add("user", usuario)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            Response resp = clientOK.newCall(request).execute();
            if (resp.isSuccessful()) {
                mensaje = resp.body().string();
            } else {
                mensaje = "Usuario no valido";
            }
        } catch (ConnectException e) {
            mensaje = "El servidor va lento y no se\nha podido conectar.\nDisculpe las molestias";
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            mensaje = "Ha habido problemas en el login";
        }
        return mensaje;
    }

    public String logOut() {
        AtomicReference<String> mensaje = new AtomicReference<>();

        OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();


        //Por POST
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.LOGOUT;
        Request request = new Request.Builder()
                .url(url)
                .build();

        Try<Response> resp = Try.of(() -> clientOK.newCall(request).execute());
        resp.onSuccess(response -> {
            if (response.isSuccessful())
                mensaje.set(Try.of(() -> response.body().string()).getOrElse("Error en la comunicacion"));
            else
                mensaje.set("Error en la comunicacion");
        }).onFailure(ConnectException.class, throwable -> {
            log.error(throwable.getMessage(), throwable);
            mensaje.set("El servidor va lento y no se\nha podido CERRAR.\nDisculpe las molestias");
        });

        return mensaje.get();
    }
}

