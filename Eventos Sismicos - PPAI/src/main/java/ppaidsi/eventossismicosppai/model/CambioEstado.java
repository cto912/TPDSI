package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;
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

    @ManyToOne()
    @JoinColumn(name = "estados_id")
    private Estado estado;

    @ManyToOne()
    @JoinColumn(name = "empleados_id", nullable = true)
    private Empleado empleado;

    public CambioEstado(Estado estado, Empleado empleado, LocalDateTime fechaHoraInicio) {
        this.estado = estado;
        this.empleado = empleado;
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public CambioEstado(Estado estado, LocalDateTime fechaHoraInicio) {
        this.estado = estado;
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public boolean esEstadoActual(){
        return this.fechaHoraFin == null;
    }
}
