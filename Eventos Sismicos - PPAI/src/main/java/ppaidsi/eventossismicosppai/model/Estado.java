package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor

@Entity
@Table(name="estados")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String ambito;

    public Estado(String nombre, String ambito) {
        this.nombre = nombre;
        this.ambito = ambito;
    }

    public boolean sosAutoDetectado(){
        return nombre.equals("AutoDetectado");
    }

    public boolean sosPendienteDeRevision(){
        return nombre.equals("PendienteDeRevision");
    }

    public boolean sosBloqueadoEnRevision(){ return nombre.equals("BloqueadoEnRevision");}

    public boolean sosRechazado(){ return nombre.equals("Rechazado");}
}
