package ppaidsi.eventossismicosppai.service;

import org.springframework.stereotype.Service;
import ppaidsi.eventossismicosppai.model.Sesion;
import ppaidsi.eventossismicosppai.repository.SesionRepo;

import java.util.List;

@Service
public class SesionService {
    private SesionRepo sesionRepo;

    public SesionService(SesionRepo sesionRepo) {
        this.sesionRepo = sesionRepo;
    }

    public void save(Sesion sesion) {
        sesionRepo.save(sesion);
    }

    public List<Sesion> getAll(){
        return sesionRepo.findAll();
    }
}
