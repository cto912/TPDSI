package ppaidsi.eventossismicosppai.model.state;

import ppaidsi.eventossismicosppai.model.CambioEstado;
import ppaidsi.eventossismicosppai.model.Empleado;
import ppaidsi.eventossismicosppai.model.EventoSismico;

import java.time.LocalDateTime;
import java.util.List;

public class BloqueadoEnRevision extends Estado{

    public BloqueadoEnRevision() {
        setNombre("BloqueadoEnRevision");
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
        return true;
    }

    @Override
    public boolean sosRechazado() {
        return false;
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
        for (CambioEstado cambioEstado : cambioEstadoList) {
            if (cambioEstado.esEstadoActual()){
                cambioEstado.setFechaHoraFin(fechaHoraActual);
                break;
            }
        }
        return new CambioEstado(estado, empleado, fechaHoraActual);
    }

    @Override
    public void rechazar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico, Empleado empleado) {
        Rechazado rechazado = crearEstadoRechazado();
        CambioEstado cambioEstado = crearCambioDeEstado(fechaHoraActual, cambioEstadoList, rechazado, empleado);
        eventoSismico.setEstado(rechazado);
        eventoSismico.addCambioDeEstado(cambioEstado);
    }

    @Override
    public Rechazado crearEstadoRechazado() {
        return new Rechazado();
    }

}
