package quemepongoAPI.clima;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClimaDarksky {
    @SerializedName("latitude")
    @Expose
    private float latitude;
    @SerializedName("longitude")
    @Expose
    private float longitude;
    @SerializedName("daily")
    @Expose
    private JsonObject forecast;
    @SerializedName("timezone")
    @Expose
    private String location;

    public JsonObject getForecast() {
        return forecast;
    }

    public String getLocation() {
        return location;
    }
}

