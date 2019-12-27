package quemepongoAPI.user;

import lombok.Data;
import quemepongoAPI.evento.Evento;
import quemepongoAPI.guardarropa.CantidadMaximaPrendaSuperadaException;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.Prenda;
import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class User {
    private @Id
    @GeneratedValue
    Long id;
    private String nombre;
    private String email;
    private String numeroCelular;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Guardarropa> guardarropas;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Evento> eventos;
    private boolean esPremium = false;

    public User() {

    }

    User(String username, String email) {
        this.nombre = username;
        this.email = email;
    }

    User(String username, String email, List<Guardarropa> guard) {
        this.nombre = username;
        this.email = email;
        this.guardarropas = guard;
    }

    public User(String username, String email, String numeroCelular, boolean premium)
    {
        this.nombre = username;
        this.email = email;
        this.guardarropas = new ArrayList<>();
        this.numeroCelular = ChequearNumeroCelular(numeroCelular);
        this.esPremium = premium;
    }

    public void PasarAPremium()
    {
        this.esPremium = true;
        for (Guardarropa unGuardarropa : guardarropas) {
            unGuardarropa.PasarAPremium();
        }
    }

    Optional<Guardarropa> traerGuardarropasPorId(Long id){
        return guardarropas.stream().filter(g -> g.getId().equals(id)).findFirst();
    }

    List<Guardarropa> traerGuardarropas(){
        return guardarropas;
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
        Optional<Guardarropa> unGuardarropa = traerGuardarropasPorId(idGuardarropa);
        if(unGuardarropa.isPresent())
            unGuardarropa.get().agregarPrenda(prenda);
    }

    boolean puedeAgregarPrenda(long idGuardarropa)
    {
        Optional<Guardarropa> unGuardarropa = traerGuardarropasPorId(idGuardarropa);
        if(unGuardarropa.isPresent())
            return unGuardarropa.get().puedeAgregarPrenda();
        else
            throw (new GuardarropasNotFoundException(idGuardarropa));
    }

    void crearEvento(Evento unEvento) { this.eventos.add(unEvento); }

    private String ChequearNumeroCelular(String numero)
    {
        if(numero.matches("11[0-9]{8}"))
        {
            return numero;
        }
        else
        {
            throw new NumeroCelularFormatoIncorrectoException();
        }
    }

    public Long getId(){
        return id;
    }
}