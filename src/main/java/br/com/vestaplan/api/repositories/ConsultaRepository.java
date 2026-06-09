package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
}
