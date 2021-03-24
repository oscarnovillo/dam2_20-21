package dao.retrofit;


import dao.modelo.Area;
import dao.modelo.AreasRequest;
import dao.modelo.CompetitionsRequest;
//import dao.modelo.TeamsRequest;
import dao.modelo.Usuario;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;


public interface AreasAPI {

  @GET("areas/")
  Response<AreasRequest> loadAreas();

  @GET("areas/{id}")
  Call<Area> loadOneArea(@Path("id") int id);


  @GET("competitions/")
  Call<CompetitionsRequest> loadCompetitions(@Query("areas") long areas);

  @POST("api/users")
  Call<Usuario> addUsuario(@Body Usuario usuario);

  @DELETE("api/users")
  Call<Usuario> delUsuario(@Query("usuario") Usuario usuario);




//  Call<TeamsRequest> loadTeams(@Path("id") long id,@Query("season") String season);

}
