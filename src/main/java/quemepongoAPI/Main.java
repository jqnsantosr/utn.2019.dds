package quemepongoAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Main {
    public static void main(String... args) throws ExecutionException, InterruptedException {

        SpringApplication.run(Main.class, args);
    }
}