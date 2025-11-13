package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor

@Entity
@Table(name="alcancesSismo")
public class AlcanceSismo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descripcion;
    private String nombre;

    public AlcanceSismo(String descripcion, String nombre) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

}
