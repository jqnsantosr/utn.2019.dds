package quemepongoAPI.user;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.prenda.Prenda;

import java.util.List;
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

        if(user.getGuardarropas().contains(newGuardarropa)){
            throw(new UserAlreadyHasGuardarropaException(newGuardarropa));
        } else {
            user.addGuardarropas(newGuardarropa);
        }

        return repository.save(user);
    }

    @PostMapping("/user/{idUser}/guardarropa/{idGuardarropa}/prenda")
    User newPrendaForGuardarropas(@RequestBody Prenda prenda, @PathVariable Long idUser, @PathVariable Long idGuardarropa) {
        User user = repository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));
        // TODO: validar que el guardarropas exista
        if(user.isPrendaInAnyGuardarropas(prenda)){
            throw(new PrendaRepetidaException(prenda));
        } else {
            user.addPrendaToGuardarropas(prenda, idGuardarropa);
        }

        return repository.save(user);
    }

    /*
    TODO:
        - Delete de prendas para un guardarropa especifico.
        - Delete de guardarropas, previa validada de que este vacío.
        - Endpoint de atuendos, con la correspondiente lógica
     */

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