package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    @Query(value = "SELECT * FROM pacientes WHERE unaccent(lower(nome)) LIKE unaccent(lower(concat('%', :nome, '%')))", nativeQuery = true)
    List<Paciente> findByName(@Param("nome") String nome);

}
