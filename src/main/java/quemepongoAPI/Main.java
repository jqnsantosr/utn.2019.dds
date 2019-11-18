package quemepongoAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quemepongoAPI.evento.Evento;
import quemepongoAPI.user.User;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Main {
    public static void main(String... args) throws ExecutionException, InterruptedException {

        SpringApplication.run(Main.class, args);

        //SOLO PARA TESTEAR QUE EL MENSAJE SE ENVIA EN TIEMPO Y FORMA
        /*User unUsuario = new User("Ferna", "googleID", "1158257317", true);
        Evento unEvento = new Evento("Cumple Pepe", "18/11/2019 a las 03:30 PM","18/11/2019 a las 01:35 PM", true, unUsuario);*/
    }
}