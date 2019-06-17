package quemepongoAPI.user;

import lombok.Data;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.Prenda;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Data
@Entity
public class User {
    private @Id
    @GeneratedValue
    Long id;
    private String username;
    private String googleId;
    @ElementCollection
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Guardarropa> guardarropas;

    User(String username, String googleId) {
        this.username = username;
        this.googleId = googleId;
    }

    /* GETTER de un Guardarropas */
    public Optional<Guardarropa> getGuardarropasById(Long id){
        return guardarropas.stream().filter(g -> g.getId().equals(id)).findFirst();
    }

    public void addGuardarropas(Guardarropa guardarropa) {
        this.guardarropas.add(guardarropa);
    }

    public boolean deleteGuardarropas(Guardarropa guardarropa) {
        if(guardarropa.getPrendas().isEmpty()){
            this.guardarropas.remove(guardarropa);
            return true;
        } else {
            return false;
        }
    }

    public boolean isPrendaInAnyGuardarropas(Prenda prenda) {
        return guardarropas.stream().anyMatch(guardarropa -> guardarropa.hasThisPrenda(prenda));
    }

    public void addPrendaToGuardarropas(Prenda prenda, Long idGuardarropa) {
      getGuardarropasById(idGuardarropa).ifPresent(guardarropa -> guardarropa.addPrenda(prenda));
    }
}
