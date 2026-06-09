package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
}
