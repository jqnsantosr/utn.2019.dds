package quemepongoAPI.guardarropa;

import lombok.Data;
import quemepongoAPI.atuendo.Atuendo;
import quemepongoAPI.atuendo.AtuendoBuilder;
import quemepongoAPI.atuendo.AtuendoClimaBuilder;
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
    @Transient
    private AtuendoBuilder atuendoBuilder;
    @ElementCollection
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Prenda> ultimasPrendasPedidas;

    public Guardarropa() {}

    public Guardarropa(String nombre) {
        this.nombre = nombre;
        prendas = new ArrayList<>();
        ultimasPrendasPedidas = new ArrayList<>();
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

    public Atuendo crearAtuendoAleatorio(List<PartesCuerpo> partes)
    {
        atuendoBuilder = new AtuendoRandomBuilder();
        constriurAtuendo(partes, null);

        return atuendoBuilder.dameAtuendo();
    }

    public Atuendo crearAtuendoClima(List<PartesCuerpo> listaPartes) //TODO: recibe un evento del usuario
    {
        //TODO: Decidir que clima usar preguntando al adapter cual es la temperatura
        String clima = "Frio";
        int target = 100; // = adapter.dameTemperatura(unEvento);

        atuendoBuilder = new AtuendoClimaBuilder();

        while(atuendoBuilder.calorAtuendo() < target)
        {
            constriurAtuendo(listaPartes, clima);
            if(hayPrendasParaEntregar())
                atuendoBuilder.agregar_nueva_capa();
            else
                break;
        }

        return atuendoBuilder.dameAtuendo();
    }

    //CASO por defecto construye un atuendo con lo basico
    public void constriurAtuendo()
    {
        List<PartesCuerpo> listaPartesDefault = new ArrayList<>();
        listaPartesDefault.add(PartesCuerpo.TORSO);
        listaPartesDefault.add(PartesCuerpo.PIERNAS);
        listaPartesDefault.add(PartesCuerpo.CABEZA);
        listaPartesDefault.add(PartesCuerpo.CALZADO);

        atuendoBuilder.crearNuevoAtuendo();
        atuendoBuilder.setListaDePartes(listaPartesDefault);
        for (PartesCuerpo unaParte : listaPartesDefault)
        {
            atuendoBuilder.buildPorParte(this, unaParte, null);
        }
    }

    //CASO usuario pide una combinacion de partes
    public void constriurAtuendo(List<PartesCuerpo> partes, String clima)
    {
        atuendoBuilder.crearNuevoAtuendo();
        atuendoBuilder.setListaDePartes(partes);
        for (PartesCuerpo parte: partes)
        {
           atuendoBuilder.buildPorParte(this, parte, clima);
        }
    }

    public Prenda darUnaPrendaAleatoria(PartesCuerpo unaParte)
    {
        //lista con las prendas que son de la parte del cuerpo pedida
        List<Prenda> prendasResultado = new ArrayList<>();

        filtrarLista(prendasResultado, unaParte);

        actualizarCacheDePrendasPedidas(prendasResultado);

        return darOtraPrendaAleatoria();
    }

    public Prenda darOtraPrendaAleatoria()
    {
        Random rand = new Random();
        try {
            return ultimasPrendasPedidas.remove(rand.nextInt(ultimasPrendasPedidas.size()));
        } catch(Exception listaVacia) {
            return null;
        }
    }

    public Prenda darUnaPrendaParaClima(PartesCuerpo unaParte, String clima)
    {
        //lista con las prendas que son de la parte del cuerpo pedida
        List<Prenda> prendasResultado = new ArrayList<>();

        filtrarLista(prendasResultado, unaParte);

        ordenarLista(prendasResultado, clima);

        actualizarCacheDePrendasPedidas(prendasResultado);

        return darOtraPrendaParaClima();
    }

    public Prenda darOtraPrendaParaClima()
    {
        try {
            return ultimasPrendasPedidas.remove(0);
        } catch(Exception listaVacia){
            return null;
        }
    }

    private void actualizarCacheDePrendasPedidas(List<Prenda> nuevaLista)
    {
        ultimasPrendasPedidas.clear();
        ultimasPrendasPedidas.addAll(nuevaLista);
    }

    private void filtrarLista(List<Prenda> unaLista, PartesCuerpo unaParte)
    {
        //filtra de todas las prendas en el guardarropas y las agrega en la lista nueva
        for (Prenda unaPrenda: prendas)
        {
            List<PartesCuerpo> partesCuerpoDeLaPrenda = unaPrenda.damePartesCuerpo();

            if(partesCuerpoDeLaPrenda.contains(unaParte))
                unaLista.add(unaPrenda);
        }
    }

    private void ordenarLista(List<Prenda> unaLista, String clima)
    {
        //TODO
    }

    private boolean hayPrendasParaEntregar()
    {
        //TODO
        return false;
    }
}