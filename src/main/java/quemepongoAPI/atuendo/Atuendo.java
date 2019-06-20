package quemepongoAPI.atuendo;

import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Atuendo
{
    private Set<Prenda> prendasElegidas = new HashSet<>();
    private List<PartesCuerpo> partesCuerpoACubrir = new ArrayList<>();

    public void mostrarAtuendo()
    {
        String prendas = "";

        for (Prenda prenda : prendasElegidas) {
             prendas += prenda.getNombre() + ", ";
        }
        System.out.println("Prendas del Atuendo: " + prendas);
    }

    public int calor()
    {
        return prendasElegidas.size() * 10; //TODO: calor es individual de cada prenda
    }

    public void add_prenda(Prenda unaPrenda)
    {
        prendasElegidas.add(unaPrenda);
    }

    public void add_parte_del_cuerpo(PartesCuerpo unaParte)
    {
        partesCuerpoACubrir.add(unaParte);
    }

    public int cant_prendas()
    {
        return prendasElegidas.size();
    }

    //-- Metodos para Tests --
    public boolean tiene_prenda(Prenda unaPrenda)
    {
        return prendasElegidas.contains(unaPrenda);
    }

    public String partes_del_cuerpo_ocupadas()
    {
        String partesOcupadas = "";
        for (PartesCuerpo parte : partesCuerpoACubrir)
        {
            partesOcupadas += parte.toString() + " ";
        }
        return partesOcupadas;
    }
}
