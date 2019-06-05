package quemepongoAPI.atuendo;

import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;

import java.util.List;

public abstract class AtuendoBuilder
{
    protected Atuendo atuendo;

    public Atuendo dameAtuendo()
    {
        return atuendo;
    }

    public void crearNuevoAtuendo() {
        atuendo = new Atuendo();
    }

    public abstract void buildPorParte(Guardarropa unGuardarropa, PartesCuerpo unaParte);
    public abstract void setListaDePartes(List<PartesCuerpo> partes);

}
