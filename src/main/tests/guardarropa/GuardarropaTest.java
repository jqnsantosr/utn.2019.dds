package guardarropa;

import org.junit.jupiter.api.Test;
import quemepongoAPI.guardarropa.CantidadMaximaPrendaSuperadaException;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.Prenda;
import quemepongoAPI.prenda.TipoPrenda;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static quemepongoAPI.prenda.PartesCuerpo.*;
import static quemepongoAPI.prenda.PartesCuerpo.CALZADO;
import static quemepongoAPI.prenda.Tela.*;

class GuardarropaTest {

    private TipoPrenda remeraLiviana = new TipoPrenda("Remera", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 5);
    private TipoPrenda remeraPesada = new TipoPrenda("Remera", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 15);
    private TipoPrenda camisa = new TipoPrenda("Camisa", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 10);
    private TipoPrenda pantalonLiviano = new TipoPrenda("Pantalon", Collections.singletonList(JEAN), Collections.singletonList(PIERNAS), 10);
    private TipoPrenda pantalonPesado = new TipoPrenda("Pantalon", Collections.singletonList(JEAN), Collections.singletonList(PIERNAS), 15);
    private TipoPrenda gorraLiviana = new TipoPrenda("Gorra", Arrays.asList(ALGODON, POLIESTER), Collections.singletonList(CABEZA), 3);
    private TipoPrenda gorra = new TipoPrenda("Gorra", Arrays.asList(ALGODON, POLIESTER), Collections.singletonList(CABEZA), 7);
    private TipoPrenda zapatilla = new TipoPrenda("Zapatilla", Arrays.asList(CUERO, LONA, CUERINA), Collections.singletonList(CALZADO), 2);
    private TipoPrenda vestido = new TipoPrenda("Vestido", Arrays.asList(SEDA, LYCRA, ALGODON), Arrays.asList(TORSO, PIERNAS), 2);
    private TipoPrenda pollera = new TipoPrenda("Pollera", Arrays.asList(SEDA, LYCRA, ALGODON), Collections.singletonList(PIERNAS), 2);
    private TipoPrenda sombrero = new TipoPrenda("Sombrero", Arrays.asList(CUERO, PANA, LONA), Arrays.asList(CABEZA), 3);
    private TipoPrenda zapato = new TipoPrenda("Zapato", Arrays.asList(CUERO, CUERINA), Collections.singletonList(CALZADO), 4);
    private TipoPrenda anteojos = new TipoPrenda("Anteojos", Arrays.asList(SINTETICA), Collections.singletonList(OJOS), 0);
    private TipoPrenda traje_de_banio = new TipoPrenda("Traje de Baño", Arrays.asList(SINTETICA), Arrays.asList(TORSO, PIERNAS), 0);
    private TipoPrenda traje_entero = new TipoPrenda("Traje Entero", Arrays.asList(SINTETICA), Arrays.asList(TORSO, PIERNAS, CALZADO, CABEZA), 8);
    private TipoPrenda buzo = new TipoPrenda("Buzo", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 10);
    private TipoPrenda buzoFrisa = new TipoPrenda("Buzo", Arrays.asList(ALGODON, FRISELINA), Collections.singletonList(TORSO), 15);
    private TipoPrenda pantalonInvierno = new TipoPrenda("Pantalon de Invierno", Collections.singletonList(ALGODON), Collections.singletonList(PIERNAS), 15);
    private TipoPrenda campera = new TipoPrenda("Campera", Arrays.asList(ALGODON, SINTETICA), Collections.singletonList(TORSO), 20);
    private TipoPrenda pantalonCorto = new TipoPrenda("Pantalon Corto", Arrays.asList(JEAN, ALGODON, SINTETICA), Collections.singletonList(PIERNAS), 5);

