package quemepongoAPI.atuendo;

import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;

import java.util.ArrayList;
import java.util.List;

public class AtuendoClimaBuilder extends AtuendoBuilder {
    private List<PartesCuerpo> partesOcupadas = new ArrayList<>();
    private List<PartesCuerpo> partesAOcupar = new ArrayList<>();
    private int capaActual = 0;

    @Override
    public void buildPorParte(Guardarropa unGuardarropa, PartesCuerpo unaParte, String clima)
    {
        /*Machete:
            1)usuario ingresa fecha del evento al guardarropa
            2)builder pide una prenda al guardarropa
            3)guardarropa ordena una lista de prendas en base al clima y devuelve el primero*/

        //la parte requerida ya esta ocupada
        if(partesOcupadas.contains(unaParte)) return;

        //pedir una prenda al guardarropa
        Prenda unaPrenda = unGuardarropa.darUnaPrendaParaClima(unaParte, clima);

        //TODO: la prenda obtenida ya fue agregada

        //verificar que las partes que la prenda ocupa no esten ocupados o que no sea requerida por usuario
        while((unaPrenda != null) && (!verificarEspacios(unaPrenda)))
        {
            unaPrenda = unGuardarropa.darOtraPrendaAleatoria();
        }

        //si no hay prendas disponibles
        if(unaPrenda == null)
        {
            if(capaActual == 0)
                    throw new AtuendoIncompletoException();
        } else {
            //agregar la prenda al SET, la parte del cuerpo a Pedida y Ocupada
            atuendo.add_prenda(unaPrenda);
            ocuparEspacios(unaPrenda);
        }
    }

    @Override
    public void setListaDePartes(List<PartesCuerpo> partes)
    {
        partesAOcupar = partes;
    }

    public void agregar_nueva_capa()
    {
        capaActual++;
        partesOcupadas.clear();
    }
}
