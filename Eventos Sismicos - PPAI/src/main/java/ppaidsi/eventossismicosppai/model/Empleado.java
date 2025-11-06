package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor

@Entity
@Table(name="empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    @OneToOne
    private Usuario usuario;

    public Empleado(String nombre, String apellido, String email, String telefono, Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.usuario = usuario;
    }

    public boolean esTuUsuario(String userName) {
        return userName.equals(usuario.getNombreUsuario());
    }
}
