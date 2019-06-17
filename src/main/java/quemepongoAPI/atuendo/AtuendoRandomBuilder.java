package quemepongoAPI.atuendo;

import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;

import java.util.ArrayList;
import java.util.List;

public class AtuendoRandomBuilder extends AtuendoBuilder
{
    @Override
    public void buildPorParte(Guardarropa unGuardarropa, PartesCuerpo unaParte, String clima)
    {
        //la parte requerida ya esta ocupada
        if(partesOcupadas.contains(unaParte)) return;

        //pedir una prenda al guardarropa
        Prenda unaPrenda = unGuardarropa.darUnaPrendaAleatoria(unaParte);

        //verificar que las partes que la prenda ocupa no esten ocupados o que no sea requerida por usuario
        while((unaPrenda != null) && (!verificarEspacios(unaPrenda)))
        {
            unaPrenda = unGuardarropa.darOtraPrendaAleatoria();
        }

        //si no hay prendas disponibles
        if(unaPrenda == null)
        {
            throw new AtuendoIncompletoException();
        }

        //agregar la prenda al SET, la parte del cuerpo a Pedida y Ocupada
        atuendo.add_prenda(unaPrenda);
        ocuparEspacios(unaPrenda);
    }

    @Override
    public void setListaDePartes(List<PartesCuerpo> partes)
    {
        partesAOcupar = partes;
    }

    @Override
    public void agregar_nueva_capa()
    {

    }
}
