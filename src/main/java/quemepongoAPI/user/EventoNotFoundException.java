package quemepongoAPI.user;

public class EventoNotFoundException extends RuntimeException {
    public EventoNotFoundException(long id)
    {
        super("No se encontro el evento con id: " + id);
    }
}
