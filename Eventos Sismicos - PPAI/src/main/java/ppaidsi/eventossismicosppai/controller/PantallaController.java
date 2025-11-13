package ppaidsi.eventossismicosppai.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppaidsi.eventossismicosppai.DTO.DatosSismicosDTO;
import ppaidsi.eventossismicosppai.DTO.EventoSismicoDTO;
import ppaidsi.eventossismicosppai.DTO.SeriesTemporalesDTO;
import ppaidsi.eventossismicosppai.DTO.UsuarioDTO;
import ppaidsi.eventossismicosppai.model.AlcanceSismo;
import ppaidsi.eventossismicosppai.model.ClasificacionSismo;
import ppaidsi.eventossismicosppai.model.OrigenDeGeneracion;
import ppaidsi.eventossismicosppai.model.PantallaEventoSismico;
import ppaidsi.eventossismicosppai.service.AlcanceSismoService;
import ppaidsi.eventossismicosppai.service.ClasificacionSisimoService;
import ppaidsi.eventossismicosppai.service.OrigenDeGeneracionService;


import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/pantalla")
public class PantallaController {


    private final PantallaEventoSismico pantallaEventoSismico;
    private final ClasificacionSisimoService clasificacionSisimoService;
    private final AlcanceSismoService alcanceSismoService;
    private final OrigenDeGeneracionService origenDeGeneracionService;

    @Autowired
    public PantallaController( PantallaEventoSismico pantallaEventoSismico,
                               ClasificacionSisimoService clasificacionSisimoService,
                               AlcanceSismoService alcanceSismoService,
                               OrigenDeGeneracionService origenDeGeneracionService) {
        this.pantallaEventoSismico = pantallaEventoSismico;
        this.clasificacionSisimoService = clasificacionSisimoService;
        this.alcanceSismoService = alcanceSismoService;
        this.origenDeGeneracionService = origenDeGeneracionService;
    }

    @GetMapping("/getESRev")
    public ResponseEntity<List<EventoSismicoDTO>> getESRev() {
        return ResponseEntity.ok(pantallaEventoSismico.opcionRegResRevMan());
    }


    @PostMapping("/postESRev")
    public  ResponseEntity<String> recibirEventoSismico(@RequestBody EventoSismicoDTO eventoSismicoDTO) {
        pantallaEventoSismico.tomarSeleccionES(eventoSismicoDTO.getId());
        return ResponseEntity.ok("Evento recibido correctamente");
    }

    @GetMapping("/getDatosSismicos")
    public ResponseEntity<DatosSismicosDTO> getDatosSismicos() {
        return ResponseEntity.ok(pantallaEventoSismico.mostarDatosSismicos());
    }

    @GetMapping("/getAllClasificacion")
    public ResponseEntity<List<ClasificacionSismo>> getAllClasificacion() {
        return ResponseEntity.ok(clasificacionSisimoService.getAll());
    }

    @GetMapping("/getAllAlcance")
    public ResponseEntity<List<AlcanceSismo>> getAllAlcance() {
        return ResponseEntity.ok(alcanceSismoService.getAll());
    }

    @GetMapping("/getAllOrigenDeGeneracion")
    public ResponseEntity<List<OrigenDeGeneracion>> getAllOrigenDeGeneracion() {
        return ResponseEntity.ok(origenDeGeneracionService.getAll());
    }

    @PutMapping("/rechazarEvento")
    public ResponseEntity<String> rechazarEvento(@RequestBody DatosSismicosDTO datosSismicoDTO) {
        pantallaEventoSismico.tomarOpcionRechazarEvento(datosSismicoDTO);
        return ResponseEntity.ok("Rechazo recibido correctamente");
    }

    @DeleteMapping("/cancelar")
    public ResponseEntity<String> cancelar(){
        pantallaEventoSismico.tomarOpcionCancelar();
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/iniciarSesion")
    public ResponseEntity<String> iniciarSesion(@RequestBody UsuarioDTO usuarioDTO){
        if (pantallaEventoSismico.iniciarSesion(usuarioDTO.getUserName(), usuarioDTO.getPassword())){
            return ResponseEntity.ok("Ok");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }

    @PutMapping("/cerrarSesion")
    public ResponseEntity<String> cerrarSesion(@RequestBody UsuarioDTO usuarioDTO){
        pantallaEventoSismico.cerrarSesion(usuarioDTO.getUserName(), usuarioDTO.getPassword());
        return ResponseEntity.ok("Ok");
    }

    @PutMapping("/confirmarEvento")
    public ResponseEntity<String> confirmarEvento(@RequestBody DatosSismicosDTO datosSismicoDTO) {
        pantallaEventoSismico.tomarOpcionConfirmar(datosSismicoDTO);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/derivarEvento")
    public ResponseEntity<String> derivarEvento(@RequestBody DatosSismicosDTO datosSismicoDTO) {
        pantallaEventoSismico.tomarOpcionDerivar(datosSismicoDTO);
        return ResponseEntity.ok("ok");
    }


}
