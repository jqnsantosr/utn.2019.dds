package quemepongoAPI.prenda;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Prenda {

    private @Id
    @GeneratedValue
    Long id;
    private String nombre;
    @ManyToOne(cascade = {CascadeType.ALL})
    private TipoPrenda tipo;
    private String colorPrimario;
    private String colorSecundario;

    Prenda(){}

    // TODO: el constructor de prenda deberia validar el tipo. Los tipos deberían venir de una lista estática.

    public Prenda(String nombre, TipoPrenda tipo, String colorPrimario){
        this.nombre = nombre;
        this.tipo = tipo;
        this.colorPrimario = colorPrimario;
    }

    public Prenda(String nombre, TipoPrenda tipo, String colorPrimario, String colorSecundario){
        this.nombre = nombre;
        this.tipo = tipo;
        this.colorPrimario = colorPrimario;
        this.colorSecundario = colorSecundario;
    }

    public List<PartesCuerpo> damePartesCuerpo()
    {
        return this.tipo.damePartesDelCuerpo();
    }

    public boolean tieneMasDeUnaParte()
    {
        return this.tipo.cantidadPartes() > 1;
    }
}
