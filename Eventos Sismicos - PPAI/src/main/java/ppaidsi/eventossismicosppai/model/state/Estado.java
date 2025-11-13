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

    public boolean sosAutoDetectado(){
        return false;
    }

    public boolean sosPendienteDeRevision(){
        return false;
    }

    public void revisar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico){
        throw new UnsupportedOperationException("No se puede revisar desde el estado: " + nombre);
    }

    public void rechazar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico, Empleado empleado){
        throw new UnsupportedOperationException("No se puede rechazar desde el estado: " + nombre);
    }

    public void confirmar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico, Empleado empleado){
        throw new UnsupportedOperationException("No se puede confirmar desde el estado: " + nombre);
    }

    public void derivar(LocalDateTime fechaHoraActual, List<CambioEstado> cambioEstadoList, EventoSismico eventoSismico, Empleado empleado){
        throw new UnsupportedOperationException("No se puede derivar desde el estado: " + nombre);
    }

    public void adquirirDatos(){
        throw new UnsupportedOperationException("No se puede adquirir datos desde el estado: " + nombre);
    }

    public void anular(){
        throw new UnsupportedOperationException("No se puede anular desde el estado: " + nombre);
    }

    public void controlarTiempo(){
        throw new UnsupportedOperationException("No se puede controlar el tiempo desde el estado: " + nombre);
    }

    public void cerrar(){
        throw new UnsupportedOperationException("No se puede cerrar desde el estado: " + nombre);
    }

    protected void setNombre(String nombre) {
        this.nombre = nombre;
    }

    protected void setAmbito(String ambito) {
        this.ambito = ambito;
    }

}
