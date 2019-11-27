package quemepongoAPI.evento;

import lombok.Data;
import quemepongoAPI.user.User;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

@Data
@Entity
public class Evento
{
    private @Id @GeneratedValue Long id;
    private String nombre;
    private boolean esFormal;
    @Transient
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' hh:mm a");
    private LocalDateTime fecha;
    private LocalDateTime fecha_notificacion;

    public Evento(String date, boolean isFormal){
        fecha = LocalDateTime.parse(date, formatter);
        esFormal = isFormal;
    }

    public Evento(String date, boolean isFormal, User usuario){
        fecha = LocalDateTime.parse(date, formatter);
        esFormal = isFormal;
        fecha_notificacion = fecha.minusHours(1);
        EventoObserver unObserver = new EventoObserver(usuario, this);
    }

    public Evento(String nombre, String fecha_evento, String fecha_notificacion, boolean isFormal, User usuario){
        this.nombre = nombre;
        fecha = LocalDateTime.parse(fecha_evento, formatter);
        esFormal = isFormal;
        this.fecha_notificacion = LocalDateTime.parse(fecha_notificacion, formatter);
        EventoObserver unObserver = new EventoObserver(usuario, this);
    }

    public boolean getEsFormal()
    {
        return esFormal;
    }

    public String getFechaEvento()
    {
        return fecha.format(formatter);
    }
}
