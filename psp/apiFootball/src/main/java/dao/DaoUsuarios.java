package dao;

import com.google.gson.Gson;
import dao.modelo.ApiError;
import dao.modelo.Area;
import dao.modelo.AreasRequest;
import dao.modelo.Usuario;
import dao.retrofit.AreasAPI;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import io.vavr.control.Either;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;

public class DaoUsuarios {


    public Either<ApiError, Usuario> updateUsuario(Usuario usu) {
        Either<ApiError, Usuario> resultado = null;

        Retrofit retrofit = ConfigurationSingleton_OkHttpClient.getInstance();

        AreasAPI areasAPI = retrofit.create(AreasAPI.class);

        Call<Usuario> call = areasAPI.addUsuario(usu);
        try {
            Response<Usuario> response = call.execute();
            if (response.isSuccessful())
            {
                Usuario u =  response.body();
                resultado = Either.right(u);
            }
            else
            {
                ApiError api = new ApiError();
                if (response.errorBody().contentType().equals(MediaType.get("application/json"))) {
                    Gson g = new Gson();
                    api = g.fromJson(response.errorBody().string(),ApiError.class);
                }
                else
                    api.setMessage(response.errorBody().string());
                resultado = Either.left(api);
            }
        }
        catch (Exception e)
        {

            resultado= Either.left(ApiError.builder().message("Error de comunicacion").build());
        }

        return resultado;
    }
}
