package ppaidsi.eventossismicosppai.model.state;

import ppaidsi.eventossismicosppai.model.CambioEstado;
import ppaidsi.eventossismicosppai.model.Empleado;
import ppaidsi.eventossismicosppai.model.EventoSismico;

import java.time.LocalDateTime;
import java.util.List;

public class Rechazado extends Estado {

    public Rechazado() {
        setNombre("Rechazado");
        setAmbito("Evento sismico");
    }

    @Override
    public boolean sosAutoDetectado() {
        return false;
    }

    @Override
    public boolean sosPendienteDeRevision() {
        return false;
    }

    @Override
    public boolean sosBloqueadoEnRevision() {
        return false;
    }

    @Override
    public boolean sosRechazado() {
        return true;
    }

    @Override
    public void revisar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico) {

    }

    @Override
    public BloqueadoEnRevision crearEstadoBloqueadoEnRevision() {
        return null;
    }

    @Override
    public CambioEstado crearCambioDeEstado(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, Estado estado) {
        return null;
    }

    @Override
    public CambioEstado crearCambioDeEstado(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, Estado estado, Empleado empleado) {
        return null;
    }

    @Override
    public void rechazar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico, Empleado empleado) {

    }

    @Override
    public Rechazado crearEstadoRechazado() {
        return null;
    }
}
