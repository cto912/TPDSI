package ppaidsi.eventossismicosppai.model;

import org.springframework.stereotype.Component;
import ppaidsi.eventossismicosppai.DTO.DatosSismicosDTO;
import ppaidsi.eventossismicosppai.DTO.EventoSismicoDTO;
import ppaidsi.eventossismicosppai.service.*;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class GestorEventoSismico {

    private final EventoSismicoService eventosSismicoService;
    private final EstadoService estadoService;
    private final UsuarioService usuarioService;
    private final SesionService sesionService;
    private final EmpleadoService empleadoService;

    private List<EventoSismico>eventosSismicos;
    private List<Estado>estados;
    private Estado estado;
    private LocalDateTime fechaHoraActual;
    private EventoSismico eventoSismicoSeleccionado;
    private String nombreAlcance;
    private String nombreClasificacion;
    private String nombreOrigen;
    private List<Usuario> usuarios;
    private Sesion sesionActual;
    private String userNameActual;
    private Empleado analistaLogueado;

    public GestorEventoSismico(EventoSismicoService eventosSismicoService, EstadoService estadoService,
                               UsuarioService usuarioService, SesionService sesionService,
                               EmpleadoService empleadoService) {
        this.eventosSismicoService = eventosSismicoService;
        this.estadoService = estadoService;
        this.usuarioService = usuarioService;
        this.sesionService = sesionService;
        this.empleadoService = empleadoService;


        this.eventosSismicos = new ArrayList<>();
        this.fechaHoraActual = LocalDateTime.now();
    }

    public void buscarEventosSismicos(List<EventoSismico> allEventosSismicos) {
        eventosSismicos = new ArrayList<>();
        eventosSismicos.addAll(
                allEventosSismicos.stream()
                        .filter(eventoSismico -> eventoSismico.estasAutoDetectado() || eventoSismico.estasPendienteDeRevision())
                        .toList()
        );
        this.ordenarPorFechaHoraOcurrencia(eventosSismicos);
        eventosSismicos.forEach(eventosSismico -> {System.out.println(eventosSismico.toString());});
    }

    public void ordenarPorFechaHoraOcurrencia(List<EventoSismico> eventosSismicos) {
        eventosSismicos.sort(Comparator.comparing(EventoSismico::getFechaHoraOcurrencia).reversed());
    }

    public List<EventoSismicoDTO> opcionRegResRevMan(){
        List<EventoSismicoDTO> infoMostrar = new ArrayList<>();
        List<EventoSismico> allEventosSismicos = eventosSismicoService.getAll();
        this.buscarEventosSismicos(allEventosSismicos);
        eventosSismicos.forEach(eventosSismico -> {
            int id = eventosSismico.getId();
            LocalDateTime fechaHoraOcurrencia = eventosSismico.getFechaHoraOcurrencia();
            double latitudEpicentro = eventosSismico.getLatitudEpicentro();
            double longitudEpicentro = eventosSismico.getLongitudEpicentro();
            double latitudHipocentro = eventosSismico.getLatitudHipocentro();
            double longitudHipocentro = eventosSismico.getLongitudHipocentro();
            double valorMagnitud = eventosSismico.getValorMagnitud();
            EventoSismicoDTO eventosSismicoDTO = new EventoSismicoDTO(id, fechaHoraOcurrencia, latitudEpicentro,
                                                                        longitudEpicentro, latitudHipocentro, longitudHipocentro,
                                                                        valorMagnitud);
            infoMostrar.add(eventosSismicoDTO);
        });
        return infoMostrar;
    }

    public DatosSismicosDTO tomarSeleccionES(int idEvento){
        this.eventoSismicoSeleccionado = eventosSismicos.stream()
                                                        .filter(e -> e.getId() == idEvento)
                                                        .findFirst()
                                                        .orElse(null);

        bloquearEs(eventoSismicoSeleccionado);
        buscarDatosSismicos();
        return new DatosSismicosDTO(eventoSismicoSeleccionado.getId(), nombreAlcance, nombreClasificacion, nombreOrigen);
    }

    public void bloquearEs(EventoSismico eventosSismico){
        buscarEstadoBloqueadoEnRevision();
        getFechaHoraActual();
        eventosSismico.bloquear(estado, fechaHoraActual);
        eventosSismicoService.save(eventosSismico);
    }

    public void buscarEstadoBloqueadoEnRevision(){
        this.estados = estadoService.getAll();
        estados.forEach(estado -> {
            if (estado.sosBloqueadoEnRevision()){
                this.estado=estado;
            }
        });
    }

    public void getFechaHoraActual(){
        fechaHoraActual=LocalDateTime.now();
    }

    public void buscarDatosSismicos(){
        nombreAlcance = eventoSismicoSeleccionado.getAlcance();
        nombreClasificacion = eventoSismicoSeleccionado.getClasificacion();
        nombreOrigen = eventoSismicoSeleccionado.getOrigen();
    }

    public void recorrerSeriesTemporales(){
    }

    public void tomarOpcionCancelar(){

    }

    public void liberarES(){
        buscarEstadoPendienteDeRevision();
        getFechaHoraActual();
        this.eventoSismicoSeleccionado.setAsPendienteDeRevision(estado, fechaHoraActual);
        eventosSismicoService.save(eventoSismicoSeleccionado);
    }

    public void buscarEstadoPendienteDeRevision(){
        this.estados = estadoService.getAll();
        estados.forEach(estado -> {
            if (estado.sosPendienteDeRevision()){
                this.estado=estado;
            }
        });
    }

    public void tomarOpcionRechazarEvento(DatosSismicosDTO datosSismicosDTO){
        validarDatos(datosSismicosDTO);
        rechazarES();
    }

    public void validarDatos(DatosSismicosDTO datosSismicosDTO){
    }

    public void rechazarES(){
        buscarEstadoRechazado();
        getFechaHoraActual();
        buscarAnalistaLogueado();
        eventoSismicoSeleccionado.rechazar(estado, fechaHoraActual, analistaLogueado);
        eventosSismicoService.save(eventoSismicoSeleccionado);
    }

    public void buscarEstadoRechazado(){
        this.estados = estadoService.getAll();
        estados.forEach(estado -> {
            if (estado.sosRechazado()){
                this.estado=estado;
            }
        });
    }

    public boolean iniciarSesion(String username, String password){
        usuarios = usuarioService.getAll();
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(username) && usuario.getPassword().equals(password)){
                getFechaHoraActual();
                Sesion sesion = new Sesion(fechaHoraActual, usuario);
                sesionService.save(sesion);
                return true;
            }
        }
        return false;
    }

    public void cerrarSesion(String userName, String password){
        List<Sesion> sesiones = sesionService.getAll();
        getFechaHoraActual();

        for (Sesion sesion : sesiones) {
            Usuario usuario = sesion.getUsuario();
            if (usuario.getNombreUsuario().equals(userName) && usuario.getPassword().equals(password)){
                if (sesion.getFechaHoraFinSesion() == null){
                    sesion.setFechaHoraFinSesion(fechaHoraActual);
                    sesionService.save(sesion);
                    break;
                }
            }
        }
    }

    public void buscarAnalistaLogueado(){
        List<Empleado> empleados = empleadoService.getAll();
        buscarSesionActual();
        buscarUsuario();

        for (Empleado empleado : empleados) {
            if (empleado.esTuUsuario(userNameActual)){
                analistaLogueado=empleado;
            }
        }
    }

    public void buscarSesionActual(){
        List<Sesion> sesiones = sesionService.getAll();
        for (Sesion sesion : sesiones) {
            if (sesion.esSesionActual()){
                sesionActual=sesion;
            }
        }
    }

    public void buscarUsuario(){
        userNameActual = sesionActual.conocerUsuario();
    }

}
