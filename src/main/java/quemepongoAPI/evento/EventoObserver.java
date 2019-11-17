package quemepongoAPI.evento;

import quemepongoAPI.user.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventoObserver implements Runnable {
    private User user;
    private Evento evento;
    private static ScheduledExecutorService ses = Executors.newScheduledThreadPool(10); //Maximo de hilos posibles

    public EventoObserver(User usuario, Evento evento){
        this.user = usuario;
        this.evento = evento;

        LocalDateTime fecha_evento = evento.getDateTime();
        LocalDateTime fecha_hoy = LocalDateTime.now();
        long tiempoHastaEvento = Duration.between(fecha_hoy, fecha_evento).getSeconds();

        ses.schedule(this, tiempoHastaEvento, TimeUnit.SECONDS);
    }

    public void run(){
        //TODO: Integrar con API de Whatsapp
        System.out.println( "HI, YOUR EVENT IS ABOUT TO START AT " + evento.getFechaEvento());
    }
}
