package quemepongoAPI.clima;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clima {
    @SerializedName("latitude")
    @Expose
    private float latitude;
    @SerializedName("longitude")
    @Expose
    private float longitude;
    @SerializedName("currently")
    @Expose
    private Currently climateNow;
    @SerializedName("timezone")
    @Expose
    private String location;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Currently getClimateNow() {
        return climateNow;
    }

    public void setClimateNow(Currently climateNow) {
        this.climateNow = climateNow;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
