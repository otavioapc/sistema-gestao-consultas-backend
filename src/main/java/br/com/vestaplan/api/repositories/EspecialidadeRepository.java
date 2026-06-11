package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Integer> {

    boolean existsByNomeIgnoreCase(String nome);

    List<Especialidade> findByNomeContainingIgnoreCase(String nome);

}
