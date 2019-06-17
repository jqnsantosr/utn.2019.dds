package quemepongoAPI.user;

public class GuardarropasNotFoundException extends RuntimeException {
    public GuardarropasNotFoundException(long id)
    {
        super("No se encontro el guardarropas con id: " + id);
    }
}
