package quemepongoAPI.atuendo;

import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;

import java.util.ArrayList;
import java.util.List;

public class AtuendoClimaBuilder extends AtuendoBuilder {
    private List<PartesCuerpo> partesAOcupar = new ArrayList<>();
    private int capaActual = 0;

    @Override
    public void buildPorParte(Guardarropa unGuardarropa, PartesCuerpo unaParte)
    {
        /*Machete:
            1)usuario ingresa fecha del evento al guardarropa
            2)builder pide una prenda al guardarropa
            3)guardarropa ordena una lista de prendas en base al clima y devuelve el primero*/

        //pedir una prenda al guardarropa
        Prenda unaPrenda = unGuardarropa.darUnaPrendaParaClima(unaParte);

        //verificar que las partes que la prenda ocupa no esten ocupados o que no sea requerida por usuario
        while((unaPrenda != null) && (!verificarEspacios(unaPrenda)))
        {
            unaPrenda = unGuardarropa.darOtraPrendaParaClima();
        }

        //si no hay prendas disponibles
        if(unaPrenda == null)
        {
            if(capaActual == 0 && !partesOcupadas.contains(unaParte))
                    throw new AtuendoIncompletoException();
        } else {
            //agregar la prenda al SET
            atuendo.agregar_prenda(unaPrenda);
            ocuparEspacios(unaPrenda);
        }
    }

    @Override
    public void setListaDePartes(List<PartesCuerpo> partes)
    {
        partesAOcupar = partes;
    }

    @Override
    public boolean verificarEspacios(Prenda unaPrenda) {
        //no importa si la parte esta ocupada por otra prenda, solo si es requerida por usuario
        List<PartesCuerpo> listaPartes = unaPrenda.damePartesCuerpo();

        for (PartesCuerpo parte: listaPartes)
        {
            if(!partesAOcupar.contains(parte) || atuendo.tiene_prenda(unaPrenda))
                return false;
        }

        return true;
    }

    @Override
    public void agregar_nueva_capa() {
        capaActual++;
    }
}
