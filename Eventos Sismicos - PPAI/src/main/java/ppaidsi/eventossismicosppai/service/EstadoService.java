package ppaidsi.eventossismicosppai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppaidsi.eventossismicosppai.model.Estado;
import ppaidsi.eventossismicosppai.repository.EstadoRepo;

import java.util.List;

@Service
public class EstadoService {
    private final EstadoRepo estadoRepo;

    @Autowired
    public EstadoService(EstadoRepo estadoRepo) {
        this.estadoRepo = estadoRepo;
    }

    public List<Estado> getAll() {
        return estadoRepo.findAll();
    }

}
