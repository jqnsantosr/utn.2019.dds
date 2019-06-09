package quemepongoAPI.atuendo;

public class AtuendoIncompletoException extends RuntimeException {
    public AtuendoIncompletoException()
    {
        super("El Atuendo no se puede completar");
    }
}
