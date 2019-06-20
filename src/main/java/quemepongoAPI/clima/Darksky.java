package quemepongoAPI.clima;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Darksky {

    @GET("forecast/{key}/{latlong}?lang=es&units=si")
    Call<Clima> getClima(@Path("latlong") String latlong, @Path("key") String key);
}
