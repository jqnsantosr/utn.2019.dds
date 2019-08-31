package quemepongoAPI.clima;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import quemepongoAPI.lugar.Lugar;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.nonNull;

@Component
public class ClimaService {

    private static final String BASE_URL = "https://api.darksky.net/";
    private static final String key = "cb486004816f422cfe6a4218f77b71c1"; // darksky key

    public Clima getClima(Lugar lugar) throws ExecutionException, InterruptedException {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Darksky darksky = retrofit.create(Darksky.class);
        String latLongForQuery = Double.toString(lugar.getCandidates().get(0).getGeometry().getLocation().getLatitude()) + ',' + Double.toString(lugar.getCandidates().get(0).getGeometry().getLocation().getLongitude());

        CompletableFuture<Clima> clima = darksky.getClima(latLongForQuery, key).toCompletableFuture();

        return clima.get();
    }
}