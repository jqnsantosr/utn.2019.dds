package quemepongoAPI.evento;

import lombok.Data;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
public class Evento
{
    private @Id @GeneratedValue long id;
    private String nombre;
    private boolean esFormal;
    private Date fecha;

    public Evento(String date, boolean isFormal) throws FechaYHoraParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'a las' hh:mm");

        try
        {
            fecha = sdf.parse(date);
        }
        catch(ParseException e)
        {
            throw new FechaYHoraParseException(date);
        }

        esFormal = isFormal;
    }

    public boolean getEsFormal()
    {
        return esFormal;
    }
}
