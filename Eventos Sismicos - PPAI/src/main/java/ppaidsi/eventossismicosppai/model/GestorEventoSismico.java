package ppaidsi.eventossismicosppai.model;

import org.springframework.stereotype.Component;
import ppaidsi.eventossismicosppai.DTO.DatosSismicosDTO;
import ppaidsi.eventossismicosppai.DTO.EventoSismicoDTO;
import ppaidsi.eventossismicosppai.DTO.SeriesTemporalesDTO;
import ppaidsi.eventossismicosppai.model.state.Estado;
import ppaidsi.eventossismicosppai.model.state.EstadoFactory;
import ppaidsi.eventossismicosppai.service.*;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class GestorEventoSismico {

    private final EventoSismicoService eventosSismicoService;
    private final UsuarioService usuarioService;
    private final SesionService sesionService;
    private final EmpleadoService empleadoService;
    private final AlcanceSismoService alcanceSismoService;
    private final OrigenDeGeneracionService origenDeGeneracionService;


    private List<EventoSismico>eventosSismicos;
    private LocalDateTime fechaHoraActual;
    private EventoSismico eventoSismicoSeleccionado;
    private String nombreAlcance;
    private String nombreClasificacion;
    private String nombreOrigen;
    private Sesion sesionActual;
    private String userNameActual;
    private Empleado analistaLogueado;
    private int idEventoSismicoSeleccionado;

    private String nombreEstadoAnterior; //SOLUCION CANCELACION DE CU

    public GestorEventoSismico(EventoSismicoService eventosSismicoService,
                               UsuarioService usuarioService, SesionService sesionService,
                               EmpleadoService empleadoService, AlcanceSismoService alcanceSismoService,
                               OrigenDeGeneracionService origenDeGeneracionService) {
        this.eventosSismicoService = eventosSismicoService;
        this.usuarioService = usuarioService;
        this.sesionService = sesionService;
        this.empleadoService = empleadoService;
        this.alcanceSismoService = alcanceSismoService;
        this.origenDeGeneracionService = origenDeGeneracionService;


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
    }

    public void ordenarPorFechaHoraOcurrencia(List<EventoSismico> eventosSismicos) {
        eventosSismicos.sort(Comparator.comparing(EventoSismico::getFechaHoraOcurrencia).reversed());
    }

    public List<EventoSismicoDTO> opcionRegResRevMan(){
        List<EventoSismicoDTO> infoMostrar = new ArrayList<>();
        List<EventoSismico> allEventosSismicos = eventosSismicoService.getAll();

        buscarEventosSismicos(allEventosSismicos);

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

    public void getFechaHoraActual(){
        fechaHoraActual=LocalDateTime.now();
    }

    public void generarSismograma(){
        //NO HACE NADA
    }

    public void buscarDatosSismicos(){
        nombreAlcance = eventoSismicoSeleccionado.getAlcance();
        nombreClasificacion = eventoSismicoSeleccionado.getClasificacion();
        nombreOrigen = eventoSismicoSeleccionado.getOrigen();
    }

    public List<SeriesTemporalesDTO> recorrerSeriesTemporales(){
        List<SeriesTemporalesDTO> seriesTemporales = eventoSismicoSeleccionado.obtenerSeriesTemporales();
        seriesTemporales = clasificarPorEstacionSismologica(seriesTemporales);
        return seriesTemporales;
    }

    public List<SeriesTemporalesDTO> clasificarPorEstacionSismologica(List<SeriesTemporalesDTO> seriesTemporales){
        seriesTemporales.sort(Comparator.comparing(SeriesTemporalesDTO::getCodigoEstacion));
        return seriesTemporales;
    }

    //FALTA ESTA PARTE
    public void tomarOpcionCancelar(){
        this.eventoSismicoSeleccionado = eventosSismicoService.getById(idEventoSismicoSeleccionado);
        cancelarCU();
    }

    public void cancelarCU(){
        Estado estado = EstadoFactory.crear(nombreEstadoAnterior);
        List<CambioEstado> cambioEstadoList = eventoSismicoSeleccionado.getCambioEstado();
        CambioEstado actual = cambioEstadoList.getLast();
        cambioEstadoList.remove(actual);
        CambioEstado anterior = cambioEstadoList.getLast();
        anterior.setFechaHoraFin(null);
        eventoSismicoSeleccionado.setEstado(estado);
        eventosSismicoService.save(eventoSismicoSeleccionado);
    }

    public void validarDatos(DatosSismicosDTO datosSismicosDTO){
        List<AlcanceSismo> alcanceSismosList = alcanceSismoService.getAll();
        List<OrigenDeGeneracion> origenDeGeneracionList = origenDeGeneracionService.getAll();
        AlcanceSismo alcanceSismo = null;
        OrigenDeGeneracion origenDeGeneracion = null;
        for (AlcanceSismo elem : alcanceSismosList) {
            if (elem.getNombre().equals(datosSismicosDTO.getAlcance())){
                alcanceSismo = elem;
                break;
            }
        }
        for (OrigenDeGeneracion elem : origenDeGeneracionList) {
            if (elem.getNombre().equals(datosSismicosDTO.getOrigen())){
                origenDeGeneracion = elem;
                break;
            }
        }
        if (alcanceSismo != null && origenDeGeneracion != null){
            eventoSismicoSeleccionado.setAlcanceSismo(alcanceSismo);
            eventoSismicoSeleccionado.setOrigenDeGeneracion(origenDeGeneracion);
            eventoSismicoSeleccionado.setValorMagnitud(datosSismicosDTO.getMagnitud());
        }else {
            System.out.println("ERROR! ALCANCE Y/O ORIGEN NO EXISTE");
        }
    }

    public boolean iniciarSesion(String username, String password){
        List<Usuario> usuarios = usuarioService.getAll();
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

    public void tomarOpcionConfirmar(DatosSismicosDTO datosSismicosDTO){
        this.eventoSismicoSeleccionado = eventosSismicoService.getById(idEventoSismicoSeleccionado);
        validarDatos(datosSismicosDTO);
        confirmarES();
    }

    public void tomarOpcionDerivarES(DatosSismicosDTO datosSismicosDTO){
        this.eventoSismicoSeleccionado = eventosSismicoService.getById(idEventoSismicoSeleccionado);
        validarDatos(datosSismicosDTO);
        derivarES();
    }

    public void tomarOpcionRechazarEvento(DatosSismicosDTO datosSismicosDTO){
        this.eventoSismicoSeleccionado = eventosSismicoService.getById(idEventoSismicoSeleccionado);
        validarDatos(datosSismicosDTO);
        rechazarES();
    }

    public DatosSismicosDTO tomarSeleccionES(int idEvento){
        this.idEventoSismicoSeleccionado = idEvento;
        this.eventoSismicoSeleccionado = eventosSismicos.stream()
                .filter(e -> e.getId() == idEvento)
                .findFirst()
                .orElse(null);
        assert eventoSismicoSeleccionado != null;
        nombreEstadoAnterior = eventoSismicoSeleccionado.getEstadoNombre(); //SOLUCION CANCELACION DE CU
        bloquearEs(eventoSismicoSeleccionado);
        buscarDatosSismicos();
        List<SeriesTemporalesDTO> seriesTemporales = recorrerSeriesTemporales();
        generarSismograma();
        return new DatosSismicosDTO(eventoSismicoSeleccionado.getId(), nombreAlcance, nombreClasificacion, nombreOrigen, 0, seriesTemporales);
    }

    public void confirmarES(){
        getFechaHoraActual();
        buscarAnalistaLogueado();
        eventoSismicoSeleccionado.confirmar(fechaHoraActual, analistaLogueado);
        eventosSismicoService.save(eventoSismicoSeleccionado);
    }

    public void derivarES(){
        getFechaHoraActual();
        buscarAnalistaLogueado();
        eventoSismicoSeleccionado.derivar(fechaHoraActual, analistaLogueado);
        eventosSismicoService.save(eventoSismicoSeleccionado);
    }

    public void rechazarES(){
        getFechaHoraActual();
        buscarAnalistaLogueado();
        eventoSismicoSeleccionado.rechazar(fechaHoraActual, analistaLogueado);
        eventosSismicoService.save(eventoSismicoSeleccionado);
    }

    public void bloquearEs(EventoSismico eventosSismico){
        getFechaHoraActual();
        eventosSismico.revisar(fechaHoraActual);
        eventosSismicoService.save(eventosSismico);
    }

}
