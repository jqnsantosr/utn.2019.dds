package atuendo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import quemepongoAPI.atuendo.Atuendo;
import quemepongoAPI.clima.Clima;
import quemepongoAPI.clima.ClimaController;
import quemepongoAPI.clima.Currently;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;
import quemepongoAPI.prenda.TipoPrenda;

import java.util.ArrayList;
import java.util.List;

class AtuendoBuilderClimaTest {

    @Mock
    ClimaController climaController;
    @Mock
    Currently currentClima;
    @Mock
    Clima clima;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void crearNuevoAtuendoParaClimaFrio()
    {
        Guardarropa unGuardarropa = new Guardarropa("Test Clima Frio");

        unGuardarropa.setClimaController(climaController);
        when(climaController.getClima()).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(25.0);

        TipoPrenda remera = new TipoPrenda("Remera");
        remera.setCalor(5);
        remera.agregarParteCuerpo(PartesCuerpo.TORSO);
        Prenda remeraAzul = new Prenda("Remera Azul", remera, "Azul");

        TipoPrenda buzo = new TipoPrenda("Buzo");
        buzo.setCalor(10);
        buzo.agregarParteCuerpo(PartesCuerpo.TORSO);
        Prenda buzoVerde = new Prenda("Buzo Verde", buzo, "Verde");

        TipoPrenda pantalon = new TipoPrenda("Pantalon");
        pantalon.setCalor(15);
        pantalon.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        Prenda jeanNegro = new Prenda("Jean Negro", pantalon, "Negro");

        TipoPrenda pantalonInvierno = new TipoPrenda("Pantalon de Invierno");
        pantalonInvierno.setCalor(15);
        pantalonInvierno.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        Prenda pantalonSnow = new Prenda("Pantalon para Nieve", pantalonInvierno, "Rojo");

        TipoPrenda gorra = new TipoPrenda("Gorra");
        gorra.setCalor(7);
        gorra.agregarParteCuerpo(PartesCuerpo.CABEZA);
        Prenda gorraBlanca = new Prenda("Gorra Blanca", gorra, "Blanca");

        TipoPrenda zapatilla = new TipoPrenda("Zapatilla");
        zapatilla.setCalor(2);
        zapatilla.agregarParteCuerpo(PartesCuerpo.CALZADO);
        Prenda nikesAmarillas = new Prenda("Nikes Amarillas", zapatilla, "Amarillas");

        unGuardarropa.addPrenda(remeraAzul);
        unGuardarropa.addPrenda(buzoVerde);
        unGuardarropa.addPrenda(jeanNegro);
        unGuardarropa.addPrenda(pantalonSnow);
        unGuardarropa.addPrenda(gorraBlanca);
        unGuardarropa.addPrenda(nikesAmarillas);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CABEZA);
        partesPedidas.add(PartesCuerpo.CALZADO);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoClima(partesPedidas);
        unAtuendo.mostrarAtuendo();

