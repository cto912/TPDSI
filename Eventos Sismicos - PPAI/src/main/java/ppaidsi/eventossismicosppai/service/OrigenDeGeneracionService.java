package ppaidsi.eventossismicosppai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppaidsi.eventossismicosppai.model.OrigenDeGeneracion;
import ppaidsi.eventossismicosppai.repository.OrigenDeGeneracionRepo;

import java.util.List;

@Service
public class OrigenDeGeneracionService {
    private final OrigenDeGeneracionRepo origenDeGeneracionRepo;

    @Autowired
    public OrigenDeGeneracionService (OrigenDeGeneracionRepo origenDeGeneracionRepo) {
        this.origenDeGeneracionRepo = origenDeGeneracionRepo;
    }

    public List<OrigenDeGeneracion> getAll(){
        return origenDeGeneracionRepo.findAll();
    }
}
