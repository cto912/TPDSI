package ppaidsi.eventossismicosppai.model.state;

import lombok.*;
import ppaidsi.eventossismicosppai.model.CambioEstado;
import ppaidsi.eventossismicosppai.model.Empleado;
import ppaidsi.eventossismicosppai.model.EventoSismico;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Estado {

    @Getter private String nombre;
    @Getter private String ambito;

    protected Estado() {
    }

    public abstract boolean sosAutoDetectado();

    public abstract boolean sosPendienteDeRevision();

    public abstract boolean sosBloqueadoEnRevision();

    public abstract boolean sosRechazado();

    public abstract void revisar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico);

    public abstract BloqueadoEnRevision crearEstadoBloqueadoEnRevision();

    public abstract CambioEstado crearCambioDeEstado(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, Estado estado);

    public abstract CambioEstado crearCambioDeEstado(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, Estado estado, Empleado empleado);

    public abstract void rechazar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico, Empleado empleado);

    public abstract Rechazado crearEstadoRechazado();



    protected void setNombre(String nombre) {
        this.nombre = nombre;
    }

    protected void setAmbito(String ambito) {
        this.ambito = ambito;
    }

}
