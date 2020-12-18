package dao.retrofit;


import dao.modelo.Area;
import dao.modelo.AreasRequest;
import dao.modelo.CompetitionsRequest;
import dao.modelo.TeamsRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface AreasAPI {

  @GET("areas/")
  Call<AreasRequest> loadAreas();

  @GET("areas/{id}")
  Call<Area> loadOneArea(@Path("id") int id);

  @GET("competitions/")
  Call<CompetitionsRequest> loadCompetitions(@Query("areas") long areas);

  @GET("competitions/{id}/teams")
  Call<TeamsRequest> loadTeams(@Path("id") long id,@Query("season") String season);

}
