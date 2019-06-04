package quemepongoAPI.atuendo;

import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;
import quemepongoAPI.prenda.TipoPrenda;

import static org.junit.jupiter.api.Assertions.*;

class AtuendoBuilderTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

    }

    @org.junit.jupiter.api.Test
    void crearNuevoAtuendo() {
        Guardarropa unGuardarropa = new Guardarropa("TEST");
        TipoPrenda remera = new TipoPrenda("Remera");
        remera.agregarParteCuerpo(PartesCuerpo.TORSO);
        Prenda remeraAzul = new Prenda("Remera Azul", remera, "Azul");

        TipoPrenda pantalon = new TipoPrenda("Pantalon");
        pantalon.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        Prenda jeanNegro = new Prenda("Jean Negro", pantalon, "Negro");

        TipoPrenda gorra = new TipoPrenda("Gorra");
        gorra.agregarParteCuerpo(PartesCuerpo.CABEZA);
        Prenda gorraBlanca = new Prenda("Gorra Blanca", gorra, "Blanca");

        TipoPrenda zapatilla = new TipoPrenda("Zapatilla");
        zapatilla.agregarParteCuerpo(PartesCuerpo.CALZADO);
        Prenda nikesAmarillas = new Prenda("Nikes Amarillas", zapatilla, "Amarillas");

        unGuardarropa.addPrenda(remeraAzul);
        unGuardarropa.addPrenda(jeanNegro);
        unGuardarropa.addPrenda(gorraBlanca);
        unGuardarropa.addPrenda(nikesAmarillas);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoAleatorio();

        assertEquals(gorraBlanca, unAtuendo.get_prendaCabeza());
        assertEquals(remeraAzul, unAtuendo.get_prendaTorso());
        assertEquals(jeanNegro, unAtuendo.get_prendaPiernas());
        assertEquals(nikesAmarillas, unAtuendo.get_prendaCalzado());
    }

    @org.junit.jupiter.api.Test
    void crearNuevoAtuendoConPrendasDeMultiplesPartes() {
        Guardarropa unGuardarropa = new Guardarropa("TEST");
        TipoPrenda vestido = new TipoPrenda("Vestido");
        vestido.agregarParteCuerpo(PartesCuerpo.TORSO);
        vestido.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        Prenda vestidoRosa = new Prenda("Vestido Rosa", vestido, "Rosa");

        TipoPrenda pollera = new TipoPrenda("Pollera");
        pollera.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        Prenda polleraVioleta = new Prenda("Pollera Violeta", pollera, "Violeta");

        TipoPrenda zapato = new TipoPrenda("Zapato");
        zapato.agregarParteCuerpo(PartesCuerpo.CALZADO);
        Prenda zapatosConTacosYPlataforma = new Prenda("Tacos con Plataforma", zapato, "Negro");

        unGuardarropa.addPrenda(vestidoRosa);
        unGuardarropa.addPrenda(polleraVioleta);
        unGuardarropa.addPrenda(zapatosConTacosYPlataforma);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoAleatorio();

        assertEquals(vestidoRosa, unAtuendo.get_prendaTorso());
        assertEquals(vestidoRosa, unAtuendo.get_prendaPiernas());
        assertNull(unAtuendo.get_prendaCabeza());
        assertEquals(zapatosConTacosYPlataforma, unAtuendo.get_prendaCalzado());
    }
}