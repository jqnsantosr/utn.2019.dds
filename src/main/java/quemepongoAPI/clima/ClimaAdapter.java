package quemepongoAPI.clima;

import com.google.gson.Gson;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClimaAdapter {

    public Clima normalizeClima(ClimaDarksky darksky) {
        Gson gson = new Gson();
        List<Daily> forecast = StreamSupport
                .stream(darksky.getForecast().get("data")
                        .getAsJsonArray()
                        .spliterator(), false)
                .map(forecastDay -> gson.fromJson(forecastDay, Daily.class))
                .collect(Collectors.toList());
        return new Clima(forecast, darksky.getLocation());
    }

    public Clima normalizeClima(ClimaOpenWeather openWeather) {
        Gson gson = new Gson();
        List<Daily> forecast = StreamSupport
                .stream(openWeather.getList()
                        .getAsJsonArray()
                        .spliterator(), false)
                .map(forecastDay -> {
                    Double time = forecastDay.getAsJsonObject().get("dt").getAsDouble();
                    Double tMin = forecastDay.getAsJsonObject().get("main").getAsJsonObject().get("temp_min").getAsDouble();
                    Double tMax = forecastDay.getAsJsonObject().get("main").getAsJsonObject().get("temp_max").getAsDouble();
                    String sum = forecastDay.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
                    String climate = forecastDay.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
                    Double precip = 0.0;
                    if(climate.equals("Rain")){
                        precip = 0.5;
                    }
                    return new Daily(time, sum, tMin, tMax, precip);
                })
                .collect(Collectors.toList());
        return new Clima(forecast, "");
    }
}
