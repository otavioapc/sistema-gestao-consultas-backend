package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistaRepository extends JpaRepository<Dentista, Integer> {
}
