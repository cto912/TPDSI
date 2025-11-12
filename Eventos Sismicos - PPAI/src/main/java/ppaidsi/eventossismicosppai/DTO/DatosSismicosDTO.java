package ppaidsi.eventossismicosppai.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DatosSismicosDTO {
    private int id;
    private String alcance;
    private String clasificacion;
    private String origen;
    private double magnitud;
}
