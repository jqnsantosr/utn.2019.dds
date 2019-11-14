package quemepongoAPI.clima;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClimaOpenWeather {
    @SerializedName("list")
    @Expose
    private JsonArray list;

    public JsonArray getList() {
        return list;
    }

}

