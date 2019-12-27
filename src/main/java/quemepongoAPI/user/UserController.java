package quemepongoAPI.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
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
import quemepongoAPI.guardarropa.CantidadMaximaPrendaSuperadaException;
import quemepongoAPI.guardarropa.Guardarropa;
import quemepongoAPI.lugar.Lugar;
import quemepongoAPI.lugar.LugarService;
import quemepongoAPI.prenda.*;

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
    private final TipoPrendaRepository tipoPrendaRepository;
    private final UserResourceAssembler assembler;
    private final ClimaService climaService;
    private final LugarService lugarService;

    UserController(UserRepository repository,
                   TipoPrendaRepository tipoPrendaRepository,
                   UserResourceAssembler assembler,
                   ClimaService climaService,
                   LugarService lugarService) {
        this.repository = repository;
        this.tipoPrendaRepository = tipoPrendaRepository;
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
    @GetMapping("/user/guardarropa")
    String allGuardarropas(@RequestParam(name = "idToken", required = true) String idToken) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = verifyToken(idToken);

        List<Guardarropa> guardarropas = user.getGuardarropas();

        return gson.toJson(guardarropas);
    }

    /* Get de todos los eventos del usuario */
    @GetMapping("/user/evento")
    String allEventos(@RequestParam(name = "idToken", required = true) String idToken) throws Exception{
        final Long id = getUserIdFromToken(idToken);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return gson.toJson(user.getEventos());
    }

    /* Get de un atuendo aleatorio */
    @GetMapping("/user/guardarropa/{idGuard}/random")
    String one(@PathVariable Long idGuard,
               @RequestParam(name = "parts", required = false) String partesCuerpo,
               @RequestParam(name = "custom", required = false) boolean custom,
               @RequestParam(name = "idToken", required = true) String idToken) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = verifyToken(idToken);
        Optional<Guardarropa> guardarropaOptional = user.traerGuardarropasPorId(idGuard);
        Guardarropa guardarropa;

        if (guardarropaOptional.isPresent()) {
            guardarropa = guardarropaOptional.get();

            if(custom)
            {
                final String[] listaPartesAsArray = partesCuerpo.split("-");
                final List<PartesCuerpo> listaPartes = new ArrayList<>();
                for(final String parte : listaPartesAsArray){
                    listaPartes.add(PartesCuerpo.fromInt(Integer.parseInt(parte)));
                }
                return gson.toJson(guardarropa.crearAtuendoAleatorio(listaPartes));
            }
            else
                return gson.toJson(guardarropa.crearAtuendoAleatorio());
        } else {
            throw new GuardarropasNotFoundException(idGuard);
        }
    }

    /* Get de un atuendo por clima */
    @GetMapping("/user/guardarropa/{idGuard}/atuendo")
    String oneAtuendoClimaSinEvento(@PathVariable Long idGuard,
                                    @RequestParam(name = "parts", required = false) String partesCuerpo,
                                    @RequestParam(name = "custom", required = false) boolean custom,
                                    @RequestParam(name = "idToken", required = true) String idToken) throws Exception{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = verifyToken(idToken);
        Optional<Guardarropa> guardarropaOptional = user.traerGuardarropasPorId(idGuard);
        Guardarropa guardarropa;

        if (guardarropaOptional.isPresent()) {
            guardarropa = guardarropaOptional.get();
            Evento evento = new Evento(LocalDateTime.now(), false);

            if(custom)
            {
                final String[] listaPartesAsArray = partesCuerpo.split("-");
                final List<PartesCuerpo> listaPartes = new ArrayList<>();
                for(final String parte : listaPartesAsArray){
                    listaPartes.add(PartesCuerpo.fromInt(Integer.parseInt(parte)));
                }

                Clima clima = climaService.getClima();

                return gson.toJson(guardarropa.crearAtuendoClima(listaPartes, clima, evento));
            }
            return getAtuendo(gson, guardarropa, evento);
        } else {
            throw new GuardarropasNotFoundException(idGuard);
        }
    }

    /* Get de atuendos aleatorios de todos los guardarropas */
    @GetMapping("/user/guardarropa/random")
    String all(@RequestParam(name = "parts", required = false) String partesCuerpo,
               @RequestParam(name = "custom", required = false) boolean custom,
               @RequestParam(name = "idToken", required = true) String idToken) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = verifyToken(idToken);
        List<Guardarropa> guardarropas = user.getGuardarropas();

        final List<PartesCuerpo> listaPartes = new ArrayList<>();
        if(custom)
        {
            final String[] listaPartesAsArray = partesCuerpo.split("-");
            for(final String parte : listaPartesAsArray){
                listaPartes.add(PartesCuerpo.fromInt(Integer.parseInt(parte)));
            }
        }
        else
        {
            listaPartes.add(PartesCuerpo.TORSO);
            listaPartes.add(PartesCuerpo.PIERNAS);
            listaPartes.add(PartesCuerpo.CABEZA);
            listaPartes.add(PartesCuerpo.CALZADO);
        }

        return guardarropas.stream()
                .map(g -> {
                    Atuendo a;
                    a = g.crearAtuendoAleatorio(listaPartes);
                    return gson.toJson(a);
                })
                .collect(Collectors.toList()).toString();
    }

    /* Get de un atuendo para un evento */
    @GetMapping("/user/guardarropa/{idGuard}/evento/{idEvento}/atuendo")
    String one(@PathVariable Long idGuard, @PathVariable Long idEvento,
               @RequestParam(name = "idToken", required = true) String idToken) throws Exception{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = verifyToken(idToken);
        Optional<Guardarropa> guardarropaOptional = user.traerGuardarropasPorId(idGuard);
        Guardarropa guardarropa;

        if (guardarropaOptional.isPresent()) {
            guardarropa = guardarropaOptional.get();

            Optional<Evento> eventoOptional = user.traerEventoPorId(idEvento);
            if(eventoOptional.isPresent()) {
                Evento evento = eventoOptional.get();

                return getAtuendo(gson, guardarropa, evento);
            }
            else {
                throw new EventoNotFoundException(idEvento);
            }
        } else {
            throw new GuardarropasNotFoundException(idGuard);
        }
    }

    private String getAtuendo(Gson gson, Guardarropa guardarropa, Evento evento) throws ClimateApisNotWorkingException {
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

    /* Get de todas las telas */
    @GetMapping("/telas")
    String allTelas() {
        Gson gson = new Gson();
        return gson.toJson(Tela.values());
    }

    /* Get de todas las partes del cuerpo */
    @GetMapping("/partes")
    String allPartesCuerpo() {
        Gson gson = new Gson();
        return gson.toJson(PartesCuerpo.values());
    }

    /* Get de todas los tipos de prendas */
    @GetMapping("/tipos")
    String allTipoPrendas() {
        Gson gson = new Gson();
        return gson.toJson(tipoPrendaRepository.findAll());
    }

    /*
    private boolean verifyToken(final String idToken){
        try{
            GoogleIdToken.Payload payload = GoogleIdVerifier.getPayload(idToken);
        } catch(final Exception e){
            return false;
        }
        return true;
    }*/

    /* Post de un usuario: google id token.*/
    @PostMapping("/signIn")
    User userSignIn(@RequestParam String idToken) throws Exception {
        return verifyToken(idToken);
    }

    private User verifyToken(final String idToken) throws Exception {
        GoogleIdToken.Payload payload = GoogleIdVerifier.getPayload(idToken);

        String userId = payload.getSubject();
        System.out.println("User ID: " + userId);
        String email = payload.getEmail();

        Optional<User> user = repository.findByEmail(email);
        if(user.isPresent())
            return repository.save(user.get());
        else {
            //Nuevo Usuario
            User nuevoUser = new User((String)payload.get("name"), email);
            return repository.save(nuevoUser);
        }
    }

    private Long getUserIdFromToken(final String idToken) throws Exception{
        return verifyToken(idToken).getId();
    }

    /* Post de un usuario: creación de cuenta.*/
    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    /* Post de un usuario: creación de cuenta.*/
    @PostMapping("/user/premium")
    User userPremium(@RequestParam(name = "idToken", required = true) String idToken) throws Exception {
        User user = verifyToken(idToken);
        user.PasarAPremium();

        return repository.save(user);
    }

    /* Post de un guardarropas: creación de guardarropas para ese usuario.*/
    @PostMapping("/user/guardarropa")
    User newGuardarropaForUser(@RequestParam(name = "idToken", required = true) String idToken, @RequestBody Guardarropa newGuardarropa) throws Exception {
        User user = verifyToken(idToken);

        if (user.getGuardarropas().contains(newGuardarropa)) {
            throw (new UserAlreadyHasGuardarropaException(newGuardarropa));
        } else {
            user.crearGuardarropas(newGuardarropa);
        }

        return repository.save(user);
    }

    /* Post de un prenda: creación de prendas para ese guardarropas.*/
    @PostMapping("/user/guardarropa/{idGuardarropa}/prenda")
    User newPrendaForGuardarropas(@RequestBody JsonNode prendaAsJsonNode,
                                  @PathVariable Long idUser,
                                  @PathVariable Long idGuardarropa,
                                  @RequestParam(name = "idToken", required = true) String idToken) throws Exception{
        final Optional<TipoPrenda> tipo = tipoPrendaRepository.findById(prendaAsJsonNode.get("tipo").asLong());
        Prenda prenda = new Prenda(prendaAsJsonNode,tipo.get());
        User user = verifyToken(idToken);

        if (user.traerGuardarropasPorId(idGuardarropa).isPresent()) {
            if (user.existePrendaEnAlgunGuardarropas(prenda)) {
                throw (new PrendaRepetidaException(prenda));
            } else {
                if(!user.puedeAgregarPrenda(idGuardarropa))
                    throw (new CantidadMaximaPrendaSuperadaException("Guardarropas Lleno, Compre Premium"));
                user.crearPrendaGuardarropas(prenda, idGuardarropa);
            }
        }

        return repository.save(user);
    }

    @PostMapping("/user/evento")
    User newEventoForUser(@RequestBody JsonNode newEventoAsJsonNode, @RequestParam(name = "idToken", required = true) String idToken) throws Exception {
        final User user = verifyToken(idToken);
        final Evento newEvento = new Evento(newEventoAsJsonNode, user);

        user.crearEvento(newEvento);

        return repository.save(user);
    }

    /*Aceptar la sugerencia de un evento*/
    @PostMapping("/user/evento/{idEvento}/aceptar")
    User aceptarAtuendoEventoForUser(@RequestBody Atuendo atuendo, @PathVariable Long idEvento, @RequestParam(name = "idToken", required = true) String idToken) throws Exception{
        final User user = verifyToken(idToken);

        Optional<Evento> eventoOptional = user.traerEventoPorId(idEvento);
        if(eventoOptional.isPresent()) {
            Evento evento = eventoOptional.get();

            evento.aceptarAtuendo(atuendo);
        }

        return repository.save(user);
    }

    /*PATCH de modificar nombre guardarropa*/
    /*EJEMPLO:
        type : 'PATCH',
        url : url,
        contentType: 'application/x-www-form-urlencoded',
        data: "nombre=Guardarropa 69"
     */
    @PatchMapping("/user/guardarropa/{idGuarda}/mod")
    User modificarGuardarropa(@RequestParam("nombre") String nombre, @PathVariable Long idGuarda,@RequestParam(name = "idToken", required = true) String idToken)throws Exception {
        final User user = verifyToken(idToken);

        Optional<Guardarropa> guardarropaOptional = user.traerGuardarropasPorId(idGuarda);
        Guardarropa guardarropa;

        if (guardarropaOptional.isPresent()) {
            guardarropa = guardarropaOptional.get();

            guardarropa.setNombre(nombre);

            return repository.save(user);
        } else {
            throw new GuardarropasNotFoundException(idGuarda);
        }
    }

    @DeleteMapping("/user/guardarropa/{idGuardarropa}")
    void deleteGuardarropa(@RequestParam(name = "idToken", required = true) String idToken, @PathVariable Long idGuardarropa) throws Exception {
        final User user = verifyToken(idToken);

        if (user.traerGuardarropasPorId(idGuardarropa).isPresent()) {
            user.borrarGuardarropas(user.traerGuardarropasPorId(idGuardarropa).get());
        }

        repository.save(user);
    }

    @DeleteMapping("/user/guardarropa/{idGuardarropa}/prenda/{idPrenda}")
    void deletePrendaFromGuardarropa(@RequestParam(name = "idToken", required = true) String idToken, @PathVariable Long idGuardarropa, @PathVariable Long idPrenda) throws Exception {
        final User user = verifyToken(idToken);

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