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
        return habilitarPantalla();
    }

    public List<EventoSismicoDTO> habilitarPantalla(){
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

    public void tomarOpcionConfirmar(DatosSismicosDTO datosSismicosDTO) {
        gestor.tomarOpcionConfirmar(datosSismicosDTO);
    }

    public void tomarOpcionDerivar(DatosSismicosDTO datosSismicosDTO) {
        gestor.tomarOpcionDerivarES(datosSismicosDTO);
    }

    public boolean iniciarSesion(String userName, String password) {
        return gestor.iniciarSesion(userName, password);
    }

    public void cerrarSesion(String userName, String password) {
        gestor.cerrarSesion(userName, password);
    }

}
