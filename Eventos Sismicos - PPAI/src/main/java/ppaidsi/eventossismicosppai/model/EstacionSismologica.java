package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor

@Entity
@Table(name = "estacionesSismologicas")
public class EstacionSismologica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String codigoEstacion;
    private String documentoCertificacionAdq;
    private LocalDateTime fechaSolicitudCertificacion;
    private double latitud;
    private double longitud;
    private String name;
    private int nroCertificacionAdquisicion;

    public EstacionSismologica(String codigoEstacion, String documentoCertificacionAdq, LocalDateTime fechaSolicitudCertificacion,
                               double latitud, double longitud, String name, int nroCertificacionAdquisicion){
        this.codigoEstacion = codigoEstacion;
        this.documentoCertificacionAdq = documentoCertificacionAdq;
        this.fechaSolicitudCertificacion = fechaSolicitudCertificacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.name = name;
        this.nroCertificacionAdquisicion = nroCertificacionAdquisicion;
    }
}
