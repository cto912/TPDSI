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
    public void rechazar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico, Empleado empleado) {
        Rechazado rechazado = crearEstadoRechazado();
        CambioEstado cambioEstado = crearCambioDeEstado(fechaHoraActual, cambioEstadoList, rechazado, empleado);
        eventoSismico.setEstado(rechazado);
        eventoSismico.addCambioDeEstado(cambioEstado);
    }

    @Override
    public void confirmar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico, Empleado empleado) {
        ConfirmadoPorPersonal confirmadoPorPersonal = crearEstadoConfirmarPorPersonal();
        CambioEstado cambioEstado = crearCambioDeEstado(fechaHoraActual, cambioEstadoList, confirmadoPorPersonal, empleado);
        eventoSismico.setEstado(confirmadoPorPersonal);
        eventoSismico.addCambioDeEstado(cambioEstado);
    }


    @Override
    public void derivar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico, Empleado empleado){
        Derivado derivado = crearEstadoDerivado();
        CambioEstado cambioEstado = crearCambioDeEstado(fechaHoraActual, cambioEstadoList, derivado, empleado);
        eventoSismico.setEstado(derivado);
        eventoSismico.addCambioDeEstado(cambioEstado);
    }

    public CambioEstado crearCambioDeEstado(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, Estado estado, Empleado empleado) {
        for (CambioEstado cambioEstado : cambioEstadoList) {
            if (cambioEstado.esEstadoActual()){
                cambioEstado.setFechaHoraFin(fechaHoraActual);
                break;
            }
        }
        return new CambioEstado(estado.getNombre(), empleado, fechaHoraActual);
    }

    public Derivado crearEstadoDerivado() {
        return new Derivado();
    }

    public Rechazado crearEstadoRechazado() {
        return new Rechazado();
    }

    public ConfirmadoPorPersonal crearEstadoConfirmarPorPersonal() {
        return new ConfirmadoPorPersonal();
    }

}
