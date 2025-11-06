package ppaidsi.eventossismicosppai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppaidsi.eventossismicosppai.model.EventoSismico;

@Repository
public interface EventoSismicoRepo extends JpaRepository<EventoSismico, Integer> {

}
