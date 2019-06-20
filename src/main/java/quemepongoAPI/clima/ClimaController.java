package quemepongoAPI.clima;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

import java.util.List;

public class ClimaController implements Callback<Clima> {

    private static final GooglePlaces placesAPI = new GooglePlaces("sarasa"); // key de GooglePlaces API
    private static final String key = "sarasa2"; // darksky key
    private static final String BASE_URL = "https://api.darksky.net/";
    private Clima clima;

    public Clima getClima() {
        return clima;
    }

    public void start(String place) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        List<Place> places = placesAPI.getPlacesByQuery(place, GooglePlaces.MAXIMUM_RESULTS);
        Darksky darksky = retrofit.create(Darksky.class);
        String latLongForQuery = Double.toString(places.get(0).getLatitude()) + ',' + Double.toString(places.get(0).getLongitude());

        Call<Clima> call = darksky.getClima(latLongForQuery, key);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Clima> call, Response<Clima> response) {
        if(response.isSuccessful()) {
            clima = response.body();
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<Clima> call, Throwable t) {
        t.printStackTrace();
    }
}
