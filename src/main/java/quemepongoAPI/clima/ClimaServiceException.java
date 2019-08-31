package quemepongoAPI.clima;

public class ClimaServiceException extends Throwable {
    public ClimaServiceException(String error) {
        super("Clima service error: " + error);
    }
}
