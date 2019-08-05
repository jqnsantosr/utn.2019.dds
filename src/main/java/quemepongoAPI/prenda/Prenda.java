package quemepongoAPI.prenda;

import lombok.Data;
import quemepongoAPI.clima.CondicionesClimaticas;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    Prenda(){}

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

    public boolean incompatibleConCondicion(List<CondicionesClimaticas> condiciones)
    {
        return tipo.incompatibleConCondicion(condiciones);
    }

    @Override
    public int compareTo(Prenda otraPrenda) {
        return this.tipo.factorCalor() - otraPrenda.tipo.factorCalor();
    }
}
