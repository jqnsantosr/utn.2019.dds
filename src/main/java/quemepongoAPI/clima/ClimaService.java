package quemepongoAPI.clima;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import quemepongoAPI.lugar.Lugar;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.isNull;

@Component
public class ClimaService {

    private static final String BASE_URL_DARKSKY = "https://api.darksky.net/";
    private static final String BASE_URL_OPENWEA = "https://api.openweathermap.org/";
    private static final String dkKey = "cb486004816f422cfe6a4218f77b71c1"; // darksky key
    private static final String owKey = "62d1ae39377a27552c025b74c1232d6a"; // openweather key
    private static final ClimaAdapter adapter = new ClimaAdapter();

    public Clima getClima(Lugar lugar) throws ClimateApisNotWorkingException {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_DARKSKY)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Darksky darksky = retrofit.create(Darksky.class);
        String latLongForQuery = Double.toString(lugar.getCandidates().get(0).getGeometry().getLocation().getLatitude()) + ',' + Double.toString(lugar.getCandidates().get(0).getGeometry().getLocation().getLongitude());

        CompletableFuture<ClimaDarksky> clima = darksky.getClima(latLongForQuery, dkKey).toCompletableFuture();

        try {
            clima.get();
        } catch (InterruptedException | ExecutionException e) {
            clima = null;
        }

        if (!isNull(clima)) {
            try {
                return adapter.normalizeClima(clima.get());
            } catch (InterruptedException | ExecutionException ignored) {
                // ignored
            }
        } else {
            Retrofit secondRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_OPENWEA)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            OpenWeather openWea = secondRetrofit.create(OpenWeather.class);
            String[] splitter = latLongForQuery.split(",");
            CompletableFuture<ClimaOpenWeather> secondClima = openWea.getClima(splitter[0], splitter[1], owKey, "metric", "es").toCompletableFuture();
            try {
                return adapter.normalizeClima(clima.get());
            } catch (InterruptedException | ExecutionException ignored) {
                throw new ClimateApisNotWorkingException();
            }
        }
        return null;
    }
}
