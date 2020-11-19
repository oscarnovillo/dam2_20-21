package dao;

import config.ConfigurationSingleton_Client;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import utils.Constantes;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public class DaoProducto_cliente {


    public Either<String, List<String>> getTodosProductos() {
        AtomicReference<Either<String, List<String>>> resultado = new AtomicReference<>();
        OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();

        //Por POST
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.PRODUCTOS;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Try.of(() -> clientOK.newCall(request).execute())
                .onSuccess(response -> {
                    if (response.isSuccessful()) {
                        Try.of(() -> response.body().string())
                                .onSuccess(s ->
                                        resultado.set(Either.right(Arrays.asList(s.split(",")))))
                                .onFailure(throwable -> resultado.set(Either.left("error de comunicacion")));
                    } else {
                        Try.of(() -> response.body().string())
                                .onSuccess(s ->
                                        resultado.set(Either.left(s)))
                                .onFailure(throwable -> resultado.set(Either.left("error de comunicacion")));

                    }
                })
                .onFailure(ConnectException.class, throwable -> {
                    log.error(throwable.getMessage(), throwable);
                    resultado.set(Either.left("El servidor va lento y no se\nha podido CERRAR.\nDisculpe las molestias"));

                });

        return resultado.get();
    }

    public Either<String, List<String>> addCesta(List productosAdd) {


        OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();
        //Por POST
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.CESTA;
        FormBody.Builder b = new FormBody.Builder();
        for (int i = 0; i < productosAdd.size(); i++) {
            b.add(Constantes.PARAMETRO_PRODUCTOS, productosAdd.get(i).toString());
        }
        b.add(Constantes.PARAMETRO_OPCION_CESTA, Constantes.OPCION_ADD_CESTA);
        RequestBody formBody = b.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        return parseaListaProductos(request);
    }

    public Either<String, List<String>> verCesta() {
        AtomicReference<Either<String, List<String>>> resultado = new AtomicReference<>();


        //Por POST
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.CESTA;

        RequestBody formBody = new FormBody.Builder()
                .add(Constantes.PARAMETRO_OPCION_CESTA, Constantes.OPCION_VER_CESTA)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        return parseaListaProductos(request);

    }


    private Either<String, List<String>> parseaListaProductos(Request request) {
        AtomicReference<Either<String, List<String>>> resultado = new AtomicReference<>();
        OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();

        Try.of(() -> clientOK.newCall(request).execute())
                .onSuccess(response -> {
                    if (response.isSuccessful()) {
                        Try.of(() -> response.body().string())
                                .onSuccess(s ->
                                        resultado.set(Either.right(Arrays.asList(s.split(",")))))
                                .onFailure(throwable -> resultado.set(Either.left("error de comunicacion")));
                    } else {
                        Try.of(() -> response.body().string())
                                .onSuccess(s ->
                                        resultado.set(Either.left(s)))
                                .onFailure(throwable -> resultado.set(Either.left("error de comunicacion")));
                    }
                })
                .onFailure(ConnectException.class, throwable -> {
                    log.error(throwable.getMessage(), throwable);
                    resultado.set(Either.left("El servidor va lento y no se\nha podido CERRAR.\nDisculpe las molestias"));
                });

        return resultado.get();
    }

    public Either<String, String> buyCesta() {


        //Por POST
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.CESTA;

        RequestBody formBody = new FormBody.Builder()
                .add(Constantes.PARAMETRO_OPCION_CESTA, Constantes.OPCION_BUY_CESTA)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        return buyLimpiarCesta(request);

    }

    public Either<String, String> clearCesta() {
        String mensaje = null;

        OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();
        //Por POST
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.CESTA;

        RequestBody formBody = new FormBody.Builder()
                .add(Constantes.PARAMETRO_OPCION_CESTA, Constantes.OPCION_CLEAR_CESTA)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        return buyLimpiarCesta(request);
    }


    private Either<String, String> buyLimpiarCesta(Request request) {
        AtomicReference<Either<String, String>> resultado = new AtomicReference<>();

        OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();
        Try.of(() -> clientOK.newCall(request).execute())
                .onSuccess(response -> {
                    if (response.isSuccessful()) {
                        Try.of(() -> response.body().string())
                                .onSuccess(s ->
                                        resultado.set(Either.right(s)))
                                .onFailure(throwable -> resultado.set(Either.left("error de comunicacion")));
                    } else {
                        Try.of(() -> response.body().string())
                                .onSuccess(s ->
                                        resultado.set(Either.left(s)))
                                .onFailure(throwable -> resultado.set(Either.left("error de comunicacion")));
                    }
                })
                .onFailure(ConnectException.class, throwable -> {
                    log.error(throwable.getMessage(), throwable);
                    resultado.set(Either.left("El servidor va lento y no se\nha podido CERRAR.\nDisculpe las molestias"));
                });

        return resultado.get();
    }
}

