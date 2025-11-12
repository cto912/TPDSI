package ppaidsi.eventossismicosppai.model.state;

public class EstadoFactory {

    public static Estado crear(String nombre){
        return switch (nombre) {
            case "AutoDetectado" -> new AutoDetectado();
            case "PendienteDeRevision" -> new PendienteDeRevision();
            case "BloqueadoEnRevision" -> new BloqueadoEnRevision();
            case "Rechazado" -> new Rechazado();
            default -> throw new IllegalArgumentException("El nombre no es valido");
        };
    }
}
