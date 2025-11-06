package ppaidsi.eventossismicosppai.service;

import org.springframework.stereotype.Service;
import ppaidsi.eventossismicosppai.model.Empleado;
import ppaidsi.eventossismicosppai.repository.EmpleadoRepo;

import java.util.List;

@Service
public class EmpleadoService {
    private final EmpleadoRepo empleadoRepo;

    public EmpleadoService(EmpleadoRepo empleadoRepo) {
        this.empleadoRepo = empleadoRepo;
    }

    public List<Empleado> getAll(){
        return empleadoRepo.findAll();
    }
}
