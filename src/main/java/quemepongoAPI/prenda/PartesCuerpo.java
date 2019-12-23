package quemepongoAPI.prenda;

public enum PartesCuerpo {

    CABEZA,
    TORSO,
    PIERNAS,
    CALZADO,
    ACCESORIO_MUNECA_DER,
    ACCESORIO_MUNECA_IZQ,
    ACCESORIO_TOBILLO_DER,
    ACCESORIO_TOBILLO_IZQ,
    OREJAS,
    OJOS,
    CUELLO;

//NO AGREGAR COMBINACIONES//

    public static PartesCuerpo fromInt(final int index){
        return PartesCuerpo.values()[index];
    }

}