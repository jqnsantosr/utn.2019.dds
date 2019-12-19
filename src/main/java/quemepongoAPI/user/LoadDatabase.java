package quemepongoAPI.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.Prenda;
import quemepongoAPI.prenda.TipoPrenda;
import quemepongoAPI.prenda.TipoPrendaRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static quemepongoAPI.prenda.PartesCuerpo.*;
import static quemepongoAPI.prenda.Tela.*;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository, TipoPrendaRepository tipoPrendaRep) {

        return args -> {
            TipoPrenda remeraLiviana = new TipoPrenda("Remera", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 5);
            TipoPrenda remeraPesada = new TipoPrenda("Remera", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 15);
            TipoPrenda camisa = new TipoPrenda("Camisa", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 10);
            TipoPrenda pantalonLiviano = new TipoPrenda("Pantalon", Collections.singletonList(JEAN), Collections.singletonList(PIERNAS), 10);
            TipoPrenda pantalonPesado = new TipoPrenda("Pantalon", Collections.singletonList(JEAN), Collections.singletonList(PIERNAS), 15);
            TipoPrenda gorraLiviana = new TipoPrenda("Gorra", Arrays.asList(ALGODON, POLIESTER), Collections.singletonList(CABEZA), 3);
            TipoPrenda gorra = new TipoPrenda("Gorra", Arrays.asList(ALGODON, POLIESTER), Collections.singletonList(CABEZA), 7);
            TipoPrenda zapatilla = new TipoPrenda("Zapatilla", Arrays.asList(CUERO, LONA, CUERINA), Collections.singletonList(CALZADO), 2);
            TipoPrenda vestido = new TipoPrenda("Vestido", Arrays.asList(SEDA, LYCRA, ALGODON), Arrays.asList(TORSO, PIERNAS), 2);
            TipoPrenda pollera = new TipoPrenda("Pollera", Arrays.asList(SEDA, LYCRA, ALGODON), Collections.singletonList(PIERNAS), 2);
            TipoPrenda sombrero = new TipoPrenda("Sombrero", Arrays.asList(CUERO, PANA, LONA), Arrays.asList(CABEZA), 3);
            TipoPrenda zapato = new TipoPrenda("Zapato", Arrays.asList(CUERO, CUERINA), Collections.singletonList(CALZADO), 4);
            TipoPrenda anteojos = new TipoPrenda("Anteojos", Arrays.asList(SINTETICA), Collections.singletonList(OJOS), 0);
            TipoPrenda traje_de_banio = new TipoPrenda("Traje de Baño", Arrays.asList(SINTETICA), Arrays.asList(TORSO, PIERNAS), 0);
            TipoPrenda traje_entero = new TipoPrenda("Traje Entero", Arrays.asList(SINTETICA), Arrays.asList(TORSO, PIERNAS, CALZADO, CABEZA), 8);
            TipoPrenda buzo = new TipoPrenda("Buzo", Collections.singletonList(ALGODON), Collections.singletonList(TORSO), 10);
            TipoPrenda buzoFrisa = new TipoPrenda("Buzo", Arrays.asList(ALGODON, FRISELINA), Collections.singletonList(TORSO), 15);
            TipoPrenda pantalonInvierno = new TipoPrenda("Pantalon de Invierno", Collections.singletonList(ALGODON), Collections.singletonList(PIERNAS), 15);
            TipoPrenda campera = new TipoPrenda("Campera", Arrays.asList(ALGODON, SINTETICA), Collections.singletonList(TORSO), 20);
            TipoPrenda pantalonCorto = new TipoPrenda("Pantalon Corto", Arrays.asList(JEAN, ALGODON, SINTETICA), Collections.singletonList(PIERNAS), 5);


            //Guardarropa 1
            Prenda remeraAzul = new Prenda("Remera Azul", ALGODON, Collections.singletonList(TORSO), remeraLiviana, "Azul");
            Prenda jeanNegro = new Prenda("Jean Negro", JEAN, Collections.singletonList(PIERNAS), pantalonPesado, "Negro");
            Prenda gorraBlanca = new Prenda("Gorra Blanca", ALGODON, Collections.singletonList(CABEZA), gorra, "Blanca");
            Prenda nikesAmarillas = new Prenda("Nikes Amarillas", LONA, Collections.singletonList(CALZADO), zapatilla, "Amarillas");
            Prenda remeraNegra = new Prenda("Remera Negro", ALGODON, Collections.singletonList(TORSO), remeraLiviana, "Negro");
            Prenda jeanAzul = new Prenda("Jean Azul", JEAN, Collections.singletonList(PIERNAS), pantalonPesado, "Azul");
            Prenda gorraRoja = new Prenda("Gorra roja", ALGODON, Collections.singletonList(CABEZA), gorra, "Blanca");
            Prenda adidasAzules = new Prenda("Adidas azules", LONA, Collections.singletonList(CALZADO), zapatilla, "Azul");

            //Guardarropa 2
            Prenda vestidoRosa = new Prenda("Vestido Rosa", LYCRA, Arrays.asList(TORSO, PIERNAS), vestido, "Rosa");
            Prenda polleraVioleta = new Prenda("Pollera Violeta", SEDA, Collections.singletonList(PIERNAS), pollera, "Violeta");
            Prenda sombreroGrande = new Prenda("Sombrero Grande", PANA, Collections.singletonList(CABEZA), sombrero, "Blanca");
            Prenda zapatosConTacosYPlataforma = new Prenda("Tacos con Plataforma", CUERO, Collections.singletonList(CALZADO), zapato, "Negro");
            Prenda pantalon_thor = new Prenda("Pantalon de Thor", JEAN, Collections.singletonList(PIERNAS), pantalonLiviano, "Negro");
            Prenda pantalon_flash = new Prenda("Pantalon de Flash", JEAN, Collections.singletonList(PIERNAS), pantalonLiviano, "Rojo");
            Prenda casco_ironman = new Prenda("Casco Iron Man", CUERO, Collections.singletonList(CABEZA), sombrero, "Rojo");
            Prenda remeraBlanca = new Prenda("Remera Blanca", ALGODON, Collections.singletonList(TORSO), remeraLiviana, "Blanco");
            Prenda camisaBlanca = new Prenda("Camisa Blanca", ALGODON, Collections.singletonList(TORSO), camisa, "Blanco");

            //Guardarropa 3
            Prenda bikiniRoja = new Prenda("Bikini Roja", SINTETICA, Arrays.asList(TORSO, PIERNAS), traje_de_banio, "Roja");
            Prenda sombreroVaquero = new Prenda("Sombrero de Vaquero", CUERO, Collections.singletonList(CABEZA), sombrero, "Marron");
            Prenda zapatosConTacosYPlataformaRojos = new Prenda("Tacos con Plataforma", CUERO, Collections.singletonList(CALZADO), zapato, "Rojo");
            Prenda anteojosSol = new Prenda("Anteojos de Sol", SINTETICA, Arrays.asList(OJOS), anteojos, "Negro");
            Prenda trajeDeBatman = new Prenda("Traje De Batman", SINTETICA, Arrays.asList(CALZADO, TORSO, PIERNAS, CABEZA), traje_entero, "Negro");
            Prenda botas_cpt_america = new Prenda("Botas de Capitan America", CUERO, Collections.singletonList(CALZADO), zapato, "Blanco?");
            Prenda remeraVioleta = new Prenda("Remera Violeta", ALGODON, Collections.singletonList(TORSO), remeraPesada, "Violeta");
            Prenda camisaVioleta = new Prenda("Camisa Violeta", ALGODON, Collections.singletonList(TORSO), camisa, "Violeta");

            Prenda sandaliasVerdes = new Prenda("Sandalias Verdes", LONA, Collections.singletonList(CALZADO), zapatilla, "Verde");

            List<Prenda> prendasDelGuardarropaComun = Arrays.asList(remeraAzul, remeraNegra, jeanAzul, jeanNegro, gorraBlanca, gorraRoja, nikesAmarillas, adidasAzules);
            Guardarropa g1 = new Guardarropa("Guardarropa Comun", prendasDelGuardarropaComun);

            List<Prenda> prendasDelGuardarropaRaro1 = Arrays.asList(vestidoRosa, polleraVioleta, sombreroGrande, zapatosConTacosYPlataforma, casco_ironman, pantalon_flash, remeraBlanca, camisaBlanca);
            Guardarropa g2 = new Guardarropa("Guardarropa Raro1", prendasDelGuardarropaRaro1);

            List<Prenda> prendasDelGuardarropaRaro2 = Arrays.asList(bikiniRoja, sombreroVaquero, zapatosConTacosYPlataformaRojos, anteojosSol, trajeDeBatman, botas_cpt_america, pantalon_thor, remeraVioleta, camisaVioleta);
            Guardarropa g3 = new Guardarropa("Guardarropa Raro2", prendasDelGuardarropaRaro2);

            List<Prenda> prendasDelGuardarropaVerano = Arrays.asList(bikiniRoja, sombreroVaquero, vestidoRosa, polleraVioleta, remeraVioleta, anteojosSol, nikesAmarillas, adidasAzules, camisaBlanca, jeanNegro, gorraBlanca, sandaliasVerdes);
            Guardarropa g4 = new Guardarropa("Guardarropa de Verano 2019", prendasDelGuardarropaVerano);

            log.info("Preloading " + repository.save(new User("Paula", "12346", Arrays.asList(g1, g2, g3,g4))));

            tipoPrendaRep.save(remeraLiviana);
            tipoPrendaRep.save(remeraPesada);
            tipoPrendaRep.save(camisa);
            tipoPrendaRep.save(pantalonLiviano);
            tipoPrendaRep.save(pantalonPesado);
            tipoPrendaRep.save(gorra);
            tipoPrendaRep.save(zapatilla);
            tipoPrendaRep.save(vestido);
            tipoPrendaRep.save(pollera);
            tipoPrendaRep.save(sombrero);
            tipoPrendaRep.save(gorraLiviana);
            tipoPrendaRep.save(anteojos);
            tipoPrendaRep.save(traje_de_banio);
            tipoPrendaRep.save(traje_entero);
            tipoPrendaRep.save(buzo);
            tipoPrendaRep.save(buzoFrisa);
            tipoPrendaRep.save(pantalonInvierno);
            tipoPrendaRep.save(campera);
            tipoPrendaRep.save(pantalonCorto);
        };
    }
}
