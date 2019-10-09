package atuendo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static quemepongoAPI.prenda.PartesCuerpo.*;
import static quemepongoAPI.prenda.Tela.*;
import static quemepongoAPI.prenda.Tela.SINTETICA;

import com.sun.xml.internal.ws.util.CompletedFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import quemepongoAPI.atuendo.Atuendo;
import quemepongoAPI.atuendo.AtuendoIncompletoException;
import quemepongoAPI.clima.Clima;
import quemepongoAPI.clima.ClimaService;
import quemepongoAPI.clima.CondicionesClimaticas;
import quemepongoAPI.clima.Currently;
import quemepongoAPI.evento.Evento;
import quemepongoAPI.evento.FechaYHoraParseException;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;
import quemepongoAPI.prenda.TipoPrenda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class AtuendoBuilderClimaTest {

    @Mock
    ClimaService climaService;
    @Mock
    Currently currentClima;
    @Mock
    Clima clima;

    private TipoPrenda remeraLiviana = new TipoPrenda("Remera", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 5);
    private TipoPrenda remeraPesada = new TipoPrenda("Remera", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 15);
    private TipoPrenda camisa = new TipoPrenda("Camisa", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 10);
    private TipoPrenda pantalonLiviano = new TipoPrenda("Pantalon", Collections.singletonList(JEAN), Collections.singletonList(PIERNAS), 10);
    private TipoPrenda pantalonPesado = new TipoPrenda("Pantalon", Collections.singletonList(JEAN), Collections.singletonList(PIERNAS), 15);
    private TipoPrenda pantalonDeVestir = new TipoPrenda("Pantalon", Collections.singletonList(ALGODON), Collections.singletonList(PIERNAS), 12);
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

    private Evento eventoNoFormal;

    {
        try {
            eventoNoFormal = new Evento("25/12/2019 a las 00:00", false);
        } catch (FechaYHoraParseException e) {
            e.printStackTrace();
        }
    }

    private Evento eventoFormal;

    {
        try {
            eventoFormal = new Evento("25/12/2019 a las 00:00", true);
        } catch (FechaYHoraParseException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void crearNuevoAtuendoParaClimaFrio() throws ExecutionException, InterruptedException {
        Guardarropa unGuardarropa = new Guardarropa("Test Clima Frio");

        when(climaService.getClima(any())).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(25.0);

        Prenda remeraAzul = new Prenda("Remera Azul", ALGODON,Collections.singletonList(TORSO), remeraLiviana, "Azul");
        Prenda buzoVerde = new Prenda("Buzo Verde", ALGODON, Collections.singletonList(TORSO), buzo, "Verde");
        Prenda jeanNegro = new Prenda("Jean Negro", JEAN,Collections.singletonList(PIERNAS), pantalonPesado, "Negro");
        Prenda pantalonSnow = new Prenda("Pantalon para Nieve", ALGODON,Collections.singletonList(PIERNAS), pantalonInvierno, "Rojo");
        Prenda gorraBlanca = new Prenda("Gorra Blanca", ALGODON,Collections.singletonList(CABEZA), gorra, "Blanca");
        Prenda nikesAmarillas = new Prenda("Nikes Amarillas", LONA,Collections.singletonList(CALZADO), zapatilla, "Amarillas");

        unGuardarropa.agregarPrenda(remeraAzul);
        unGuardarropa.agregarPrenda(buzoVerde);
        unGuardarropa.agregarPrenda(jeanNegro);
        unGuardarropa.agregarPrenda(pantalonSnow);
        unGuardarropa.agregarPrenda(gorraBlanca);
        unGuardarropa.agregarPrenda(nikesAmarillas);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CABEZA);
        partesPedidas.add(PartesCuerpo.CALZADO);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoClima(partesPedidas, clima, eventoNoFormal);
        unAtuendo.mostrarAtuendo();

        assertTrue(unAtuendo.tiene_prenda(buzoVerde));
        assertTrue(unAtuendo.tiene_prenda(pantalonSnow));
        assertTrue(unAtuendo.tiene_prenda(gorraBlanca));
        assertTrue(unAtuendo.tiene_prenda(nikesAmarillas));
    }

    @Test
    void crearNuevoAtuendoParaClimaCaliente() throws ExecutionException, InterruptedException {
        Guardarropa unGuardarropa = new Guardarropa("Test Clima Caliente");

        when(climaService.getClima(any())).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(35.0);

        Prenda remeraVerde = new Prenda("Remera Verde", ALGODON, Collections.singletonList(TORSO), remeraLiviana, "Verde");
        Prenda jeanGris = new Prenda("Jean Gris", JEAN, Collections.singletonList(PIERNAS),pantalonLiviano, "Gris");
        Prenda gorraNegra = new Prenda("Gorra negra", ALGODON, Collections.singletonList(CABEZA),gorraLiviana, "Negra");
        Prenda nikesVerdes = new Prenda("Nikes Verdes", LONA,Collections.singletonList(CALZADO), zapatilla, "Verdes");

        unGuardarropa.agregarPrenda(remeraVerde);
        unGuardarropa.agregarPrenda(jeanGris);
        unGuardarropa.agregarPrenda(gorraNegra);
        unGuardarropa.agregarPrenda(nikesVerdes);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CABEZA);
        partesPedidas.add(PartesCuerpo.CALZADO);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoClima(partesPedidas, clima, eventoNoFormal);
        unAtuendo.mostrarAtuendo();

        assertTrue(unAtuendo.tiene_prenda(remeraVerde));
        assertTrue(unAtuendo.tiene_prenda(jeanGris));
        assertTrue(unAtuendo.tiene_prenda(gorraNegra));
        assertTrue(unAtuendo.tiene_prenda(nikesVerdes));
    }

    @Test
    void crearNuevoAtuendoParaClimaFrioTieneMasDeUnaCapa() throws ExecutionException, InterruptedException {
        Guardarropa unGuardarropa = new Guardarropa("Test Clima Frio Power");

        when(climaService.getClima(any())).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(15.0);

        Prenda remeraTermicaBlanca = new Prenda("Remera Termica Blanca", ALGODON, Collections.singletonList(TORSO), remeraPesada, "Blanca");
        Prenda buzoNegro = new Prenda("Buzo Negro", ALGODON, Collections.singletonList(TORSO), buzoFrisa, "Negro");
        Prenda camperaGris = new Prenda("Campera Gris", SINTETICA, Collections.singletonList(TORSO), campera, "Gris");
        Prenda jeanNegro = new Prenda("Jean Negro", JEAN,Collections.singletonList(PIERNAS), pantalonPesado, "Negro");
        Prenda gorraLanaNegra = new Prenda("Gorra de lana negra", ALGODON,Collections.singletonList(CABEZA), gorra, "Negra");
        Prenda adidasNegra = new Prenda("Adidas Negras", LONA,Collections.singletonList(CALZADO), zapatilla, "Negras");

        unGuardarropa.agregarPrenda(remeraTermicaBlanca);
        unGuardarropa.agregarPrenda(buzoNegro);
        unGuardarropa.agregarPrenda(camperaGris);
        unGuardarropa.agregarPrenda(jeanNegro);
        unGuardarropa.agregarPrenda(gorraLanaNegra);
        unGuardarropa.agregarPrenda(adidasNegra);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CABEZA);
        partesPedidas.add(PartesCuerpo.CALZADO);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoClima(partesPedidas, clima, eventoNoFormal);
        unAtuendo.mostrarAtuendo();

        assertTrue(unAtuendo.tiene_prenda(camperaGris));
        assertTrue(unAtuendo.tiene_prenda(buzoNegro));
        assertTrue(unAtuendo.tiene_prenda(jeanNegro));
        assertTrue(unAtuendo.tiene_prenda(adidasNegra));
        assertTrue(unAtuendo.tiene_prenda(gorraLanaNegra));
    }

    @Test
    void crearNuevoAtuendoParaClimaFrioNoAlcanzanPrendas() throws ExecutionException, InterruptedException {
        Guardarropa unGuardarropa = new Guardarropa("Test Clima Frio No Alcanzan Prendas");

        when(climaService.getClima(any())).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(17.0);

        Prenda remeraTermicaBlanca = new Prenda("Remera Termica Blanca", ALGODON, Collections.singletonList(TORSO), remeraPesada, "Blanca");
        Prenda jeanNegro = new Prenda("Jean Negro", JEAN,Collections.singletonList(PIERNAS), pantalonPesado, "Negro");
        Prenda adidasNegra = new Prenda("Adidas Negras", LONA,Collections.singletonList(CALZADO), zapatilla, "Negro");

        unGuardarropa.agregarPrenda(remeraTermicaBlanca);
        unGuardarropa.agregarPrenda(jeanNegro);
        unGuardarropa.agregarPrenda(adidasNegra);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CALZADO);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoClima(partesPedidas, clima, eventoNoFormal);
        unAtuendo.mostrarAtuendo();

        assertTrue(unAtuendo.tiene_prenda(remeraTermicaBlanca));
        assertTrue(unAtuendo.tiene_prenda(jeanNegro));
        assertTrue(unAtuendo.tiene_prenda(adidasNegra));
    }

    @Test
    void crearNuevoAtuendoParaClimaCalienteTieneSoloUnaCapa() throws ExecutionException, InterruptedException {
        Guardarropa unGuardarropa = new Guardarropa("Test Clima Caliente Una Capa");

        when(climaService.getClima(any())).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(32.0);

        Prenda remeraVerde = new Prenda("Remera Verde", ALGODON, Collections.singletonList(TORSO), remeraLiviana, "Verde");
        Prenda camperaRosa = new Prenda("Campera Rosa", SINTETICA, Collections.singletonList(TORSO), campera, "Rosa");
        Prenda jeanGris = new Prenda("Jean Gris", JEAN, Collections.singletonList(PIERNAS), pantalonLiviano, "Gris");
        Prenda bermudaVioleta = new Prenda("Bermuda Violeta", JEAN, Collections.singletonList(PIERNAS), pantalonCorto, "Violeta");
        Prenda nikesVerdes = new Prenda("Nikes Verdes", LONA, Collections.singletonList(CALZADO), zapatilla, "Verdes");

        unGuardarropa.agregarPrenda(remeraVerde);
        unGuardarropa.agregarPrenda(jeanGris);
        unGuardarropa.agregarPrenda(nikesVerdes);
        unGuardarropa.agregarPrenda(bermudaVioleta);
        unGuardarropa.agregarPrenda(camperaRosa);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CALZADO);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoClima(partesPedidas, clima, eventoNoFormal);
        unAtuendo.mostrarAtuendo();

        assertTrue(unAtuendo.tiene_prenda(remeraVerde));
        assertTrue(unAtuendo.tiene_prenda(bermudaVioleta));
        assertTrue(unAtuendo.tiene_prenda(nikesVerdes));
        assertFalse(unAtuendo.tiene_prenda(camperaRosa));
        assertFalse(unAtuendo.tiene_prenda(jeanGris));
    }

    @Test
    void crearNuevoAtuendoParaClimaTiraExceptionPorNoTenerPrendas() throws ExecutionException, InterruptedException {
        Guardarropa unGuardarropa = new Guardarropa("TEST");

        when(climaService.getClima(any())).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(9001.0); // it's over 9000!!!

        Prenda vestidoRosa = new Prenda("Vestido Rosa", SEDA, Arrays.asList(TORSO, PIERNAS), vestido, "Rosa");

        unGuardarropa.agregarPrenda(vestidoRosa);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CALZADO);

        assertThrows(AtuendoIncompletoException.class, () -> unGuardarropa.crearAtuendoClima(partesPedidas, clima, eventoNoFormal));
    }

    @Test
    void crearNuevoAtuendoParaVientoNoTienePollera() throws ExecutionException, InterruptedException {
        Guardarropa unGuardarropa = new Guardarropa("TEST");

        when(climaService.getClima(any())).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(0.0);

        Prenda polleraNegra = new Prenda("Pollera Negra", SEDA, Arrays.asList(PIERNAS), pollera, "Negro");
        Prenda jeanGris = new Prenda("Jean Gris", JEAN, Collections.singletonList(PIERNAS), pantalonLiviano, "Gris");
        Prenda remeraTermicaBlanca = new Prenda("Remera Termica Blanca", ALGODON, Collections.singletonList(TORSO), remeraPesada, "Blanca");
        Prenda adidasNegra = new Prenda("Adidas Negras", LONA,Collections.singletonList(CALZADO), zapatilla, "Negro");

        //TODO
        pollera.agregarIncompatibilidad(CondicionesClimaticas.VIENTO);

        unGuardarropa.agregarPrenda(polleraNegra);
        unGuardarropa.agregarPrenda(jeanGris);
        unGuardarropa.agregarPrenda(remeraTermicaBlanca);
        unGuardarropa.agregarPrenda(adidasNegra);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CALZADO);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoClima(partesPedidas, clima, eventoNoFormal);
        unAtuendo.mostrarAtuendo();

        assertTrue(unAtuendo.tiene_prenda(remeraTermicaBlanca));
        assertTrue(unAtuendo.tiene_prenda(jeanGris));
        assertTrue(unAtuendo.tiene_prenda(adidasNegra));
        assertFalse(unAtuendo.tiene_prenda(polleraNegra));
    }

    @Test
    void crearNuevoAtuendoParaEventoFormalConClimaFrio() throws ExecutionException, InterruptedException
    {
        Guardarropa unGuardarropa = new Guardarropa("TEST");

        when(climaService.getClima(any())).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(23.95);

        Prenda camisaBlanca = new Prenda("Camisa Blanca", ALGODON, Arrays.asList(TORSO), camisa, "Blanco");
        Prenda pantalonNegro = new Prenda("Pantalon Negro", ALGODON, Arrays.asList(PIERNAS), pantalonDeVestir, "Negro");
        Prenda sacoNegro = new Prenda("Saco Negro", ALGODON, Arrays.asList(TORSO), campera, "Negro");
        Prenda zapatosNegros = new Prenda("Zapatos Negros", CUERO, Arrays.asList(CALZADO), zapato, "Negro");
        Prenda jeanGris = new Prenda("Jean Gris", JEAN, Collections.singletonList(PIERNAS), pantalonLiviano, "Gris");

        camisaBlanca.setEsFormal(true);
        pantalonNegro.setEsFormal(true);
        sacoNegro.setEsFormal(true);
        zapatosNegros.setEsFormal(true);

        unGuardarropa.agregarPrenda(camisaBlanca);
        unGuardarropa.agregarPrenda(pantalonNegro);
        unGuardarropa.agregarPrenda(sacoNegro);
        unGuardarropa.agregarPrenda(zapatosNegros);
        unGuardarropa.agregarPrenda(jeanGris);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CALZADO);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoClima(partesPedidas, clima, eventoFormal);
        unAtuendo.mostrarAtuendo();

        assertTrue(unAtuendo.tiene_prenda(sacoNegro));
        assertTrue(unAtuendo.tiene_prenda(camisaBlanca));
        assertTrue(unAtuendo.tiene_prenda(pantalonNegro));
        assertTrue(unAtuendo.tiene_prenda(zapatosNegros));
        assertFalse(unAtuendo.tiene_prenda(jeanGris));
    }

    @Test
    void crearNuevoAtuendoParaEventoFormalConClimaCaliente() throws ExecutionException, InterruptedException
    {
        Guardarropa unGuardarropa = new Guardarropa("TEST");

        when(climaService.getClima(any())).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(32.2);

        Prenda sacoNegro = new Prenda("Saco Negro", ALGODON, Arrays.asList(TORSO), campera, "Negro");
        Prenda zapatosNegros = new Prenda("Zapatos Negros", CUERO, Arrays.asList(CALZADO), zapato, "Negro");
        Prenda jeanGris = new Prenda("Jean Gris", JEAN, Collections.singletonList(PIERNAS), pantalonLiviano, "Gris");
        Prenda vestidoRojo = new Prenda("Vestido Rojo", SEDA, Arrays.asList(TORSO, PIERNAS), vestido, "Rojo");

        vestidoRojo.setEsFormal(true);
        sacoNegro.setEsFormal(true);
        zapatosNegros.setEsFormal(true);

        unGuardarropa.agregarPrenda(sacoNegro);
        unGuardarropa.agregarPrenda(zapatosNegros);
        unGuardarropa.agregarPrenda(jeanGris);
        unGuardarropa.agregarPrenda(vestidoRojo);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CALZADO);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoClima(partesPedidas, clima, eventoFormal);
        unAtuendo.mostrarAtuendo();

        assertFalse(unAtuendo.tiene_prenda(sacoNegro));
        assertTrue(unAtuendo.tiene_prenda(vestidoRojo));
        assertTrue(unAtuendo.tiene_prenda(zapatosNegros));
        assertFalse(unAtuendo.tiene_prenda(jeanGris));
    }
}