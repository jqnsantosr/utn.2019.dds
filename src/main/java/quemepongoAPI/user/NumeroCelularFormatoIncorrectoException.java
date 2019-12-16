package quemepongoAPI.user;

public class NumeroCelularFormatoIncorrectoException extends RuntimeException {
    public NumeroCelularFormatoIncorrectoException()
    {
        super("El numero ingresado no es correcto (debe tener 10 numeros empezando por 11)");
    }
}
