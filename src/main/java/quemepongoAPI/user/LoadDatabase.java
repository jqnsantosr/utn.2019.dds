package quemepongoAPI.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;
import quemepongoAPI.prenda.TipoPrenda;
import quemepongoAPI.prenda.TipoPrendaRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository, TipoPrendaRepository tipoPrendaRep) {
        TipoPrenda remera = new TipoPrenda("Remera");
        remera.agregarParteCuerpo(PartesCuerpo.TORSO);
        tipoPrendaRep.save(remera);

        TipoPrenda camisa = new TipoPrenda("Camisa");
        camisa.agregarParteCuerpo(PartesCuerpo.TORSO);
        tipoPrendaRep.save(camisa);

        TipoPrenda pantalon = new TipoPrenda("Pantalon");
        pantalon.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        tipoPrendaRep.save(pantalon);

        TipoPrenda gorra = new TipoPrenda("Gorra");
        gorra.agregarParteCuerpo(PartesCuerpo.CABEZA);
        tipoPrendaRep.save(gorra);

        TipoPrenda zapatilla = new TipoPrenda("Zapatilla");
        zapatilla.agregarParteCuerpo(PartesCuerpo.CALZADO);
        tipoPrendaRep.save(zapatilla);

        TipoPrenda vestido = new TipoPrenda("Vestido");
        vestido.agregarParteCuerpo(PartesCuerpo.TORSO);
        vestido.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        tipoPrendaRep.save(vestido);

        TipoPrenda pollera = new TipoPrenda("Pollera");
        pollera.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        tipoPrendaRep.save(pollera);

        TipoPrenda sombrero = new TipoPrenda("Sombrero");
        sombrero.agregarParteCuerpo(PartesCuerpo.CABEZA);
        tipoPrendaRep.save(sombrero);

        TipoPrenda zapato = new TipoPrenda("Zapato");
        zapato.agregarParteCuerpo(PartesCuerpo.CALZADO);
        tipoPrendaRep.save(zapato);

        TipoPrenda anteojos = new TipoPrenda("Anteojos");
        anteojos.agregarParteCuerpo(PartesCuerpo.OJOS);
        tipoPrendaRep.save(anteojos);

        TipoPrenda traje_de_banio = new TipoPrenda("Traje de Baño");
        traje_de_banio.agregarParteCuerpo(PartesCuerpo.TORSO);
        traje_de_banio.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        tipoPrendaRep.save(traje_de_banio);

        TipoPrenda traje_entero = new TipoPrenda("Traje Entero");
        traje_entero.agregarParteCuerpo(PartesCuerpo.TORSO);
        traje_entero.agregarParteCuerpo(PartesCuerpo.PIERNAS);
        traje_entero.agregarParteCuerpo(PartesCuerpo.CALZADO);
        traje_entero.agregarParteCuerpo(PartesCuerpo.CABEZA);
        tipoPrendaRep.save(traje_entero);

        //Guardarropa 1
        Prenda remeraAzul = new Prenda("Remera Azul", remera, "Azul");
        Prenda remeraNegra = new Prenda("Remera Negra", remera, "Negro");
        Prenda jeanAzul = new Prenda("Jean Negro", pantalon, "Azul");
        Prenda jeanNegro = new Prenda("Jean Negro", pantalon, "Negro");
        Prenda gorraBlanca = new Prenda("Gorra Blanca", gorra, "Blanca");
        Prenda gorraRoja = new Prenda("Gorra Roja", gorra, "Rojo");
        Prenda nikesAmarillas = new Prenda("Nikes Amarillas", zapatilla, "Amarillo");
        Prenda adidasAzules = new Prenda("Adidas azules", zapatilla, "Azul");

        List<Prenda> prendasDelGuardarropaComun = Arrays.asList(remeraAzul, remeraNegra, jeanAzul, jeanNegro, gorraBlanca, gorraRoja, nikesAmarillas, adidasAzules);

        Guardarropa g1 = new Guardarropa("Guardarropa Comun", prendasDelGuardarropaComun);

        //Guardarropa 2
        Prenda vestidoRosa = new Prenda("Vestido Rosa", vestido, "Rosa");
        Prenda polleraVioleta = new Prenda("Pollera Violeta", pollera, "Violeta");
        Prenda sombreroGrande = new Prenda("Sombrero Grande", sombrero, "Blanca");
        Prenda zapatosConTacosYPlataforma = new Prenda("Tacos con Plataforma", zapato, "Negro");
        Prenda casco_ironman = new Prenda("Casco Iron Man", sombrero, "Rojo");
        Prenda pantalon_flash = new Prenda("Pantalon de Flash", pantalon, "Rojo");
        Prenda remeraBlanca = new Prenda("Remera Blanca", remera, "Blanco");
        Prenda camisaBlanca = new Prenda("Camisa Blanca", remera, "Blanco");

        List<Prenda> prendasDelGuardarropaRaro1 = Arrays.asList(vestidoRosa, polleraVioleta, sombreroGrande, zapatosConTacosYPlataforma, casco_ironman, pantalon_flash, remeraBlanca, camisaBlanca);

        Guardarropa g2 = new Guardarropa("Guardarropa Comun", prendasDelGuardarropaRaro1);

        //Guardarropa 3
        Prenda bikiniRoja = new Prenda("Bikini Roja", traje_de_banio, "Roja");
        Prenda sombreroVaquero = new Prenda("Sombrero de Vaquero", sombrero, "Marron");
        Prenda zapatosConTacosYPlataformaRojos = new Prenda("Tacos con Plataforma", zapato, "Rojo");
        Prenda anteojosSol = new Prenda("Anteojos de Sol", anteojos, "Negro");
        Prenda trajeDeBatman = new Prenda("Traje De Batman", traje_entero, "Negro");
        Prenda botas_cpt_america = new Prenda("Botas de Capitan America", zapato, "Blanco?");
        Prenda pantalon_thor = new Prenda("Pantalon de Thor", pantalon, "Negro");
        Prenda remeraVioleta = new Prenda("Remera Violeta", remera, "Violeta");
        Prenda camisaVioleta = new Prenda("Camisa Violeta", remera, "Violeta");

        List<Prenda> prendasDelGuardarropaRaro2 = Arrays.asList(bikiniRoja,sombreroVaquero,zapatosConTacosYPlataformaRojos,anteojosSol,trajeDeBatman,botas_cpt_america,pantalon_thor,remeraVioleta,camisaVioleta);

        Guardarropa g3 = new Guardarropa("Guardarropa Comun", prendasDelGuardarropaRaro2);

        List<Guardarropa> guards = Arrays.asList(g1,g2,g3);

        return args -> {
            log.info("Preloading " + repository.save(new User("Ricardo", "12345", guards)));
        };
    }
}