    Prenda remeraAzul = new Prenda("Remera Azul", ALGODON, Collections.singletonList(TORSO), remeraLiviana, "Azul");
    Prenda jeanNegro = new Prenda("Jean Negro", JEAN, Collections.singletonList(PIERNAS), pantalonPesado, "Negro");
    Prenda gorraBlanca = new Prenda("Gorra Blanca", ALGODON, Collections.singletonList(CABEZA), gorra, "Blanca");
    Prenda nikesAmarillas = new Prenda("Nikes Amarillas", LONA, Collections.singletonList(CALZADO), zapatilla, "Amarillas");
    Prenda vestidoRosa = new Prenda("Vestido Rosa", LYCRA, Arrays.asList(TORSO, PIERNAS), vestido, "Rosa");
    Prenda polleraVioleta = new Prenda("Pollera Violeta", SEDA, Collections.singletonList(PIERNAS), pollera, "Violeta");
    Prenda sombreroGrande = new Prenda("Sombrero Grande", PANA, Collections.singletonList(CABEZA), sombrero, "Blanca");
    Prenda zapatosConTacosYPlataforma = new Prenda("Tacos con Plataforma", CUERO, Collections.singletonList(CALZADO), zapato, "Negro");
    Prenda pantalon_thor = new Prenda("Pantalon de Thor", JEAN, Collections.singletonList(PIERNAS), pantalonLiviano, "Negro");
    Prenda pantalon_flash = new Prenda("Pantalon de Flash", JEAN, Collections.singletonList(PIERNAS), pantalonLiviano, "Rojo");
    Prenda casco_ironman = new Prenda("Casco Iron Man", CUERO, Collections.singletonList(CABEZA), sombrero, "Rojo");
    Prenda bikiniRoja = new Prenda("Bikini Roja", SINTETICA, Arrays.asList(TORSO, PIERNAS), traje_de_banio, "Roja");
    Prenda sombreroVaquero = new Prenda("Sombrero de Vaquero", CUERO, Collections.singletonList(CABEZA), sombrero, "Marron");
    Prenda anteojosSol = new Prenda("Anteojos de Sol", SINTETICA, Arrays.asList(OJOS), anteojos, "Negro");
    Prenda trajeDeBatman = new Prenda("Traje De Batman", SINTETICA, Arrays.asList(CALZADO, TORSO, PIERNAS, CABEZA), traje_entero, "Negro");
    Prenda botas_cpt_america = new Prenda("Botas de Capitan America", CUERO, Collections.singletonList(CALZADO), zapato, "Blanco?");
    Prenda musculosaFloreada = new Prenda("Musculosa Floreada", ALGODON, Collections.singletonList(TORSO), remeraLiviana, "Multicolor");
    Prenda anteojosNocturnos = new Prenda("Anteojos de Noche", SINTETICA, Arrays.asList(OJOS), anteojos, "Verde");
    Prenda jeanCorto = new Prenda("Jean Corto", JEAN, Collections.singletonList(PIERNAS), pantalonCorto, "Blanco");
    Prenda camperaDeLluvia = new Prenda("Campera de LLuvia", SINTETICA, Collections.singletonList(TORSO), campera, "Azul");
    Prenda camisetaRacing = new Prenda("Camiseta de Rasin", ALGODON, Collections.singletonList(TORSO), remeraPesada, "No Existen");
    //21 prendas - un guardarropa normal tiene 20 como maximo

    @Test
    void agregarPrendaFallaPorSerGuardarropaNormal() throws CantidadMaximaPrendaSuperadaException {
        Guardarropa unGuardarropa = new Guardarropa("Guardarropa1", false);

        unGuardarropa.agregarPrenda(remeraAzul);
        unGuardarropa.agregarPrenda(jeanNegro);
        unGuardarropa.agregarPrenda(gorraBlanca);
        unGuardarropa.agregarPrenda(nikesAmarillas);
        unGuardarropa.agregarPrenda(vestidoRosa);
        unGuardarropa.agregarPrenda(polleraVioleta);
        unGuardarropa.agregarPrenda(sombreroGrande);
        unGuardarropa.agregarPrenda(zapatosConTacosYPlataforma);
        unGuardarropa.agregarPrenda(pantalon_thor);
        unGuardarropa.agregarPrenda(pantalon_flash);
        unGuardarropa.agregarPrenda(casco_ironman);
        unGuardarropa.agregarPrenda(bikiniRoja);
        unGuardarropa.agregarPrenda(sombreroVaquero);
        unGuardarropa.agregarPrenda(anteojosSol);
        unGuardarropa.agregarPrenda(trajeDeBatman);
        unGuardarropa.agregarPrenda(botas_cpt_america);
        unGuardarropa.agregarPrenda(musculosaFloreada);
        unGuardarropa.agregarPrenda(anteojosNocturnos);
        unGuardarropa.agregarPrenda(jeanCorto);
        unGuardarropa.agregarPrenda(camperaDeLluvia);

        //ahora tiene que fallar
        assertThrows(CantidadMaximaPrendaSuperadaException.class,() -> unGuardarropa.agregarPrenda(camisetaRacing));
    }

