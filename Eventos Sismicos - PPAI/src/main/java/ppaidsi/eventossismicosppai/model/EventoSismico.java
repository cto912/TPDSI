package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor

@Entity
@Table(name="eventosSismicos")
public class EventoSismico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHoraOcurrencia;
    private double latitudEpicentro;
    private double longitudEpicentro;
    private double latitudHipocentro;
    private double longitudHipocentro;
    private double valorMagnitud;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "seriesTemporal_id")
    private List<SerieTemporal> serieTemporal = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "clasificacionesSismo_id")
    private ClasificacionSismo clasificacionSismo;

    @ManyToOne()
    @JoinColumn(name = "alcancesSismocs_id")
    private AlcanceSismo alcanceSismo;

    @ManyToOne()
    @JoinColumn(name = "origenesDeGeneracion")
    private OrigenDeGeneracion origenDeGeneracion;

    @ManyToOne()
    @JoinColumn(name = "estados_id")
    private Estado estadoActual;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cambiosEstado_id")
    private List<CambioEstado> cambioEstado = new ArrayList<>();

    public EventoSismico(LocalDateTime fechaHoraFin, LocalDateTime fechaHoraOcurrencia, double LatitudEpicentro, double LongitudEpicentro,
                         double latitudHipocentro, double longitudHipocentro, double valorMagnitud, ClasificacionSismo clasificacionSismo,
                         AlcanceSismo alcanceSismo, OrigenDeGeneracion origenDeGeneracion, Estado estado, List<SerieTemporal> serieTemporal) {
        this.fechaHoraFin = fechaHoraFin;
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
        this.latitudEpicentro = LatitudEpicentro;
        this.longitudEpicentro = LongitudEpicentro;
        this.latitudHipocentro = latitudHipocentro;
        this.longitudHipocentro = longitudHipocentro;
        this.valorMagnitud = valorMagnitud;
        this.clasificacionSismo = clasificacionSismo;
        this.alcanceSismo = alcanceSismo;
        this.origenDeGeneracion = origenDeGeneracion;
        this.estadoActual = estado;
        this.serieTemporal = serieTemporal;
        this.crearCambioEstado(fechaHoraFin, estado);
    }

    public void crearCambioEstado(LocalDateTime fechaHoraInicio, Estado estado){
        CambioEstado cambioEstado = new CambioEstado(estado, fechaHoraInicio);
        this.cambioEstado.add(cambioEstado);
    }

    public void crearCambioEstado(LocalDateTime fechaHoraInicio, Estado estado, Empleado empleado){
        CambioEstado cambioEstado = new CambioEstado(estado, empleado ,fechaHoraInicio);
        this.cambioEstado.add(cambioEstado);
    }

    public boolean estasAutoDetectado(){
        return estadoActual.sosAutoDetectado();
    }

    public boolean estasPendienteDeRevision(){
        return estadoActual.sosPendienteDeRevision();
    }

    public void bloquear(Estado estado, LocalDateTime fechaHoraActual){
        setEstadoActual(estado);

        for (CambioEstado cambioEstado1 : this.cambioEstado) {
            if (cambioEstado1.esEstadoActual()){
                cambioEstado1.setFechaHoraFin(fechaHoraActual);
            }
        }

        crearCambioEstado(estado, fechaHoraActual);
    }

    public void crearCambioEstado(Estado estado, LocalDateTime fechaHoraInicio){
        CambioEstado cambioEstado = new CambioEstado(estado, fechaHoraInicio);
        this.cambioEstado.add(cambioEstado);
    }

    public String toString(){
        return  "Evento sismico Id: " + id + "\n" +
                "Fecha y hora de ocurrencia: " + fechaHoraOcurrencia + "\n" +
                "Valor de magnitud: " + valorMagnitud;

    }

    public String getAlcance(){
        return  alcanceSismo.getNombre();
    }

    public String getClasificacion(){
        return clasificacionSismo.getNombre();
    }

    public String getOrigen(){
        return origenDeGeneracion.getNombre();
    }

    public void setAsPendienteDeRevision(Estado estado, LocalDateTime fechaHoraActual){
        setEstadoActual(estado);

        for (CambioEstado cambioEstado1 : this.cambioEstado) {
            if (cambioEstado1.esEstadoActual()){
                cambioEstado1.setFechaHoraFin(fechaHoraActual);
            }
        }
        crearCambioEstado(estado, fechaHoraActual);
    }

    public void obtenerSeriesTemporales(){
        for (SerieTemporal serieTemporal : serieTemporal) {
            serieTemporal.getDatos();
        }
    }

    public void rechazar(Estado estado, LocalDateTime fechaHoraActual, Empleado analista){
        setEstadoActual(estado);
        for (CambioEstado cambioEstado1 : this.cambioEstado) {
            if (cambioEstado1.esEstadoActual()){
                cambioEstado1.setFechaHoraFin(fechaHoraActual);
            }
        }
        crearCambioEstado(fechaHoraActual, estado, analista);
    }



}
