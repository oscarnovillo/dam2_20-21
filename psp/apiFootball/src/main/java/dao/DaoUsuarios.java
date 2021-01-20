package dao;

import dao.modelo.Area;
import dao.modelo.AreasRequest;
import dao.modelo.Usuario;
import dao.retrofit.AreasAPI;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import io.vavr.control.Either;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;

public class DaoUsuarios {


    public Either<String, Usuario> updateUsuario(Usuario usu) {
        Either<String, Usuario> resultado = null;

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
                resultado = Either.left(response.errorBody().toString());
            }
        }
        catch (Exception e)
        {
            resultado= Either.left("Error de comunicacion");
        }

        return resultado;
    }
}
