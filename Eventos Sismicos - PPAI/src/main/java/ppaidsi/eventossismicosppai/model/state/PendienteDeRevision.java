package ppaidsi.eventossismicosppai.model.state;

import ppaidsi.eventossismicosppai.model.CambioEstado;
import ppaidsi.eventossismicosppai.model.EventoSismico;

import java.time.LocalDateTime;
import java.util.List;

public class PendienteDeRevision extends Estado{

    public PendienteDeRevision() {
        setNombre("PendienteDeRevision");
        setAmbito("Evento sismico");
    }

    @Override
    public boolean sosPendienteDeRevision() {
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

    @Override
    public void anular(){

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

    public BloqueadoEnRevision crearEstadoBloqueadoEnRevision() {
        return new BloqueadoEnRevision();
    }

}