    @Test
    void agregarPrendaPorSerGuardarropaPremium() throws CantidadMaximaPrendaSuperadaException {
        Guardarropa unGuardarropa = new Guardarropa("Guardarropa1", true);

        unGuardarropa.agregarPrenda(remeraAzul);
        unGuardarropa.agregarPrenda(jeanNegro);
        unGuardarropa.agregarPrenda(gorraBlanca);
        unGuardarropa.agregarPrenda(nikesAmarillas);
        unGuardarropa.agregarPrenda(vestidoRosa);
        unGuardarropa.agregarPrenda(polleraVioleta);
        unGuardarropa.agregarPrenda(sombreroGrande);
        unGuardarropa.agregarPrenda(zapatosConTacosYPlataforma);
        unGuardarropa.agregarPrenda(pantalon_thor);
        unGuardarropa.agregarPrenda(pantalon_flash);
        unGuardarropa.agregarPrenda(casco_ironman);
        unGuardarropa.agregarPrenda(bikiniRoja);
        unGuardarropa.agregarPrenda(sombreroVaquero);
        unGuardarropa.agregarPrenda(anteojosSol);
        unGuardarropa.agregarPrenda(trajeDeBatman);
        unGuardarropa.agregarPrenda(botas_cpt_america);
        unGuardarropa.agregarPrenda(musculosaFloreada);
        unGuardarropa.agregarPrenda(anteojosNocturnos);
        unGuardarropa.agregarPrenda(jeanCorto);
        unGuardarropa.agregarPrenda(camperaDeLluvia);

        //ahora no tiene que fallar
        unGuardarropa.agregarPrenda(camisetaRacing);
    }

    @Test
    void agregarPrendaFallaPrimeroPeroPasaAPremiumYFunciona() throws CantidadMaximaPrendaSuperadaException {
        Guardarropa unGuardarropa = new Guardarropa("Guardarropa1", false);

        unGuardarropa.agregarPrenda(remeraAzul);
        unGuardarropa.agregarPrenda(jeanNegro);
        unGuardarropa.agregarPrenda(gorraBlanca);
        unGuardarropa.agregarPrenda(nikesAmarillas);
        unGuardarropa.agregarPrenda(vestidoRosa);
        unGuardarropa.agregarPrenda(polleraVioleta);
        unGuardarropa.agregarPrenda(sombreroGrande);
        unGuardarropa.agregarPrenda(zapatosConTacosYPlataforma);
        unGuardarropa.agregarPrenda(pantalon_thor);
        unGuardarropa.agregarPrenda(pantalon_flash);
        unGuardarropa.agregarPrenda(casco_ironman);
        unGuardarropa.agregarPrenda(bikiniRoja);
        unGuardarropa.agregarPrenda(sombreroVaquero);
        unGuardarropa.agregarPrenda(anteojosSol);
        unGuardarropa.agregarPrenda(trajeDeBatman);
        unGuardarropa.agregarPrenda(botas_cpt_america);
        unGuardarropa.agregarPrenda(musculosaFloreada);
        unGuardarropa.agregarPrenda(anteojosNocturnos);
        unGuardarropa.agregarPrenda(jeanCorto);
        unGuardarropa.agregarPrenda(camperaDeLluvia);

        //ahora tiene que fallar
        assertThrows(CantidadMaximaPrendaSuperadaException.class,() -> unGuardarropa.agregarPrenda(camisetaRacing));

        unGuardarropa.PasarAPremium();

        unGuardarropa.agregarPrenda(camisetaRacing);
    }
}