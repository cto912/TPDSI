package ppaidsi.eventossismicosppai.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ppaidsi.eventossismicosppai.DTO.DatosSismicosDTO;
import ppaidsi.eventossismicosppai.DTO.EventoSismicoDTO;
import ppaidsi.eventossismicosppai.DTO.SeriesTemporalesDTO;

import java.util.*;

@Data
@Component
public class PantallaEventoSismico {

    private final GestorEventoSismico gestor;
    private DatosSismicosDTO datosSismicosDTO;

    @Autowired
    public PantallaEventoSismico(GestorEventoSismico gestor) {
        this.gestor = gestor;
    }

    public List<EventoSismicoDTO> opcionRegResRevMan() {
        return gestor.opcionRegResRevMan();
    }

    public void tomarSeleccionES(int idEvento) {
        datosSismicosDTO = gestor.tomarSeleccionES(idEvento);
    }

    public DatosSismicosDTO mostarDatosSismicos(){
        return datosSismicosDTO;
    }

    public void tomarOpcionRechazarEvento(DatosSismicosDTO datosSismicosDTO) {
        gestor.tomarOpcionRechazarEvento(datosSismicosDTO);
    }

    public void tomarOpcionCancelar(){
        gestor.tomarOpcionCancelar();
    }

    public boolean iniciarSesion(String userName, String password) {
        return gestor.iniciarSesion(userName, password);
    }

    public void cerrarSesion(String userName, String password) {
        gestor.cerrarSesion(userName, password);
    }

    public List<SeriesTemporalesDTO> getSeriesTemporales(){
        return gestor.recorrerSeriesTemporales();
    }
}
