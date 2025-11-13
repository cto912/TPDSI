package ppaidsi.eventossismicosppai.model.state;

import ppaidsi.eventossismicosppai.model.CambioEstado;
import ppaidsi.eventossismicosppai.model.EventoSismico;

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
    public void revisar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico){
        BloqueadoEnRevision bloqueadoEnRevision = crearEstadoBloqueadoEnRevision();
        CambioEstado cambioEstado = crearCambioDeEstado(fechaHoraActual, cambioEstadoList, bloqueadoEnRevision);
        eventoSismico.setEstado(bloqueadoEnRevision);
        eventoSismico.addCambioDeEstado(cambioEstado);
    }

    @Override
    public void controlarTiempo(){

    }

    public BloqueadoEnRevision crearEstadoBloqueadoEnRevision() {
        return new BloqueadoEnRevision();
    }

    public CambioEstado crearCambioDeEstado(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, Estado estado) {
        for (CambioEstado cambioEstado : cambioEstadoList) {
            if (cambioEstado.esEstadoActual()){
                cambioEstado.setFechaHoraFin(fechaHoraActual);
                break;
            }
        }
        return new CambioEstado(estado.getNombre(), null, fechaHoraActual);
    }

}
