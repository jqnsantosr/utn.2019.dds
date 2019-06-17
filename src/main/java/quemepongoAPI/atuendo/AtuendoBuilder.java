package quemepongoAPI.atuendo;

import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;

import java.util.ArrayList;
import java.util.List;

public abstract class AtuendoBuilder
{
    protected Atuendo atuendo;

    public List<PartesCuerpo> partesOcupadas = new ArrayList<>();
    public List<PartesCuerpo> partesAOcupar = new ArrayList<>();

    public Atuendo dameAtuendo()
    {
        return atuendo;
    }

    public void crearNuevoAtuendo() {
        atuendo = new Atuendo();
    }

    public abstract void buildPorParte(Guardarropa unGuardarropa, PartesCuerpo unaParte, String clima);
    public abstract void setListaDePartes(List<PartesCuerpo> partes);
    public abstract void agregar_nueva_capa();

    public boolean verificarEspacios(Prenda unaPrenda)
    {
        List<PartesCuerpo> listaPartes = unaPrenda.damePartesCuerpo();

        for (PartesCuerpo parte: listaPartes)
        {
            if(partesOcupadas.contains(parte) || !partesAOcupar.contains(parte)) return false;
        }

        return true;
    }

    public void ocuparEspacios(Prenda unaPrenda)
    {
        List<PartesCuerpo> listaPartes = unaPrenda.damePartesCuerpo();

        for (PartesCuerpo parte: listaPartes)
        {
            atuendo.add_parte_del_cuerpo(parte);
            partesOcupadas.add(parte);
        }
    }

    public int calorAtuendo()
    {
        return atuendo.calor();
    }
}
