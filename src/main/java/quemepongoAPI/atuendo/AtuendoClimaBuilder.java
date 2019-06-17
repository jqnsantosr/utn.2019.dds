package quemepongoAPI.atuendo;

import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;

import java.util.ArrayList;
import java.util.List;

public class AtuendoClimaBuilder extends AtuendoBuilder {
    private List<PartesCuerpo> partesOcupadas = new ArrayList<>();
    private List<PartesCuerpo> partesAOcupar = new ArrayList<>();

    @Override
    public void buildPorParte(Guardarropa unGuardarropa, PartesCuerpo unaParte)
    {
        /*Machete:
            1)usuario ingresa fecha del evento al guardarropa
            2)builder pide una prenda al guardarropa en base al clima
            3)guardarropa ordena una lista de prendas y devuelve el primero

          Problemas:
            1)quien recoge la info del clima?
            2)quien se encarga de controlar el nivel de calor del atuendo?
            3)como se manejan las prendas superpuestas?
        */
    }

    @Override
    public void setListaDePartes(List<PartesCuerpo> partes)
    {
        partesAOcupar = partes;
    }
}
