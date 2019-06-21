package quemepongoAPI.user;

public class GuardarropasNotEmptyException extends Exception {
    public GuardarropasNotEmptyException(long id)
    {
        super("El guardarropas " + id + " no esta vacío.");
    }
}
