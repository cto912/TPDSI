package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

@Entity
@Table(name="sesiones")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime fechaHoraFinSesion;
    private LocalDateTime fechaHoraInicioSesion;

    @ManyToOne
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    public Sesion(LocalDateTime fechaHoraInicioSesion, Usuario usuario) {
        this.fechaHoraInicioSesion = fechaHoraInicioSesion;
        this.usuario = usuario;
    }

    public boolean esSesionActual(){
        return this.getFechaHoraFinSesion() == null;
    }

    public String conocerUsuario(){
        return usuario.getNombreUsuario();
    }


}
