package quemepongoAPI.clima;

import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.concurrent.CompletableFuture;

public interface OpenWeather {

    @GET("data/2.5/forecast")
    CompletableFuture<ClimaOpenWeather> getClima(@Query("lat") String lat,
                                                 @Query("lon") String lon,
                                                 @Query("APPID") String key,
                                                 @Query("units") String units,
                                                 @Query("lang") String lang
                                                 );
}
