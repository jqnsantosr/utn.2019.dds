package quemepongoAPI.user;

import org.hibernate.internal.log.ConnectionAccessLogger;
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

import java.sql.*;
import java.util.Calendar;

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
    Atuendo one(@PathVariable Long idUser, @PathVariable Long idGuard) {
        User user = repository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));
        Optional<Guardarropa> guardarropaOptional = user.getGuardarropasById(idGuard);
        Guardarropa guardarropa;

        if (guardarropaOptional.isPresent())
        {
            guardarropa = guardarropaOptional.get();

            return guardarropa.crearAtuendoAleatorio();
        }
        else
        {
            throw new GuardarropasNotFoundException(idGuard);
        }
    }

    /* Post de un usuario: creación de cuenta.*/
    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {

        try {
            String host = "jdbc:mysql://localhost:3306/quemepongo";
            String uName = "root";
            String uPass = "procopio";
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            String strusername = "username";
            String query = " insert into usuarios (usuarios_id, nombre, fecha_alta, fecha_modificacion)"
                    + " values (?, ?, ?, ?)";

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, 3);
            preparedStmt.setString (2, strusername);
            preparedStmt.setDate   (3, startDate);
            preparedStmt.setDate(4, startDate);

            preparedStmt.execute();

            con.close();


        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
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