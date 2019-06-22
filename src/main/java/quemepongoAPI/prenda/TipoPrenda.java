package quemepongoAPI.prenda;

import lombok.Data;

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

    public TipoPrenda(String nombre, List<Tela> telasPosibles, List<PartesCuerpo> partesCuerpo, int calor)
    {
        this.nombre = nombre;
        this.telasPosibles = telasPosibles;
        this.partesCuerpo = partesCuerpo;
        Collections.sort(this.partesCuerpo);
        this.calor = calor;
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

}
