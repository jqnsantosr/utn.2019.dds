package quemepongoAPI.guardarropa;

import lombok.Data;
import quemepongoAPI.prenda.Prenda;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Guardarropa {

    private @Id @GeneratedValue Long id;
    private String nombre;
    @ElementCollection
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Prenda> prendas;

    Guardarropa() {}

    Guardarropa(String nombre) {
        this.nombre = nombre;
    }

    public void addPrenda(Prenda prenda){
        prendas.add(prenda);
    }

    public void removePrenda(Prenda prenda){
        prendas.remove(prenda);
    }

    public boolean hasThisPrenda(Prenda prenda){
        return prendas.contains(prenda);
    }
}