        assertTrue(unAtuendo.tiene_prenda(buzoVerde));
        assertTrue(unAtuendo.tiene_prenda(pantalonSnow));
        assertTrue(unAtuendo.tiene_prenda(gorraBlanca));
        assertTrue(unAtuendo.tiene_prenda(nikesAmarillas));
    }

    @Test
    void crearNuevoAtuendoParaClimaCaliente()
    {
        Guardarropa unGuardarropa = new Guardarropa("Test Clima Caliente");

        unGuardarropa.setClimaController(climaController);
        when(climaController.getClima()).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(35.0);

        TipoPrenda remera = new TipoPrenda("Remera");
        remera.setCalor(5);
        remera.agregarParteCuerpo(PartesCuerpo.TORSO);
        Prenda remeraVerde = new Prenda("Remera Verde", remera, "Verde");

        TipoPrenda pantalon = new TipoPrenda("Pantalon");
        pantalon.setCalor(10);
        pantalon.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        Prenda jeanGris = new Prenda("Jean Gris", pantalon, "Gris");

        TipoPrenda gorra = new TipoPrenda("Gorra");
        gorra.setCalor(3);
        gorra.agregarParteCuerpo(PartesCuerpo.CABEZA);
        Prenda gorraNegra = new Prenda("Gorra negra", gorra, "Negra");

        TipoPrenda zapatilla = new TipoPrenda("Zapatilla");
        zapatilla.setCalor(2);
        zapatilla.agregarParteCuerpo(PartesCuerpo.CALZADO);
        Prenda nikesVerdes = new Prenda("Nikes Verdes", zapatilla, "Verdes");

        unGuardarropa.addPrenda(remeraVerde);
        unGuardarropa.addPrenda(jeanGris);
        unGuardarropa.addPrenda(gorraNegra);
        unGuardarropa.addPrenda(nikesVerdes);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CABEZA);
        partesPedidas.add(PartesCuerpo.CALZADO);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoClima(partesPedidas);
        unAtuendo.mostrarAtuendo();

        assertTrue(unAtuendo.tiene_prenda(remeraVerde));
        assertTrue(unAtuendo.tiene_prenda(jeanGris));
        assertTrue(unAtuendo.tiene_prenda(gorraNegra));
        assertTrue(unAtuendo.tiene_prenda(nikesVerdes));
    }

    @Test
    void crearNuevoAtuendoParaClimaFrioTieneMasDeUnaCapa()
    {
        Guardarropa unGuardarropa = new Guardarropa("Test Clima Frio Power");

        unGuardarropa.setClimaController(climaController);
        when(climaController.getClima()).thenReturn(clima);
        when(clima.getClimateNow()).thenReturn(currentClima);
        when(currentClima.getTemperature()).thenReturn(15.0);

        TipoPrenda remera = new TipoPrenda("Remera");
        remera.setCalor(15);
        remera.agregarParteCuerpo(PartesCuerpo.TORSO);
        Prenda remeraTermicaBlanca = new Prenda("Remera Termica Blanca", remera, "Blanca");

        TipoPrenda buzo = new TipoPrenda("Buzo");
        buzo.setCalor(15);
        buzo.agregarParteCuerpo(PartesCuerpo.TORSO);
        Prenda buzoNegro = new Prenda("Buzo Negro", buzo, "Negro");

        TipoPrenda campera = new TipoPrenda("Campera");
        campera.setCalor(20);
        campera.agregarParteCuerpo(PartesCuerpo.TORSO);
        Prenda camperaGris = new Prenda("Campera Gris", campera, "Gris");

        TipoPrenda pantalon = new TipoPrenda("Pantalon");
        pantalon.setCalor(15);
        pantalon.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        Prenda jeanNegro = new Prenda("Jean Negro", pantalon, "Negro");

        TipoPrenda gorra = new TipoPrenda("Gorra");
        gorra.setCalor(3);
        gorra.agregarParteCuerpo(PartesCuerpo.CABEZA);
        Prenda gorraLanaNegra = new Prenda("Gorra de lana negra", gorra, "Negra");

        TipoPrenda zapatilla = new TipoPrenda("Zapatilla");
        zapatilla.setCalor(2);
        zapatilla.agregarParteCuerpo(PartesCuerpo.CALZADO);
        Prenda adidasNegra = new Prenda("Adidas Negras", zapatilla, "Negras");

        unGuardarropa.addPrenda(remeraTermicaBlanca);
        unGuardarropa.addPrenda(buzoNegro);
        unGuardarropa.addPrenda(camperaGris);
        unGuardarropa.addPrenda(jeanNegro);
        unGuardarropa.addPrenda(gorraLanaNegra);
        unGuardarropa.addPrenda(adidasNegra);

        List<PartesCuerpo> partesPedidas = new ArrayList<>();
        partesPedidas.add(PartesCuerpo.TORSO);
        partesPedidas.add(PartesCuerpo.PIERNAS);
        partesPedidas.add(PartesCuerpo.CABEZA);
        partesPedidas.add(PartesCuerpo.CALZADO);

        Atuendo unAtuendo = unGuardarropa.crearAtuendoClima(partesPedidas);
        unAtuendo.mostrarAtuendo();

        assertTrue(unAtuendo.tiene_prenda(camperaGris));
        assertTrue(unAtuendo.tiene_prenda(jeanNegro));
        assertTrue(unAtuendo.tiene_prenda(jeanNegro));
        assertTrue(unAtuendo.tiene_prenda(adidasNegra));
    }

    @Test
    void crearNuevoAtuendoParaClimaFrioNoAlcanzanPrendas()
    {

    }

    @Test
    void crearNuevoAtuendoParaClimaCalienteTieneSoloUnaCapa()
    {

    }

    @Test
    void crearNuevoAtuendoParaClimaTiraExceptionPorNoTenerPrendas()
    {

    }
}