package ppaidsi.eventossismicosppai.repository;

import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppaidsi.eventossismicosppai.model.TipoDeDato;

@Repository
public interface TipoDeDatoRepo extends JpaRepository<TipoDeDato, Integer> {
}
