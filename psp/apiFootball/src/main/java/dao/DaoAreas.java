package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ConfigurationSingleton_Client;
import dao.modelo.*;
import dao.retrofit.AreasAPI;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import io.vavr.control.Either;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class DaoAreas {

    public Either<String, List<Area>> getAreas() {
        Either<String, List<Area>> resultado = null;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConfigurationSingleton_Client.getInstance().getPath_base())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ConfigurationSingleton_OkHttpClient.getInstance())
                .build();

        AreasAPI areasAPI = retrofit.create(AreasAPI.class);

        Call<AreasRequest> call = areasAPI.loadAreas();
        try {
            Response<AreasRequest> response = call.execute();
            if (response.isSuccessful())
            {
                AreasRequest changesList = response.body();
                resultado = Either.right(changesList.getAreas());
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

    public Either<String, List<Competition>> getCompetitions(Area area) {
        Either<String, List<Competition>> resultado = null;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConfigurationSingleton_Client.getInstance().getPath_base())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ConfigurationSingleton_OkHttpClient.getInstance())
                .build();

        AreasAPI areasAPI = retrofit.create(AreasAPI.class);

        Call<CompetitionsRequest> call = areasAPI.loadCompetitions(area.getId());
        try {
            Response<CompetitionsRequest> response = call.execute();
            if (response.isSuccessful())
            {
                CompetitionsRequest changesList = response.body();
                resultado = Either.right(changesList.getCompetitions());
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

    public Either<String, List<Team>> getTeams(Competition competition,String season) {
        Either<String, List<Team>> resultado = null;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConfigurationSingleton_Client.getInstance().getPath_base())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ConfigurationSingleton_OkHttpClient.getInstance())
                .build();

        AreasAPI areasAPI = retrofit.create(AreasAPI.class);

        Call<TeamsRequest> call = areasAPI.loadTeams(competition.getId(),season);
        try {
            Response<TeamsRequest> response = call.execute();
            if (response.isSuccessful())
            {
                TeamsRequest changesList = response.body();
                resultado = Either.right(changesList.getTeams());
            }
            else
            {
                resultado = Either.left("ERROR "+response.errorBody().toString());
            }
        }
        catch (Exception e)
        {
            resultado= Either.left("Error de comunicacion");
        }

        return resultado;
    }



}
