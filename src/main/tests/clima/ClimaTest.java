package clima;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import quemepongoAPI.clima.Clima;
import quemepongoAPI.clima.Darksky;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClimaTest {

    private MockWebServer mockWebServer;
    private Retrofit retrofit;

    @Before
    public void setUp() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        MockitoAnnotations.initMocks(this);
        mockWebServer = new MockWebServer();
        retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void getClimateFromDarksky() throws IOException {

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("{\n" +
                        "    \"latitude\": -34.6237933,\n" +
                        "    \"longitude\": -58.4022563,\n" +
                        "    \"timezone\": \"America/Argentina/Buenos_Aires\",\n" +
                        "    \"currently\": {\n" +
                        "        \"time\": 1561039852,\n" +
                        "        \"summary\": \"Despejado\",\n" +
                        "        \"icon\": \"clear-day\",\n" +
                        "        \"precipIntensity\": 0,\n" +
                        "        \"precipProbability\": 0,\n" +
                        "        \"temperature\": 13.55,\n" +
                        "        \"apparentTemperature\": 13.55,\n" +
                        "        \"dewPoint\": 8.16,\n" +
                        "        \"humidity\": 0.7,\n" +
                        "        \"pressure\": 1023.82,\n" +
                        "        \"windSpeed\": 0.35,\n" +
                        "        \"windGust\": 1.24,\n" +
                        "        \"windBearing\": 54,\n" +
                        "        \"cloudCover\": 0,\n" +
                        "        \"uvIndex\": 2,\n" +
                        "        \"visibility\": 10.269,\n" +
                        "        \"ozone\": 241.1\n" +
                        "    }}"));

        Darksky service = retrofit.create(Darksky.class);
        Call<Clima> call = service.getClima("-34.6237933,-58.4022563", "key");
        assertNotNull(call.execute());
    }

}