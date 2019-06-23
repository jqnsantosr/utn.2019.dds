package quemepongoAPI.atuendo;

import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;

import java.util.ArrayList;
import java.util.List;

public abstract class AtuendoBuilder
{
    protected Atuendo atuendo;

    List<PartesCuerpo> partesOcupadas = new ArrayList<>();
    List<PartesCuerpo> partesAOcupar = new ArrayList<>();

    public Atuendo dameAtuendo()
    {
        return atuendo;
    }

    public void crearNuevoAtuendo() {
        atuendo = new Atuendo();
    }

    public abstract void buildPorParte(Guardarropa unGuardarropa, PartesCuerpo unaParte);
    public abstract void setListaDePartes(List<PartesCuerpo> partes);
    public abstract boolean verificarEspacios(Prenda unaPrenda);
    public abstract void agregar_nueva_capa();

    void ocuparEspacios(Prenda unaPrenda)
    {
        List<PartesCuerpo> listaPartes = unaPrenda.damePartesCuerpo();

        for (PartesCuerpo parte: listaPartes)
        {
            atuendo.agregar_parte_del_cuerpo(parte);
            partesOcupadas.add(parte);
        }
    }

    public int calorAtuendo()
    {
        return atuendo.calor();
    }

    public int cant_prendas(){
        return atuendo.cant_prendas();
    }
}
