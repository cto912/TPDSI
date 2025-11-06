package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor

@Entity
@Table(name="tiposDeDato")
public class TipoDeDato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String denominacion;
    private String nombreUnidadMedida;
    private double valorUmbral;

    public TipoDeDato(String denominacion, String nombreUnidadMedida, double valorUmbral){
        this.denominacion = denominacion;
        this.nombreUnidadMedida = nombreUnidadMedida;
        this.valorUmbral = valorUmbral;
    }


}
