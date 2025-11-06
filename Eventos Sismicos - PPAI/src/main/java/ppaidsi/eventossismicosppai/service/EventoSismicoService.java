package ppaidsi.eventossismicosppai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppaidsi.eventossismicosppai.model.EventoSismico;
import ppaidsi.eventossismicosppai.repository.EventoSismicoRepo;

import java.util.List;

@Service
public class EventoSismicoService {

    private final EventoSismicoRepo repo;

    @Autowired
    public EventoSismicoService(EventoSismicoRepo repo) {
        this.repo = repo;
    }

    public List<EventoSismico> getAll(){
        return repo.findAll();
    }

    public EventoSismico getById(int id){
        return repo.findById(id).get();
    }

    public void save(EventoSismico eventoSismico){
        repo.save(eventoSismico);
    }
}
