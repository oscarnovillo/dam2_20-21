package dao.retrofit;

import dao.modelo.AreasRequest;
import dao.modelo.marvel.Marvel;
import dao.modelo.marvel.Series;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface MarvelApi {

    @GET("v1/public/characters")
    Call<Marvel> getCharacters();

    @GET("v1/public/characters")
    Single<Marvel> getCharactersRx();

}
