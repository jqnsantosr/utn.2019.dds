package quemepongoAPI.guardarropa;

import lombok.Data;
import quemepongoAPI.atuendo.Atuendo;
import quemepongoAPI.atuendo.AtuendoBuilder;
import quemepongoAPI.atuendo.AtuendoRandomBuilder;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Entity
public class Guardarropa {

    private @Id @GeneratedValue Long id;
    private String nombre;
    @ElementCollection
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Prenda> prendas;
    private AtuendoBuilder atuendoBuilder;

    public Guardarropa() {}

    public Guardarropa(String nombre) {
        this.nombre = nombre;
        prendas = new ArrayList<>();
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

    public Atuendo crearAtuendoAleatorio()
    {
        atuendoBuilder = new AtuendoRandomBuilder();
        constriurAtuendo();

        return atuendoBuilder.dameAtuendo();
    }

    public void constriurAtuendo()
    {
        atuendoBuilder.crearNuevoAtuendo();
        atuendoBuilder.buildTorso(this);
        atuendoBuilder.buildPiernas(this);
        atuendoBuilder.buildCabeza(this);
        atuendoBuilder.buildCalzado(this);
    }

    public Prenda darUnaPrendaAleatoria(PartesCuerpo unaParte)
    {
        //lista con las prendas que son de la parte del cuerpo pedida
        List<Prenda> prendasResultado = new ArrayList<>();

        //filtra de todas las prendas en el guardarropas y las agrega en la lista nueva
        for (Prenda unaPrenda: prendas)
        {
            List<PartesCuerpo> partesCuerpoDeLaPrenda = unaPrenda.damePartesCuerpo();

            if(partesCuerpoDeLaPrenda.contains(unaParte))
                prendasResultado.add(unaPrenda);
        }

        if(prendasResultado.isEmpty())
            //TODO: error no hay prenda para esa parte del cuerpo
            return null;

        //retornar una prenda aleatoria
        Random rand = new Random();
        return prendasResultado.get(rand.nextInt(prendasResultado.size()));
    }
}