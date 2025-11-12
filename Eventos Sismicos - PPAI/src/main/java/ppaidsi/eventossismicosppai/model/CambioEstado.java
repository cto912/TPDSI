package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;
import ppaidsi.eventossismicosppai.model.state.Estado;
import ppaidsi.eventossismicosppai.model.state.EstadoFactory;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor

@Entity
@Table(name="cambiosEstado")
public class CambioEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHoraInicio;

    @Transient
    private Estado estado;

    @Column(name = "estado", nullable = false)
    private String nombreEstado;

    @ManyToOne()
    @JoinColumn(name = "empleados_id", nullable = true)
    private Empleado empleado;

    public CambioEstado(Estado estado, Empleado empleado, LocalDateTime fechaHoraInicio) {
        this.estado = estado;
        this.nombreEstado = estado.getNombre();
        this.empleado = empleado;
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public CambioEstado(Estado estado, LocalDateTime fechaHoraInicio) {
        this.estado = estado;
        this.nombreEstado = estado.getNombre();
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public CambioEstado(String nombreEstado, Empleado empleado, LocalDateTime fechaHoraInicio) {
        this.nombreEstado = nombreEstado;
        this.estado = EstadoFactory.crear(nombreEstado);
        this.fechaHoraInicio = fechaHoraInicio;
        this.empleado = empleado;
    }

    public CambioEstado(String nombreEstado, LocalDateTime fechaHoraInicio) {
        this.nombreEstado = nombreEstado;
        this.estado = EstadoFactory.crear(nombreEstado);
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public boolean esEstadoActual(){
        return this.fechaHoraFin == null;
    }
}
