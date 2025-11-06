package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor

@Entity
@Table(name="origenesDeGeneracion")
public class OrigenDeGeneracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descripcion;
    private String nombre;

    public OrigenDeGeneracion(String descripcion, String nombre) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }
}
