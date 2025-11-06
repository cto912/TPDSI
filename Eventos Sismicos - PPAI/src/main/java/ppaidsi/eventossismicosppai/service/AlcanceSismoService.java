package ppaidsi.eventossismicosppai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppaidsi.eventossismicosppai.model.AlcanceSismo;
import ppaidsi.eventossismicosppai.repository.AlcanceSismoRepo;

import java.util.List;

@Service
public class AlcanceSismoService {

    private final AlcanceSismoRepo alcanceSismoRepo;

    @Autowired
    public AlcanceSismoService(AlcanceSismoRepo alcanceSismoRepo) {
        this.alcanceSismoRepo = alcanceSismoRepo;
    }

    public List<AlcanceSismo> getAll(){
        return alcanceSismoRepo.findAll();
    }
}
