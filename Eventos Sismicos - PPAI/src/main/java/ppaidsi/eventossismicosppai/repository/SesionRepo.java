package ppaidsi.eventossismicosppai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppaidsi.eventossismicosppai.model.Sesion;

@Repository
public interface SesionRepo extends JpaRepository<Sesion, Integer> {
}
