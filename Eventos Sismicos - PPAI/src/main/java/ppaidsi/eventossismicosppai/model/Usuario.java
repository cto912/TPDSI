package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor

@Entity
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String password;
    private String nombreUsuario;

    public Usuario(String password, String nombreUsuario) {
        this.password = password;
        this.nombreUsuario = nombreUsuario;
    }
}
