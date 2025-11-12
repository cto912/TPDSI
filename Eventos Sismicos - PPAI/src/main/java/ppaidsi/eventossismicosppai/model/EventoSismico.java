package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;
import ppaidsi.eventossismicosppai.DTO.SeriesTemporalesDTO;
import ppaidsi.eventossismicosppai.model.state.Estado;
import ppaidsi.eventossismicosppai.model.state.EstadoFactory;

import java.time.Duration;
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

    @Transient
    private Estado estadoActual;

    @Column(name = "estado", nullable = false)
    private String estadoNombre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cambiosEstado_id")
    private List<CambioEstado> cambioEstado = new ArrayList<>();

    public EventoSismico(LocalDateTime fechaHoraFin, LocalDateTime fechaHoraOcurrencia, double LatitudEpicentro, double LongitudEpicentro,
                         double latitudHipocentro, double longitudHipocentro, double valorMagnitud, ClasificacionSismo clasificacionSismo,
                         AlcanceSismo alcanceSismo, OrigenDeGeneracion origenDeGeneracion, String nombreEstado, List<SerieTemporal> serieTemporal) {
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
        this.estadoNombre = nombreEstado;
        this.estadoActual = EstadoFactory.crear(nombreEstado);
        this.serieTemporal = serieTemporal;
        this.crearCambioEstado(this.fechaHoraFin, this.estadoActual);
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

    public void revisar(LocalDateTime fechaHoraActual){
        estadoActual.revisar(fechaHoraActual, this.cambioEstado, this);
    }

    public void addCambioDeEstado(CambioEstado cambioEstado){
        this.cambioEstado.add(cambioEstado);
    }

    public void crearCambioEstado(Estado estado, LocalDateTime fechaHoraInicio){
        CambioEstado cambioEstado = new CambioEstado(estado, fechaHoraInicio);
        this.cambioEstado.add(cambioEstado);
    }

    public String toString(){
        return  "Evento sismico Id: " + id + "\n" +
                "Fecha y hora de ocurrencia: " + fechaHoraOcurrencia + "\n" +
                "Valor de magnitud: " + valorMagnitud + "\n" +
                "Estado: " + estadoNombre;

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

    public List<SeriesTemporalesDTO> obtenerSeriesTemporales(){
        List<SeriesTemporalesDTO> seriesTemporales = new ArrayList<>();
        for (SerieTemporal serieTemporal : serieTemporal) {
            seriesTemporales.add(serieTemporal.getDatos());
        }
        return seriesTemporales;
    }

    public List<Object> obtenerEstacionSismologica(){
        List<Object> datos = new ArrayList<>();
        for (SerieTemporal serieTemporal : serieTemporal) {
            datos.add(serieTemporal.obtenerEstacionSismologica());
        }
        return datos;
    }

    public void rechazar(LocalDateTime fechaHoraActual, Empleado analista){
        estadoActual.rechazar(fechaHoraActual, this.cambioEstado, this, analista);
    }

    public void setEstado(Estado estado){
        this.estadoActual = estado;
        this.estadoNombre = estado.getNombre();
    }

    public Estado getEstado(){
        if (estadoActual == null){
            estadoActual = EstadoFactory.crear(estadoNombre);
        }
        return estadoActual;
    }


    @PostLoad
    private void initEstado(){
        if (estadoActual == null && estadoNombre != null) {
            estadoActual = EstadoFactory.crear(estadoNombre);
        }
    }

}
