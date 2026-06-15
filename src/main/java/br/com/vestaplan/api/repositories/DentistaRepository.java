package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DentistaRepository extends JpaRepository<Dentista, Integer> {

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByCro(String cro);

    @Query(value = "SELECT * FROM dentistas WHERE unaccent(lower(nome)) LIKE unaccent(lower(concat('%', :nome, '%')))", nativeQuery = true)
    List<Dentista> findByName(@Param("nome") String nome);

}
