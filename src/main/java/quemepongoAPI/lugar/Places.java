package quemepongoAPI.lugar;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface Places {

    @GET("maps/api/place/findplacefromtext/json")
    CompletableFuture<Lugar> getPlace(@QueryMap Map<String, String> options);
}
