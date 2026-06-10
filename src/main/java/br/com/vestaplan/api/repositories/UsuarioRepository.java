package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Usuario;
import br.com.vestaplan.api.enums.PerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    List<Usuario> findUsuarioByNomeContainingIgnoreCase(String name);

    List<Usuario> findByPerfil(PerfilUsuario perfil);

}
