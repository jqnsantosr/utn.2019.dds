package quemepongoAPI.clima;

public class ClimateApisNotWorkingException extends Exception {
    public ClimateApisNotWorkingException()
    {
        super("Las APIs de clima no quieren arrancar.");
    }
}
