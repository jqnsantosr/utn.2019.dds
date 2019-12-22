package quemepongoAPI.prenda;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import lombok.Data;
import quemepongoAPI.clima.CondicionesClimaticas;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class Prenda implements Comparable<Prenda>{

    private @Id
    @GeneratedValue
    Long id;
    private String nombre;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn (name = "idTipoPrenda")
    private TipoPrenda tipo;
    private Tela tela;
    @ElementCollection
    private List<PartesCuerpo> partes;
    private String colorPrimario;
    private String colorSecundario;
    private boolean esFormal = false;

    //public Prenda(){}

    public Prenda(final JsonNode jn, final TipoPrenda tipo){ //para no usar Jackson autobinding
        this.nombre = jn.get("nombre").asText();
        this.colorPrimario = jn.get("colorPrimario").asText();
        this.colorSecundario = jn.get("colorSecundario").asText();
        this.esFormal = jn.get("esFormal").asBoolean();
        this.tipo = tipo;
        this.partes = new ArrayList<>();
        this.tela = Tela.fromInt(jn.get("tela").asInt());
    }

    public Prenda(String nombre, Tela tela, List<PartesCuerpo> partes, TipoPrenda tipo, String colorPrimario) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.colorPrimario = colorPrimario;
        this.tela = tela;
        this.partes = partes;

        Collections.sort(this.partes);
        if(!tipo.getTelasPosibles().contains(tela) || !(tipo.getPartesCuerpo().equals(partes))) throw (new PrendaIncoherenteException());
    }

    public Prenda(String nombre, Tela tela, TipoPrenda tipo, String colorPrimario, String colorSecundario){
        this.nombre = nombre;
        this.tipo = tipo;
        this.colorPrimario = colorPrimario;
        this.colorSecundario = colorSecundario;
    }

    public List<PartesCuerpo> damePartesCuerpo()
    {
        return this.tipo.damePartesDelCuerpo();
    }

    public boolean tieneMasDeUnaParte()
    {
        return this.tipo.cantidadPartes() > 1;
    }

    public boolean perteneceA(List<PartesCuerpo> listaPartes)
    {
        boolean pertenece = true;
        List<PartesCuerpo> partesDeLaPrenda = tipo.damePartesDelCuerpo();

        for (PartesCuerpo parte : partesDeLaPrenda) {
            if(!listaPartes.contains(parte)) pertenece = false;
        }

        return pertenece;
    }

    public boolean compatibleConCondicion(List<CondicionesClimaticas> condiciones)
    {
        return tipo.compatibleConCondicion(condiciones);
    }

    @Override
    public int compareTo(Prenda otraPrenda) {
        return this.tipo.factorCalor() - otraPrenda.tipo.factorCalor();
    }

    public boolean getEsFormal()
    {
        return esFormal;
    }

    public void setEsFormal(boolean value)
    {
        esFormal = value;
    }
}
