package quemepongoAPI.evento;

import quemepongoAPI.user.User;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventoObserver implements Runnable {
    private User user;
    private Evento evento;
    private static ScheduledExecutorService ses = Executors.newScheduledThreadPool(10); //Maximo de hilos posibles
    public static final String ACCOUNT_SID = "AC803578d89db1117e5c7551b9cc9ac298";
    public static final String AUTH_TOKEN = "96c1005c0624315b0b6c28a97c3cb575";

    public EventoObserver(User usuario, Evento evento){
        this.user = usuario;
        this.evento = evento;

        LocalDateTime fecha_evento = evento.getFecha_notificacion();
        LocalDateTime fecha_hoy = LocalDateTime.now();
        long tiempoHastaEvento = Duration.between(fecha_hoy, fecha_evento).getSeconds();

        ses.schedule(this, tiempoHastaEvento, TimeUnit.SECONDS);
    }

    public void run(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("whatsapp:+549" + user.getNumeroCelular()),
                new PhoneNumber("whatsapp:+14155238886"),
                "El Evento " + evento.getNombre()  + " está por Comenzar")
                .create();

        System.out.println( "SE ENVIO UN MENSAJE AL USUARIO: " + user.getNombre() + ", DEL EVENTO: " + evento.getFechaEvento());
    }
}
