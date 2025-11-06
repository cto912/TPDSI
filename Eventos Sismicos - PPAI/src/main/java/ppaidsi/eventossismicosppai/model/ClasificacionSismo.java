package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor

@Entity
@Table(name="clasificacionesSismo")
public class ClasificacionSismo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double kmProfundidadDesde;
    private double kmProfundidadHasta;
    private String nombre;

    public ClasificacionSismo(double kmProfundidadDesde, double kmProfundidadHasta, String nombre){
        this.kmProfundidadDesde = kmProfundidadDesde;
        this.kmProfundidadHasta = kmProfundidadHasta;
        this.nombre = nombre;
    }
}
