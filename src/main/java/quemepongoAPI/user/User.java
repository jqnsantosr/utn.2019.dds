package quemepongoAPI.user;

import lombok.Data;
import quemepongoAPI.evento.Evento;
import quemepongoAPI.guardarropa.CantidadMaximaPrendaSuperadaException;
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
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Evento> eventos;

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

    Optional<Evento> traerEventoPorId(Long id){
        return eventos.stream().filter(g -> g.getId().equals(id)).findFirst();
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
      traerGuardarropasPorId(idGuardarropa).ifPresent(guardarropa -> {
          try {
              guardarropa.agregarPrenda(prenda);
          } catch (CantidadMaximaPrendaSuperadaException e) {
              e.printStackTrace();
          }
      });
    }

    void crearEvento(Evento unEvento) { this.eventos.add(unEvento); }
}
