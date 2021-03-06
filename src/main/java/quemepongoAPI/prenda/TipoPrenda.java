package quemepongoAPI.prenda;

import lombok.Data;
import quemepongoAPI.clima.CondicionesClimaticas;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class TipoPrenda {

    private @Id @GeneratedValue Long idTipoPrenda;
    private String nombre;
    @ElementCollection
    private List<Tela> telasPosibles;
    @ElementCollection
    private List<PartesCuerpo> partesCuerpo;
    private int calor;
    @ElementCollection
    private List<CondicionesClimaticas> incompatible;
    @ElementCollection
    private List<CondicionesClimaticas> especialidad;

    public TipoPrenda(String nombre, List<Tela> telasPosibles, List<PartesCuerpo> partesCuerpo, int calor)
    {
        this.nombre = nombre;
        this.telasPosibles = telasPosibles;
        this.partesCuerpo = partesCuerpo;
        Collections.sort(this.partesCuerpo);
        this.calor = calor;
        this.incompatible = new ArrayList<>();
    }

    //TODO: poner arriba
    public void agregarIncompatibilidad(CondicionesClimaticas unaCondicion)
    {
        this.incompatible.add(unaCondicion);
    }

    List<PartesCuerpo> damePartesDelCuerpo()
    {
        return partesCuerpo;
    }

    int cantidadPartes()
    {
        return partesCuerpo.size();
    }

    public int factorCalor()
    {
        return calor;
    }

    public boolean compatibleConCondicion(List<CondicionesClimaticas> condiciones)
    {
        for (CondicionesClimaticas condicion : condiciones) {
            if(incompatible.contains(condicion)) return false;
        }

        return true;
    }
}
