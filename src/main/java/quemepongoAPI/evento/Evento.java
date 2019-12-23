package quemepongoAPI.evento;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import quemepongoAPI.atuendo.Atuendo;
import quemepongoAPI.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Slf4j
public class Evento
{
    private @Id @GeneratedValue Long id;
    private String nombre;
    private boolean esFormal;
    @Transient
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' hh:mm a");
    private LocalDateTime fecha;
    private LocalDateTime fecha_notificacion;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn (name = "idAtuendo")
    private Atuendo atuendo;

    public Evento(final JsonNode jn, User usuario){
        this.nombre = jn.get("nombre").asText();
        this.fecha = LocalDateTime.parse(jn.get("fecha").asText(), formatter);
        this.fecha_notificacion = LocalDateTime.parse(jn.get("fecha_notificacion").asText(), formatter);
        this.esFormal = jn.get("esFormal").asBoolean();
        EventoObserver unObserver = new EventoObserver(usuario, this);
    }

    public Evento(String date, boolean isFormal){
        fecha = LocalDateTime.parse(date, formatter);
        esFormal = isFormal;
    }

    public Evento(LocalDateTime date, boolean isFormal){
        fecha = date;
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

    public void aceptarAtuendo(Atuendo unAtuendo){
        this.atuendo = unAtuendo;
        log.info("Se aceptó una sugerencia para el evento: " + this.id);
    }
}
