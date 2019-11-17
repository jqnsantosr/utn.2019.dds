package quemepongoAPI.evento;

import lombok.Data;
import quemepongoAPI.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public boolean getEsFormal()
    {
        return esFormal;
    }

    public LocalDateTime getDateTime()
    {
        return fecha_notificacion;
    }

    public String getFechaEvento()
    {
        return fecha.format(formatter);
    }
}
