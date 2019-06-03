package quemepongoAPI.prenda;

import lombok.Data;

import javax.persistence.*;

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

    Prenda(String nombre, TipoPrenda tipo, String colorPrimario){
        this.nombre = nombre;
        this.tipo = tipo;
        this.colorPrimario = colorPrimario;
    }

    Prenda(String nombre, TipoPrenda tipo, String colorPrimario, String colorSecundario){
        this.nombre = nombre;
        this.tipo = tipo;
        this.colorPrimario = colorPrimario;
        this.colorSecundario = colorSecundario;
    }
}
