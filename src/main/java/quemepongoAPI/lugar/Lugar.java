package quemepongoAPI.lugar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Lugar {
    @SerializedName("candidates")
    @Expose
    private List<Site> candidates;

    public List<Site> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Site> candidates) {
        this.candidates = candidates;
    }
}
