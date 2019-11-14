package quemepongoAPI.evento;

public class FechaYHoraParseException extends Throwable{
    public FechaYHoraParseException(String error) {
        super("fecha y hora error al parsear: " + error);
    }
}
