package quemepongoAPI.clima;

import java.util.List;

public class Clima {

    private List<Daily> forecast;
    private String location;

    public Clima() {

    }

    Clima(List<Daily> f, String l) {
        forecast = f;
        location = l;
    }

    public List<Daily> getForecast() {
        return forecast;
    }

    public String getLocation() { return location; }

    public Double getMostProximateTemperature() {
        return forecast.get(0).getTemperatureMax();
    }

}
