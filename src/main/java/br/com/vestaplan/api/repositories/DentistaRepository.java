package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DentistaRepository extends JpaRepository<Dentista, Integer> {

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByCro(String cro);

    List<Dentista> findDentistaByNomeContainingIgnoreCase(String nome);

}
