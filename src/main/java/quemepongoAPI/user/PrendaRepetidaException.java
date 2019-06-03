package quemepongoAPI.user;

import quemepongoAPI.prenda.Prenda;

public class PrendaRepetidaException extends RuntimeException {
    public PrendaRepetidaException(Prenda prenda) {
        super("This guardarropas already has prenda " + prenda.getNombre());
    }
}
