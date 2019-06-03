package quemepongoAPI.user;

import quemepongoAPI.guardarropa.Guardarropa;

public class UserAlreadyHasGuardarropaException extends RuntimeException {
    UserAlreadyHasGuardarropaException(Guardarropa guardarropa) {
        super("User already has guardarropas " + guardarropa.getId());
    }
}
