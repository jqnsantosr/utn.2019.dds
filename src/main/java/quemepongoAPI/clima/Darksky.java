package quemepongoAPI.clima;

import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.concurrent.CompletableFuture;

public interface Darksky {

    @GET("forecast/{key}/{latlong}?lang=es&units=si&exclude=[currently,minutely,hourly]")
    CompletableFuture<ClimaDarksky> getClima(@Path("latlong") String latlong, @Path("key") String key);
}
