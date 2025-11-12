package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

@Entity
@Table(name = "sismografos")
public class Sismografo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime fechaAdquisicion;
    private String identificadorSismografo;
    private String nroSerie;

    @ManyToOne()
    @JoinColumn(name = "estacionesSismologicas_id")
    private EstacionSismologica estacionSismologica;

    public Sismografo(LocalDateTime fechaAdquisicion, String identificadorSismografo, String nroSerie, EstacionSismologica estacionSismologica){
        this.fechaAdquisicion = fechaAdquisicion;
        this.identificadorSismografo = identificadorSismografo;
        this.nroSerie = nroSerie;
        this.estacionSismologica = estacionSismologica;
    }

    public String getEstacionSismologica(){
        return estacionSismologica.getCodigoEstacion();
    }

}
