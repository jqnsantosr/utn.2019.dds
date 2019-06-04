package quemepongoAPI.atuendo;

import quemepongoAPI.guardarropa.Guardarropa;

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

    public abstract void buildTorso(Guardarropa unGuardarropa);
    public abstract void buildPiernas(Guardarropa unGuardarropa);
    public abstract void buildCabeza(Guardarropa unGuardarropa);
    public abstract void buildCalzado(Guardarropa unGuardarropa);
}
