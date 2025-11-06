package ppaidsi.eventossismicosppai.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor

@Entity
@Table(name="detallesMuestraSismica")
public class DetalleMuestraSismica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double valor;

    @ManyToOne()
    @JoinColumn(name = "tiposDeDato_id")
    private TipoDeDato tipoDeDato;

    public DetalleMuestraSismica(double valor, TipoDeDato tipoDeDato) {
        this.valor = valor;
        this.tipoDeDato = tipoDeDato;
    }

    public List<Object> getDatos(){
        List<Object> datos = new ArrayList<>();
        datos.add(valor);
        datos.add(tipoDeDato.getDenominacion());
        return datos;
    }



}
