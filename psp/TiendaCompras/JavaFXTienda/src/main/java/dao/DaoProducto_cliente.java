package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.ConfigurationSingleton_Client;
import dao.modelo.Producto;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import utils.Constantes;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public class DaoProducto_cliente {


    public Either<String, List<Producto>> getTodosProductos() {
        //Por POST
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.URL_PRODUCTOS;

        Request request = new Request.Builder()
                .url(url)
                .build();

        return parseaListaProductos(request);
    }

    public Either<String, List<Producto>> addCesta(List<Producto> productosAdd) {
        //Por POST
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.URL_CESTA;
        FormBody.Builder b = new FormBody.Builder();
        Gson gson = new Gson();
        b.add(Constantes.PARAMETRO_PRODUCTOS, gson.toJson(productosAdd));

        b.add(Constantes.PARAMETRO_OPCION_CESTA, Constantes.OPCION_ADD_CESTA);
        RequestBody formBody = b.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        return parseaListaProductos(request);
    }


    public Either<String, Producto> editarProducto(Producto producto) {
        //Por PUT para actualizar
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.URL_PRODUCTOS;

        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("producto", gson.toJson(producto));
        String urlConParams = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(urlConParams)
                .put(RequestBody.create("HOla Alvaro que tal la familia", MediaType.get("application/json")))
                .build();
        OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();

        AtomicReference<Either<String, Producto>> resultado = new AtomicReference<>();
        Try.of(() -> clientOK.newCall(request).execute())
                .onSuccess(response -> {
                    if (response.isSuccessful()) {
                        try {
                            resultado.set(
                                    Either.right(gson.fromJson(response.body().string(), Producto.class)));
                        } catch (Exception e) {
                            resultado.set(Either.left("ocurrio un error copjn el parseo de datos del servidor"));
                        }
                    }
                    else
                    {
                        try {
                            resultado.set(Either.left(response.body().string() + response.code()));
                        }catch (Exception e) {
                            resultado.set(Either.left("ocurrio un error con el servidor"));
                        }

                    }
                });
        return resultado.get();
    }

    public Either<String, List<Producto>> verCesta() {
        //Por POST
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.URL_CESTA;

        RequestBody formBody = new FormBody.Builder()
                .add(Constantes.PARAMETRO_OPCION_CESTA, Constantes.OPCION_VER_CESTA)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        return parseaListaProductos(request);

    }


    private Either<String, List<Producto>> parseaListaProductos(Request request) {
        Gson gson = new Gson();


        AtomicReference<Either<String, List<Producto>>> resultado = new AtomicReference<>();
        OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();

        Try.of(() -> clientOK.newCall(request).execute())
                .onSuccess(response -> {
                    if (response.isSuccessful()) {
                        Try.of(() -> response.body().string())
                                .onSuccess(sProducto ->
                                        Try.of(() -> gson.fromJson(sProducto, new TypeToken<List<Producto>>() {
                                        }.getType()))
                                                .map(o -> (List) o)
                                                .onSuccess(o -> resultado.set(Either.right(o)))
                                                .onFailure(throwable -> resultado.set(Either.left("el objeto del servidor no se pudo parsear"))))
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
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.URL_CESTA;

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

        //Por POST
        String url = ConfigurationSingleton_Client.getInstance().getPath_base() + Constantes.URL_CESTA;

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

