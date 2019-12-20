package quemepongoAPI.prenda;

public enum Tela {

    ALGODON,
    LYCRA,
    SATEN,
    MODAL,
    SEDA,
    TUL,
    BATISTA,
    FRISELINA,
    VOILE,
    CUERO,
    PIQUE,
    CORDEROY,
    GABARDINA,
    ACROCEL,
    CUERINA,
    SINTETICA,
    POLIESTER,
    RASO,
    CORDERITO,
    JEAN, /* DENIM */
    LANILLA,
    LONA,
    PANA;

    public static Tela fromInt(final int index){
        return Tela.values()[index];
    }

}
