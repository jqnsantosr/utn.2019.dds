package quemepongoAPI.atuendo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;
import quemepongoAPI.prenda.TipoPrenda;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AtuendoBuilderAleatorioTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void crearNuevoAtuendoBasico() {
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

        assertTrue(unAtuendo.tiene_prenda(gorraBlanca));
        assertTrue(unAtuendo.tiene_prenda(remeraAzul));
        assertTrue(unAtuendo.tiene_prenda(jeanNegro));
        assertTrue(unAtuendo.tiene_prenda(nikesAmarillas));
    }

    @Test
    void crearNuevoAtuendoConPrendasDeMultiplesPartes() {
        Guardarropa unGuardarropa = new Guardarropa("TEST");

        TipoPrenda vestido = new TipoPrenda("Vestido");
        vestido.agregarParteCuerpo(PartesCuerpo.TORSO);
        vestido.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        Prenda vestidoRosa = new Prenda("Vestido Rosa", vestido, "Rosa");

        TipoPrenda pollera = new TipoPrenda("Pollera");
        pollera.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        Prenda polleraVioleta = new Prenda("Pollera Violeta", pollera, "Violeta");

        TipoPrenda sombrero = new TipoPrenda("Sombrero");
        sombrero.agregarParteCuerpo(PartesCuerpo.CABEZA);
        Prenda sombreroGrande = new Prenda("Sombrero Grande", sombrero, "Blanca");

        TipoPrenda zapato = new TipoPrenda("Zapato");
        zapato.agregarParteCuerpo(PartesCuerpo.CALZADO);
        Prenda zapatosConTacosYPlataforma = new Prenda("Tacos con Plataforma", zapato, "Negro");

        unGuardarropa.addPrenda(vestidoRosa);
        unGuardarropa.addPrenda(polleraVioleta);
        unGuardarropa.addPrenda(sombreroGrande);
        unGuardarropa.addPrenda(zapatosConTacosYPlataforma);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoAleatorio();

        assertTrue(unAtuendo.tiene_prenda(vestidoRosa));
        assertFalse(unAtuendo.tiene_prenda(polleraVioleta));
        assertTrue(unAtuendo.tiene_prenda(sombreroGrande));
        assertTrue(unAtuendo.tiene_prenda(zapatosConTacosYPlataforma));
    }

    @Test
    void crearNuevoAtuendoEspecifico() {
        Guardarropa unGuardarropa = new Guardarropa("TEST");

        TipoPrenda traje_de_banio = new TipoPrenda("Traje de Baño");
        traje_de_banio.agregarParteCuerpo(PartesCuerpo.TORSO);
        traje_de_banio.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        Prenda bikiniRoja = new Prenda("Bikini Roja", traje_de_banio, "Roja");

        TipoPrenda sombrero = new TipoPrenda("Sombrero");
        sombrero.agregarParteCuerpo(PartesCuerpo.CABEZA);
        Prenda sombreroVaquero = new Prenda("Sombrero de Vaquero", sombrero, "Marron");

        TipoPrenda zapato = new TipoPrenda("Zapato");
        zapato.agregarParteCuerpo(PartesCuerpo.CALZADO);
        Prenda zapatosConTacosYPlataforma = new Prenda("Tacos con Plataforma", zapato, "Negro");

        TipoPrenda anteojos = new TipoPrenda("Anteojos");
        anteojos.agregarParteCuerpo(PartesCuerpo.OJOS);
        Prenda anteojosSol = new Prenda("Anteojos de Sol", anteojos, "Negro");

        unGuardarropa.addPrenda(bikiniRoja);
        unGuardarropa.addPrenda(sombreroVaquero);
        unGuardarropa.addPrenda(zapatosConTacosYPlataforma);
        unGuardarropa.addPrenda(anteojosSol);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CABEZA);
        partesPedidas.add(PartesCuerpo.OJOS);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoAleatorio(partesPedidas);

        assertTrue(unAtuendo.tiene_prenda(bikiniRoja));
        assertTrue(unAtuendo.tiene_prenda(sombreroVaquero));
        assertFalse(unAtuendo.tiene_prenda(zapatosConTacosYPlataforma));
        assertTrue(unAtuendo.tiene_prenda(anteojosSol));
    }

    @Test
    void crearNuevoAtuendoConPartesQueNoEntranPorEstarOcupadas() {
        Guardarropa unGuardarropa = new Guardarropa("TEST");

        TipoPrenda traje_entero = new TipoPrenda("Traje Entero");
        traje_entero.agregarParteCuerpo(PartesCuerpo.TORSO);
        traje_entero.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        traje_entero.agregarParteCuerpo(PartesCuerpo.CALZADO);
        traje_entero.agregarParteCuerpo(PartesCuerpo.CABEZA);
        Prenda trajeDeBatman = new Prenda("Traje De Batman", traje_entero, "Negro");

        TipoPrenda pantalon = new TipoPrenda("Pantalon");
        pantalon.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        Prenda pantalon_thor = new Prenda("Pantalon de Thor", pantalon, "Negro");
        Prenda pantalon_flash = new Prenda("Pantalon de Flash", pantalon, "Rojo");

        TipoPrenda sombrero = new TipoPrenda("Sombrero");
        sombrero.agregarParteCuerpo(PartesCuerpo.CABEZA);
        Prenda casco_ironman = new Prenda("Casco Iron Man", sombrero, "Rojo");

        TipoPrenda zapato = new TipoPrenda("Zapato");
        zapato.agregarParteCuerpo(PartesCuerpo.CALZADO);
        Prenda botas_cpt_america = new Prenda("Botas de Capitan America", zapato, "Blanco?");

        unGuardarropa.addPrenda(trajeDeBatman);
        unGuardarropa.addPrenda(pantalon_thor);
        unGuardarropa.addPrenda(pantalon_flash);
        unGuardarropa.addPrenda(casco_ironman);
        unGuardarropa.addPrenda(botas_cpt_america);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoAleatorio();

        assertTrue(unAtuendo.tiene_prenda(trajeDeBatman));
        assertFalse(unAtuendo.tiene_prenda(pantalon_thor));
        assertFalse(unAtuendo.tiene_prenda(pantalon_flash));
        assertFalse(unAtuendo.tiene_prenda(casco_ironman));
        assertFalse(unAtuendo.tiene_prenda(botas_cpt_america));
    }

    @Test
    void crearNuevoAtuendoTiraExceptionPorEstarIncompleto()
    {
        Guardarropa unGuardarropa = new Guardarropa("TEST");

        assertThrows(AtuendoIncompletoException.class, () -> unGuardarropa.crearAtuendoAleatorio());
    }
}