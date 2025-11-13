package ppaidsi.eventossismicosppai.model.state;

import ppaidsi.eventossismicosppai.model.CambioEstado;
import ppaidsi.eventossismicosppai.model.Empleado;
import ppaidsi.eventossismicosppai.model.EventoSismico;

import java.time.LocalDateTime;
import java.util.List;

public class Derivado extends Estado{

    public Derivado(){
        setNombre("Derivado");
        setAmbito("Evento Sismico");
    }

    @Override
    public void rechazar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico, Empleado empleado){

    }

    @Override
    public void confirmar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico, Empleado empleado){

    }
}
