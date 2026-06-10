package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    List<Paciente> findPacienteByNomeContainingIgnoreCase(String nome);

}
