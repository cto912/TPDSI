package ppaidsi.eventossismicosppai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppaidsi.eventossismicosppai.model.ClasificacionSismo;
import ppaidsi.eventossismicosppai.repository.ClasificacionSismoRepo;

import java.util.List;

@Service
public class ClasificacionSisimoService {
    private final ClasificacionSismoRepo clasificacionSismoRepo;

    @Autowired
    public ClasificacionSisimoService(ClasificacionSismoRepo clasificacionSismoRepo) {
        this.clasificacionSismoRepo = clasificacionSismoRepo;
    }

    public List<ClasificacionSismo> getAll(){
        return clasificacionSismoRepo.findAll();
    }
}
