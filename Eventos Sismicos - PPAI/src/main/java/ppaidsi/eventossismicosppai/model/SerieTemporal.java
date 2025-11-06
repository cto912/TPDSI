package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor

@Entity
@Table(name = "seriesTemporal")
public class SerieTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean condicionAlarma;
    private LocalDateTime fechaHoraInicioRegistroMuestras;
    private LocalDateTime fechaHoraRegistro;
    private double frecuenciaMuestreo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "muestrasSismicas_Id")
    private List<MuestraSismica> muestraSismica = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "sismografos_id")
    private Sismografo sismografo;

    public SerieTemporal(boolean condicionAlarma, LocalDateTime fechaHoraInicioRegistroMuestras, LocalDateTime fechaHoraRegistro,
                         double frecuenciaMuestreo, Sismografo sismografo, List<MuestraSismica> muestraSismica) {
        this.condicionAlarma = condicionAlarma;
        this.fechaHoraInicioRegistroMuestras = fechaHoraInicioRegistroMuestras;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.frecuenciaMuestreo = frecuenciaMuestreo;
        this.sismografo = sismografo;
        this.muestraSismica = muestraSismica;
    }

    public void addMuestraSismica(MuestraSismica muestraSismica){
        this.muestraSismica.add(muestraSismica);
    }

    public void getDatos(){
        List<Object> datos = new ArrayList<>();
        datos.add(id);
        datos.add(condicionAlarma);
        datos.add(fechaHoraRegistro);
        datos.add(frecuenciaMuestreo);
        for (MuestraSismica muestraSismica : muestraSismica) {
            datos.add(muestraSismica.getDatos());
        }
    }

}
