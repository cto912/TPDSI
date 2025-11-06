package ppaidsi.eventossismicosppai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppaidsi.eventossismicosppai.model.Usuario;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
}
