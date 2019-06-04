package quemepongoAPI.atuendo;

import quemepongoAPI.prenda.Prenda;

public class Atuendo
{
    private Prenda prendaTorso;
    private Prenda prendaPiernas;
    private Prenda prendaCabeza;
    private Prenda prendaCalzado;

    public void mostrarAtuendo()
    {
        //TODO
    }

    public Prenda get_prendaTorso()
    {
        return prendaTorso;
    }

    public void set_prendaTorso(Prenda unaPrenda)
    {
        this.prendaTorso = unaPrenda;
    }

    public Prenda get_prendaPiernas()
    {
        return prendaPiernas;
    }

    public void set_prendaPiernas(Prenda prendaPiernas)
    {
        this.prendaPiernas = prendaPiernas;
    }

    public Prenda get_prendaCabeza()
    {
        return prendaCabeza;
    }

    public void set_prendaCabeza(Prenda prendaCabeza)
    {
        this.prendaCabeza = prendaCabeza;
    }

    public Prenda get_prendaCalzado() {
        return prendaCalzado;
    }

    public void set_prendaCalzado(Prenda prendaCalzado) {
        this.prendaCalzado = prendaCalzado;
    }
}
