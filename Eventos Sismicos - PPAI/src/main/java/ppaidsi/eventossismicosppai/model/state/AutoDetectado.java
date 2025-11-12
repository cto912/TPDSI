package ppaidsi.eventossismicosppai.model.state;

import ppaidsi.eventossismicosppai.model.CambioEstado;
import ppaidsi.eventossismicosppai.model.Empleado;
import ppaidsi.eventossismicosppai.model.EventoSismico;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class AutoDetectado extends Estado {

    public AutoDetectado() {
        setNombre("AutoDetectado");
        setAmbito("Evento sismico");
    }

    @Override
    public boolean sosAutoDetectado() {
        return true;
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
        return false;
    }

    @Override
    public void revisar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico){
        BloqueadoEnRevision bloqueadoEnRevision = crearEstadoBloqueadoEnRevision();
        CambioEstado cambioEstado = crearCambioDeEstado(fechaHoraActual, cambioEstadoList, bloqueadoEnRevision);
        eventoSismico.setEstado(bloqueadoEnRevision);
        eventoSismico.addCambioDeEstado(cambioEstado);
    }

    @Override
    public BloqueadoEnRevision crearEstadoBloqueadoEnRevision() {
        return new BloqueadoEnRevision();
    }

    @Override
    public CambioEstado crearCambioDeEstado(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, Estado estado) {
        for (CambioEstado cambioEstado : cambioEstadoList) {
            if (cambioEstado.esEstadoActual()){
                cambioEstado.setFechaHoraFin(fechaHoraActual);
                break;
            }
        }
        return new CambioEstado(estado, fechaHoraActual);
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
