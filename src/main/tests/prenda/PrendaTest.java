package prenda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quemepongoAPI.prenda.Prenda;
import quemepongoAPI.prenda.PrendaIncoherenteException;
import quemepongoAPI.prenda.TipoPrenda;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static quemepongoAPI.prenda.PartesCuerpo.CALZADO;
import static quemepongoAPI.prenda.PartesCuerpo.TORSO;
import static quemepongoAPI.prenda.Tela.ALGODON;
import static quemepongoAPI.prenda.Tela.CUERO;

class PrendaTest {

    private TipoPrenda remeraLiviana = new TipoPrenda("Remera", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 5);
    private Prenda prendaTestigo = new Prenda("Remera Azul", ALGODON, Arrays.asList(TORSO), remeraLiviana, "Azul");

    @BeforeEach
    void setUp() {
    }

    @Test
    void crearPrendaOk() {

        Prenda remeraAzul = new Prenda("Remera Azul", ALGODON, Arrays.asList(TORSO), remeraLiviana, "Azul");
        assertEquals(remeraAzul, prendaTestigo);
    }

    @Test
    void crearPrendaDeTelaIncoherente() {
        assertThrows(PrendaIncoherenteException.class, () -> new Prenda("Remera Azul", CUERO, Arrays.asList(TORSO), remeraLiviana, "Azul"));
    }

    @Test
    void crearPrendaDeLugarIncoherente() {
        assertThrows(PrendaIncoherenteException.class, () -> new Prenda("Remera Azul", CUERO, Arrays.asList(CALZADO), remeraLiviana, "Azul"));
    }
}
