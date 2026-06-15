package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Usuario;
import br.com.vestaplan.api.enums.PerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    @Query(value = "SELECT * FROM usuarios WHERE unaccent(lower(nome)) LIKE unaccent(lower(concat('%', :nome, '%')))", nativeQuery = true)
    List<Usuario> findByName(@Param("nome") String name);

    List<Usuario> findByPerfil(PerfilUsuario perfil);

}
