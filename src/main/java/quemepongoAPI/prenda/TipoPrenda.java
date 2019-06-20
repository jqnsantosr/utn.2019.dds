package quemepongoAPI.prenda;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.*;

@Data
@Entity
public class TipoPrenda {

    private @Id @GeneratedValue Long id;
    private String nombre;
    @ElementCollection
    private List<Tela> telasPosibles;
    @ElementCollection
    private List<PartesCuerpo> partesCuerpo;
    private int calor;

    public TipoPrenda(String nombre)
    {
        this.nombre = nombre;
        telasPosibles = new ArrayList<>();
        partesCuerpo = new ArrayList<>();
    }

    public void agregarTelaPosible(Tela unaTela)
    {
        telasPosibles.add(unaTela);
    }

    public void agregarParteCuerpo(PartesCuerpo unaParte)
    {
        partesCuerpo.add(unaParte);
    }

    public List<PartesCuerpo> damePartesDelCuerpo()
    {
        return partesCuerpo;
    }

    public int cantidadPartes()
    {
        return partesCuerpo.size();
    }

    public int factorCalor()
    {
        return calor;
    }

    public void setCalor(int unCalor)
    {
        calor = unCalor;
    }
}
