package quemepongoAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Main {
    public static void main(String... args) throws ExecutionException, InterruptedException {

        SpringApplication.run(Main.class, args);

        //SOLO PARA TESTEAR QUE EL MENSAJE SE ENVIA EN TIEMPO Y FORMA
        /*User unUsuario = new User();
        Evento unEvento = new Evento("17/11/2019 a las 07:49 PM", true, unUsuario);*/
    }
}