package quemepongoAPI.atuendo;

import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;

import java.util.List;

public class AtuendoRandomBuilder extends AtuendoBuilder
{
    public void buildTorso(Guardarropa unGuardarropa)
    {
        //verificar que la prenda ocupe solamente el torso
        Prenda unaPrenda = unGuardarropa.darUnaPrendaAleatoria(PartesCuerpo.TORSO);

        if(unaPrenda == null) return;

        if(unaPrenda.tieneMasDeUnaParte())
        {
            ocuparEspacios(unaPrenda);
        }

        atuendo.set_prendaTorso(unaPrenda);
    }

    public void buildPiernas(Guardarropa unGuardarropa)
    {
        //verificar que el espacio no este ocupado
        if(atuendo.get_prendaPiernas() != null) { return; }

        Prenda unaPrenda = unGuardarropa.darUnaPrendaAleatoria(PartesCuerpo.PIERNAS);

        if(unaPrenda == null) return;

        //verificar los demas espacios que ocuparia la prenda
        if(!verificarEspacios(unaPrenda)); //TODO pedir otra prenda

        if(unaPrenda.tieneMasDeUnaParte())
        {
            ocuparEspacios(unaPrenda);
        }

        atuendo.set_prendaPiernas(unaPrenda);
    }

    public void buildCabeza(Guardarropa unGuardarropa)
    {
        //verificar que el espacio no este ocupado
        if(atuendo.get_prendaCabeza() != null) { return; }

        Prenda unaPrenda = unGuardarropa.darUnaPrendaAleatoria(PartesCuerpo.CABEZA);

        if(unaPrenda == null) return;

        //verificar los demas espacios que ocuparia la prenda
        if(!verificarEspacios(unaPrenda)); //TODO pedir otra prenda

        if(unaPrenda.tieneMasDeUnaParte())
        {
            ocuparEspacios(unaPrenda);
        }

        atuendo.set_prendaCabeza(unaPrenda);
    }

    public void buildCalzado(Guardarropa unGuardarropa)
    {
        //verificar que los espacios no esten ocupados
        if(atuendo.get_prendaCalzado() != null) { return; }

        Prenda unaPrenda = unGuardarropa.darUnaPrendaAleatoria(PartesCuerpo.CALZADO);

        if(unaPrenda == null) return;

        //verificar los demas espacios que ocuparia la prenda
        if(!verificarEspacios(unaPrenda)); //TODO pedir otra prenda

        if(unaPrenda.tieneMasDeUnaParte())
        {
            ocuparEspacios(unaPrenda);
        }

        atuendo.set_prendaCalzado(unaPrenda);
    }

    //TODO: accesorios opcionales?

    private boolean verificarEspacios(Prenda unaPrenda)
    {
        List<PartesCuerpo> listaPartes = unaPrenda.damePartesCuerpo();

        for (PartesCuerpo parte: listaPartes)
        {
            switch (parte)
            {
                case CABEZA: {
                    if(atuendo.get_prendaCabeza() != null) return false;
                    break;
                }
                case TORSO: {
                    if(atuendo.get_prendaTorso() != null) return false;
                    break;
                }
                case PIERNAS: {
                    if (atuendo.get_prendaPiernas() != null) return false;
                    break;
                }
                case CALZADO: {
                    if(atuendo.get_prendaCalzado() != null) return false;
                    break;
                }
            }
        }
        return true;
    }

    private void ocuparEspacios(Prenda unaPrenda)
    {
        List<PartesCuerpo> listaPartes = unaPrenda.damePartesCuerpo();

        for (PartesCuerpo parte: listaPartes)
        {
            switch (parte)
            {
                case CABEZA: {
                    atuendo.set_prendaCabeza(unaPrenda);
                    break;
                }
                case TORSO: {
                    atuendo.set_prendaTorso(unaPrenda);
                    break;
                }
                case PIERNAS: {
                    atuendo.set_prendaPiernas(unaPrenda);
                    break;
                }
                case CALZADO: {
                    atuendo.set_prendaCalzado(unaPrenda);
                    break;
                }
            }
        }
    }
}
