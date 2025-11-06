package ppaidsi.eventossismicosppai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppaidsi.eventossismicosppai.model.Empleado;

@Repository
public interface EmpleadoRepo extends JpaRepository<Empleado, Integer> {
}
