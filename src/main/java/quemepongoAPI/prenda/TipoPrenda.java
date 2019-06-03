package quemepongoAPI.prenda;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class TipoPrenda {

    private @Id @GeneratedValue Long id;
    private String nombre;
    @ElementCollection
    private List<Tela> telasPosibles;
    @ElementCollection
    private List<PartesCuerpo> partesCuerpo;
    private Boolean esAbrigo;

}
