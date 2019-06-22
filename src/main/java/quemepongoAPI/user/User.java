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
    private String username;
    private String googleId;
    @ElementCollection
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Guardarropa> guardarropas;

    /*
    TODO:
        - Lógica de premium / comunes (en guardarropas?).
     */

    User(String username, String googleId) {
        this.username = username;
        this.googleId = googleId;
    }

    User(String username, String googleId, List<Guardarropa> guard) {
        this.username = username;
        this.googleId = googleId;
        this.guardarropas = guard;
    }

    Optional<Guardarropa> getGuardarropasById(Long id){
        return guardarropas.stream().filter(g -> g.getId().equals(id)).findFirst();
    }

    void addGuardarropas(Guardarropa guardarropa) {
        this.guardarropas.add(guardarropa);
    }

    void deleteGuardarropas(Guardarropa guardarropa) throws GuardarropasNotEmptyException {
        if(guardarropa.getPrendas().isEmpty()){
            this.guardarropas.remove(guardarropa);
        } else {
            throw new GuardarropasNotEmptyException(guardarropa.getId());
        }
    }

    boolean isPrendaInAnyGuardarropas(Prenda prenda) {
        return guardarropas.stream().anyMatch(guardarropa -> guardarropa.hasThisPrenda(prenda));
    }

    void addPrendaToGuardarropas(Prenda prenda, Long idGuardarropa) {
      getGuardarropasById(idGuardarropa).ifPresent(guardarropa -> guardarropa.addPrenda(prenda));
    }
}
