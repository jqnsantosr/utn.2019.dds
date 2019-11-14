package clima;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import quemepongoAPI.clima.*;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClimaTest {

    private MockWebServer mockWebServerOne;
    private MockWebServer mockWebServerTwo;
    private Retrofit retrofit;
    private Retrofit secondRetrofit;

    @Before
    public void setUp() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        MockitoAnnotations.initMocks(this);
        mockWebServerOne = new MockWebServer();
        mockWebServerTwo = new MockWebServer();
        retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServerOne.url("").toString())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        secondRetrofit = new Retrofit.Builder()
                .baseUrl(mockWebServerTwo.url("").toString())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @After
    public void tearDown() throws Exception {
        mockWebServerOne.shutdown();
        mockWebServerTwo.shutdown();
    }

    @Test
    public void getClimateFromDarksky() {

        mockWebServerOne.enqueue(new MockResponse()
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
        CompletableFuture<ClimaDarksky> clima = service.getClima("-34.6237933,-58.4022563", "key");
        assertNotNull(clima);
    }

    @Test
    public void getClimateFromOpenWeather() {

        mockWebServerOne.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("\"{    \\\"cod\\\": \\\"200\\\",    " +
                        "\\\"message\\\": 0,    \\\"cnt\\\": 40,    " +
                        "\\\"list\\\": [        {            " +
                        "\\\"dt\\\": 1573441200,            " +
                        "\\\"main\\\": {                " +
                        "\\\"temp\\\": 18.79,                " +
                        "\\\"temp_min\\\": 18.79,                " +
                        "\\\"temp_max\\\": 19.36,                " +
                        "\\\"pressure\\\": 1022,                " +
                        "\\\"sea_level\\\": 1022,                " +
                        "\\\"grnd_level\\\": 1020,                " +
                        "\\\"humidity\\\": 70,                " +
                        "\\\"temp_kf\\\": -0.57            },            " +
                        "\\\"weather\\\": [                {                    " +
                        "\\\"id\\\": 800,                    " +
                        "\\\"main\\\": \\\"Clear\\\",                    " +
                        "\\\"description\\\": \\\"cielo claro\\\",                    " +
                        "\\\"icon\\\": \\\"01n\\\"                }            ],            " +
                        "\\\"clouds\\\": {               " +
                        " \\\"all\\\": 0            },            " +
                        "\\\"wind\\\": {                " +
                        "\\\"speed\\\": 7.25,               " +
                        " \\\"deg\\\": 74            },           " +
                        " \\\"sys\\\": {               " +
                        " \\\"pod\\\": \\\"n\\\"            },       " +
                        "     \\\"dt_txt\\\": \\\"2019-11-11 03:00:00\\\" " +
                        "       },\""));

        OpenWeather service = secondRetrofit.create(OpenWeather.class);
        CompletableFuture<ClimaOpenWeather> clima = service.getClima("-34.6237933", "-58.4022563", "key", "metric", "es");
        assertNotNull(clima);
    }

}