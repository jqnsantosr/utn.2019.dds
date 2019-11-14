package quemepongoAPI.lugar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class LugarService {

    private static final String BASE_URL = "https://maps.googleapis.com/";
    private static final String key = "AIzaSyD7Int-FkF8Ad2UgtK4J-6Pd8hWIZzs1Y8"; // google key

    public Lugar getLugar(String lugar) throws ExecutionException, InterruptedException {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Places googlePlaces = retrofit.create(Places.class);
        Map<String, String> data = new HashMap<>();
        data.put("input", lugar);
        data.put("inputtype", "textquery");
        data.put("fields", "geometry");
        data.put("key", key);

        CompletableFuture<Lugar> googlePlace = googlePlaces
                .getPlace(data)
                .toCompletableFuture();

        return googlePlace.get();
    }
}
