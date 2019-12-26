package quemepongoAPI.guardarropa;

import lombok.Data;
import quemepongoAPI.atuendo.Atuendo;
import quemepongoAPI.atuendo.AtuendoBuilder;
import quemepongoAPI.atuendo.AtuendoClimaBuilder;
import quemepongoAPI.atuendo.AtuendoRandomBuilder;
import quemepongoAPI.clima.Clima;
import quemepongoAPI.clima.CondicionesClimaticas;
import quemepongoAPI.evento.Evento;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class Guardarropa {

    private @Id @GeneratedValue Long id;
    private String nombre;
    @ElementCollection
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Prenda> prendas;
    private int cantidad_maxima_prendas = 20; //20 Normal - Ilimitado Premium
    @Transient
    private AtuendoBuilder atuendoBuilder;
    @Transient
    private List<Prenda> ultimasPrendasPedidas = new ArrayList<>();
    @Transient
    private double ultimaTemperaturaPedida;
    @Transient
    private List<CondicionesClimaticas> ultimasCondicionesClimaticas;
    @Transient
    private boolean eventoFormal = false;
    @Transient
    private List<Atuendo> atuendosAceptados = new ArrayList<>();

    public Guardarropa() {}

    public Guardarropa(String nombre) {
        this.nombre = nombre;
        this.prendas = new ArrayList<>();
    }

    public Guardarropa(String nombre, List<Prenda> prendasList) {
        this.nombre = nombre;
        this.prendas = prendasList;
    }

    public Guardarropa(String nombre, boolean premium) {
        this.nombre = nombre;
        this.prendas = new ArrayList<>();
        if (premium) this.cantidad_maxima_prendas = Integer.MAX_VALUE;
    }

    public void PasarAPremium()
    {
        cantidad_maxima_prendas = Integer.MAX_VALUE;
    }

    public void agregarPrenda(Prenda prenda) {
        if((prendas.size() + 1) > cantidad_maxima_prendas)
            throw (new CantidadMaximaPrendaSuperadaException("No se puede agregar mas prendas"));
        prendas.add(prenda);
    }

    public boolean puedeAgregarPrenda()
    {
        if((prendas.size() + 1) > cantidad_maxima_prendas)
            return false;
        return true;
    }

    public void quitarPrenda(Prenda prenda){
        prendas.remove(prenda);
    }

    public Optional<Prenda> getPrenda(Long idPrenda) {
        return prendas.stream().filter(p -> p.getId().equals(idPrenda)).findFirst(); }

    public boolean existePrenda(Prenda prenda){
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
        constriurAtuendo(partes);

        return atuendoBuilder.dameAtuendo();
    }

    public Atuendo crearAtuendoClima(List<PartesCuerpo> listaPartes, Clima clima, Evento unEvento)
    {
        ultimaTemperaturaPedida = clima.getMostProximateTemperature();

        //TODO OPCIONAL: condiciones climaticas del adapter
        //ultimasCondicionesClimaticas = climaController.dameCondiciones();
        ultimasCondicionesClimaticas = new ArrayList<>();
        ultimasCondicionesClimaticas.add(CondicionesClimaticas.LLUVIA);
        ultimasCondicionesClimaticas.add(CondicionesClimaticas.VIENTO);

        eventoFormal = unEvento.getEsFormal();

        //relaciona temperatura con calor de las prendas
        int target = obtenerTarget();

        atuendoBuilder = new AtuendoClimaBuilder();
        constriurAtuendo(listaPartes);

        while(atuendoBuilder.calorAtuendo() < target)
        {
            if(hayPrendasParaEntregar(listaPartes, eventoFormal))
                atuendoBuilder.agregar_nueva_capa();
            else
                break;
            constriurAtuendoClima(listaPartes);
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
            atuendoBuilder.buildPorParte(this, unaParte);
        }
    }

    //CASO usuario pide una combinacion de partes
    private void constriurAtuendo(List<PartesCuerpo> partes)
    {
        atuendoBuilder.crearNuevoAtuendo();
        atuendoBuilder.setListaDePartes(partes);
        for (PartesCuerpo parte: partes)
        {
            atuendoBuilder.buildPorParte(this, parte);
        }
    }

    private void constriurAtuendoClima(List<PartesCuerpo> partes)
    {
        for (PartesCuerpo parte: partes)
        {
            atuendoBuilder.buildPorParte(this, parte);
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

    public Prenda darUnaPrendaParaClima(PartesCuerpo unaParte)
    {
        //lista con las prendas que son de la parte del cuerpo pedida
        List<Prenda> prendasResultado = new ArrayList<>();

        filtrarListaClima(prendasResultado, unaParte, eventoFormal);

        ordenarListaPorTemperatura(prendasResultado);

        actualizarCacheDePrendasPedidas(prendasResultado);

        return darOtraPrendaParaClima();
    }

    public Prenda darOtraPrendaParaClima()
    {
        int posicion;

        if(ultimaTemperaturaPedida >= 27) //TODO: temperatura templada por configuracion
            posicion = 0; //hace calor, por lo tanto saca la prenda mas fria
        else
            posicion = ultimasPrendasPedidas.size() - 1; //hace frio, por lo tanto saca la prenda mas calurosa

        try {
            return ultimasPrendasPedidas.remove(posicion);
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

            if(partesCuerpoDeLaPrenda.contains(unaParte) && !AtuendoAceptadoTienePrenda(unaPrenda))
                unaLista.add(unaPrenda);
        }
    }

    private void filtrarListaClima(List<Prenda> unaLista, PartesCuerpo unaParte, boolean formal)
    {
        //filtra de todas las prendas en el guardarropas y las agrega en la lista nueva
        for (Prenda unaPrenda : prendas) {
            List<PartesCuerpo> partesCuerpoDeLaPrenda = unaPrenda.damePartesCuerpo();

            if (partesCuerpoDeLaPrenda.contains(unaParte)
                    && unaPrenda.compatibleConCondicion(ultimasCondicionesClimaticas)
                    && !AtuendoAceptadoTienePrenda(unaPrenda))
            {
                if(formal && unaPrenda.getEsFormal())
                    unaLista.add(unaPrenda);
                else if (!formal)
                    unaLista.add(unaPrenda);
            }
        }
    }

    private void ordenarListaPorTemperatura(List<Prenda> unaLista)
    {
        Collections.sort(unaLista);
    }

    private boolean hayPrendasParaEntregar(List<PartesCuerpo> listaPartes, boolean formal)
    {
        int cantPrendasPosibles = 0;

        for (Prenda unaPrenda : prendas) {
            if(unaPrenda.perteneceA(listaPartes) && unaPrenda.compatibleConCondicion(ultimasCondicionesClimaticas))
            {
                if(formal && unaPrenda.getEsFormal())
                    cantPrendasPosibles++;
                if(!formal)
                    cantPrendasPosibles++;
            }
        }

        return cantPrendasPosibles > atuendoBuilder.cant_prendas();
    }

    private int obtenerTarget()
    {
        //27 grados centigrados es templado, debe ser por configuracion
        if(ultimaTemperaturaPedida >= 27){
            //hace calor, el target debe ser lo mas bajo posible
            return 1;
        } else {
            //hace frio, el target debe ser lo mas aproximado
            return Math.abs(27-(int)ultimaTemperaturaPedida) * 10;
        }
    }

    //Para GUADARROPAS COMPARTIDOS: si un usuario acepta un atuendo,
    // los otros usuarios no pueden tener las mismas prendas
    public void AceptarAtuendo(Atuendo unAtuendo)
    {
        atuendosAceptados.add(unAtuendo);
    }

    public boolean AtuendoAceptadoTienePrenda(Prenda unaPrenda)
    {
        for (Atuendo atuendo : atuendosAceptados) {
            if (atuendo.tiene_prenda(unaPrenda))
                return true;
        }
        return false;
    }

    //Para Tests
    public int CantPrendas()
    {
        return prendas.size();
    }
}