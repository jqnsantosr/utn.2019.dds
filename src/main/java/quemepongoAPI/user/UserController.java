package quemepongoAPI.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;
import quemepongoAPI.atuendo.Atuendo;
import quemepongoAPI.clima.Clima;
import quemepongoAPI.clima.ClimaService;
import quemepongoAPI.clima.ClimateApisNotWorkingException;
import quemepongoAPI.evento.Evento;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.lugar.Lugar;
import quemepongoAPI.lugar.LugarService;
import quemepongoAPI.prenda.PartesCuerpo;
import quemepongoAPI.prenda.Prenda;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@CrossOrigin(origins = { "http://localhost:3000"})
@RestController
class UserController {

    private final UserRepository repository;
    private final UserResourceAssembler assembler;
    private final ClimaService climaService;
    private final LugarService lugarService;

    UserController(UserRepository repository,
                   UserResourceAssembler assembler,
                   ClimaService climaService,
                   LugarService lugarService) {
        this.repository = repository;
        this.assembler = assembler;
        this.climaService = climaService;
        this.lugarService = lugarService;
    }

    /* Get de todos los usuarios */
    @GetMapping("/user")
    Resources<Resource<User>> all() {

        List<Resource<User>> User = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(User,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    /* Get de un usuario */
    @GetMapping("/user/{id}")
    Resource<User> one(@PathVariable Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toResource(user);
    }

    /* Get de todos los guardarropas del usuario */
    @GetMapping("/user/{id}/guardarropa")
    String allGuardarropas(@PathVariable Long id) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        List<Guardarropa> guardarropas = user.getGuardarropas();

        return gson.toJson(guardarropas);
    }

    /* Get de un atuendo aleatorio */
    @GetMapping("/user/{idUser}/guardarropa/{idGuard}/random")
    String one(@PathVariable Long idUser, @PathVariable Long idGuard) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = repository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));
        Optional<Guardarropa> guardarropaOptional = user.traerGuardarropasPorId(idGuard);
        Guardarropa guardarropa;

        if (guardarropaOptional.isPresent()) {
            guardarropa = guardarropaOptional.get();
            return gson.toJson(guardarropa.crearAtuendoAleatorio());
        } else {
            throw new GuardarropasNotFoundException(idGuard);
        }
    }

    /* Get de atuendos aleatorios de todos los guardarropas */
    @GetMapping("/user/{idUser}/guardarropa/random")
    String all(@PathVariable Long idUser) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = repository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));
        List<Guardarropa> guardarropas = user.getGuardarropas();

        return guardarropas.stream()
                .map(g -> {
                    Atuendo a;
                    a = g.crearAtuendoAleatorio();
                    return gson.toJson(a);
                })
                .collect(Collectors.toList()).toString();
    }

    /* Get de un atuendo para un evento */
    @GetMapping("/user/{idUser}/guardarropa/{idGuard}/{idEvento}/atuendo")
    String one(@PathVariable Long idUser, @PathVariable Long idGuard, @PathVariable Long idEvento) throws ClimateApisNotWorkingException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = repository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));
        Optional<Guardarropa> guardarropaOptional = user.traerGuardarropasPorId(idGuard);
        Guardarropa guardarropa;

        if (guardarropaOptional.isPresent()) {
            guardarropa = guardarropaOptional.get();

            Optional<Evento> eventoOptional = user.traerEventoPorId(idEvento);
            if(eventoOptional.isPresent()) {
                Evento evento = eventoOptional.get();

                /*TODO: lista de partes del cuerpo por variable*/
                return getAtuendo(gson, guardarropa, evento);
            }
            else {
                throw new EventoNotFoundException(idEvento);
            }
        } else {
            throw new GuardarropasNotFoundException(idGuard);
        }
    }

    /* Get de un atuendo por clima */
    @GetMapping("/user/{idUser}/guardarropa/{idGuard}/atuendo")
    String oneAtuendoClimaSinEvento(@PathVariable Long idUser, @PathVariable Long idGuard) throws ClimateApisNotWorkingException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = repository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));
        Optional<Guardarropa> guardarropaOptional = user.traerGuardarropasPorId(idGuard);
        Guardarropa guardarropa;

        if (guardarropaOptional.isPresent()) {
            guardarropa = guardarropaOptional.get();
            Evento evento = new Evento(LocalDateTime.now(), false);

            return getAtuendo(gson, guardarropa, evento);
        } else {
            throw new GuardarropasNotFoundException(idGuard);
        }
    }

    private String getAtuendo(Gson gson, Guardarropa guardarropa, Evento evento) throws ClimateApisNotWorkingException {
        /*TODO: lista de partes del cuerpo por variable*/
        List<PartesCuerpo> listaPartesDefault = new ArrayList<>();
        listaPartesDefault.add(PartesCuerpo.TORSO);
        listaPartesDefault.add(PartesCuerpo.PIERNAS);
        listaPartesDefault.add(PartesCuerpo.CABEZA);
        listaPartesDefault.add(PartesCuerpo.CALZADO);

        Clima clima = climaService.getClima();

        return gson.toJson(guardarropa.crearAtuendoClima(listaPartesDefault, clima, evento));
    }

    /* no se lo que hace */
    @GetMapping("/clima/{place}")
    String all(@PathVariable String place) throws ExecutionException, InterruptedException, ClimateApisNotWorkingException {
        Gson gson = new Gson();
        Lugar lugar = lugarService.getLugar(place);
        return gson.toJson(climaService.getClima(lugar));
    }

    /* Post de un usuario: creación de cuenta.*/
    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    /* Post de un guardarropas: creación de guardarropas para ese usuario.*/
    @PostMapping("/user/{id}/guardarropa")
    User newGuardarropaForUser(@RequestBody Guardarropa newGuardarropa, @PathVariable Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (user.getGuardarropas().contains(newGuardarropa)) {
            throw (new UserAlreadyHasGuardarropaException(newGuardarropa));
        } else {
            user.crearGuardarropas(newGuardarropa);
        }

        return repository.save(user);
    }

    @DeleteMapping("/user/{idUser}/guardarropa/{idGuardarropa}")
    void deleteGuardarropa(@PathVariable Long idUser, @PathVariable Long idGuardarropa) throws GuardarropasNotEmptyException {
        User user = repository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        if (user.traerGuardarropasPorId(idGuardarropa).isPresent()) {
            user.borrarGuardarropas(user.traerGuardarropasPorId(idGuardarropa).get());
        }
        ;

        repository.save(user);
    }

    @PostMapping("/user/{idUser}/guardarropa/{idGuardarropa}/prenda")
    User newPrendaForGuardarropas(@RequestBody Prenda prenda, @PathVariable Long idUser, @PathVariable Long idGuardarropa) {
        User user = repository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        if (user.traerGuardarropasPorId(idGuardarropa).isPresent()) {
            if (user.existePrendaEnAlgunGuardarropas(prenda)) {
                throw (new PrendaRepetidaException(prenda));
            } else {
                user.crearPrendaGuardarropas(prenda, idGuardarropa);
            }
        }

        return repository.save(user);
    }

    @DeleteMapping("/user/{idUser}/guardarropa/{idGuardarropa}/{idPrenda}")
    void deletePrendaFromGuardarropa(@PathVariable Long idUser, @PathVariable Long idGuardarropa, @PathVariable Long idPrenda) throws GuardarropasNotEmptyException {
        User user = repository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        user.traerGuardarropasPorId(idGuardarropa)
                .flatMap(guardarropa -> guardarropa.getPrenda(idPrenda))
                .ifPresent(prenda -> user.traerGuardarropasPorId(idGuardarropa)
                        .get()
                        .quitarPrenda(prenda));

        repository.save(user);
    }



    /*
    Innecesario. Se comenta para dejar el ejemplo cosa de usarlo en algun futuro si se necesita.
    Update de usuario.
    @PutMapping("/user/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return repository.findById(id)
                .map(User -> {
                    User.setUsername(newUser.getUsername());
                    return repository.save(User);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }
    */

    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}