package quemepongoAPI.lugar;

public class LugarServiceException extends Throwable {
    public LugarServiceException(String error) {
        super("Lugar service error: " + error);
    }
}
