package ppaidsi.eventossismicosppai.model.state;

public class EstadoFactory {

    public static Estado crear(String nombre){
        return switch (nombre) {
            case "AutoDetectado" -> new AutoDetectado();
            case "PendienteDeRevision" -> new PendienteDeRevision();
            case "BloqueadoEnRevision" -> new BloqueadoEnRevision();
            case "Rechazado" -> new Rechazado();
            case "ConfirmadoPorPersonal" -> new ConfirmadoPorPersonal();
            case "Derivado" -> new Derivado();
            case "AutoConfirmado" -> new AutoConfirmado();
            case "Cerrado" -> new Cerrado();
            case "PendienteDeCierre" -> new PendienteDeCierre();
            case "SinRevision" -> new SinRevision();
            default -> throw new IllegalArgumentException("El nombre no es valido");
        };
    }
}
