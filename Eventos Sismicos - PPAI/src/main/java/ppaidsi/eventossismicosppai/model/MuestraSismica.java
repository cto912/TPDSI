package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor

@Entity
@Table(name="muestrasSismicas")
public class MuestraSismica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime fechaHoraMuestra;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "detallesMuestraSismica_id")
    private List<DetalleMuestraSismica> detalleMuestraSismica = new ArrayList<>();

    public MuestraSismica(LocalDateTime fechaHoraMuestra, List<DetalleMuestraSismica> detalleMuestraSismica) {
        this.fechaHoraMuestra = fechaHoraMuestra;
        this.detalleMuestraSismica = detalleMuestraSismica;
    }

    public void addDetalleMuestraSismica(DetalleMuestraSismica detalleMuestraSismica){
        this.detalleMuestraSismica.add(detalleMuestraSismica);
    }

    public List<Object> getDatos(){
        List<Object> datos = new ArrayList<>();
        datos.add(fechaHoraMuestra);
        for (DetalleMuestraSismica detalleMuestraSismica :detalleMuestraSismica){
            datos.add(detalleMuestraSismica.getDatos());
        }
        return datos;
    }

}
