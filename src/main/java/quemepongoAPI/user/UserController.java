package quemepongoAPI.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;
import quemepongoAPI.atuendo.Atuendo;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.Prenda;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
class UserController {

    private final UserRepository repository;
    private final UserResourceAssembler assembler;

    UserController(UserRepository repository, UserResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
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