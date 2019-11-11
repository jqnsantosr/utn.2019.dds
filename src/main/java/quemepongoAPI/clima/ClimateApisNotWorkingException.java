package quemepongoAPI.clima;

public class ClimateApisNotWorkingException extends Throwable {
    public ClimateApisNotWorkingException()
    {
        super("Las APIs de clima no quieren arrancar.");
    }
}
