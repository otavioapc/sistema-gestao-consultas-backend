package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Integer> {
}
