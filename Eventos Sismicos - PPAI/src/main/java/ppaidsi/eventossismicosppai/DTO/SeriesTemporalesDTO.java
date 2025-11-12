package ppaidsi.eventossismicosppai.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SeriesTemporalesDTO {
    private int id;
    private String codigoEstacion;
    private boolean condicionAlarma;
    private LocalDateTime fechaHoraRegistro;
    private double frecuenciaMuestreo;
    private List<Object> muestras;
}
