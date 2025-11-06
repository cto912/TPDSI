package ppaidsi.eventossismicosppai.service;

import org.springframework.stereotype.Service;
import ppaidsi.eventossismicosppai.model.Usuario;
import ppaidsi.eventossismicosppai.repository.UsuarioRepo;

import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepo usuarioRepo;

    public UsuarioService(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public List<Usuario> getAll() {
        return usuarioRepo.findAll();
    }

}
