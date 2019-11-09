package quemepongoAPI.user;

import lombok.Data;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.Prenda;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Data
@Entity
class User {
    private @Id
    @GeneratedValue
    Long id;
    private String nombre;
    private String googleId;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Guardarropa> guardarropas;

    public User() {

    }
    /*
    TODO:
        - Lógica de premium / comunes (en guardarropas?).
     */

    User(String username, String googleId) {
        this.nombre = username;
        this.googleId = googleId;
    }

    User(String username, String googleId, List<Guardarropa> guard) {
        this.nombre = username;
        this.googleId = googleId;
        this.guardarropas = guard;
    }

    Optional<Guardarropa> traerGuardarropasPorId(Long id){
        return guardarropas.stream().filter(g -> g.getId().equals(id)).findFirst();
    }

    void crearGuardarropas(Guardarropa guardarropa) {
        this.guardarropas.add(guardarropa);
    }

    void borrarGuardarropas(Guardarropa guardarropa) throws GuardarropasNotEmptyException {
        if(guardarropa.getPrendas().isEmpty()){
            this.guardarropas.remove(guardarropa);
        } else {
            throw new GuardarropasNotEmptyException(guardarropa.getId());
        }
    }

    boolean existePrendaEnAlgunGuardarropas(Prenda prenda) {
        return guardarropas.stream().anyMatch(guardarropa -> guardarropa.existePrenda(prenda));
    }

    void crearPrendaGuardarropas(Prenda prenda, Long idGuardarropa) {
      traerGuardarropasPorId(idGuardarropa).ifPresent(guardarropa -> guardarropa.agregarPrenda(prenda));
    }
}
