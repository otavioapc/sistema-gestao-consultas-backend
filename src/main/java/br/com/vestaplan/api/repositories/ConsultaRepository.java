package br.com.vestaplan.api.repositories;

import br.com.vestaplan.api.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

    @Query("SELECT COUNT(c) > 0 FROM Consulta c WHERE c.dentista.id = :dentistaId " +
            "AND c.status <> StatusConsulta.CANCELADA " +
            "AND (:id IS NULL OR c.id <> :id) " +
            "AND (:inicio < c.dataFim AND :fim > c.dataInicio)")
    boolean existsByDentistaIdAndHorarioConflitante(
            @Param("dentistaId") Integer dentistaId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            @Param("id") Integer id
    );
    @Query(value = "SELECT c.* FROM consultas c " +
            "JOIN dentistas d ON c.id_dentista = d.id " +
            "LEFT JOIN dentista_especialidade de ON d.id = de.id_dentista " +
            "WHERE (:idPaciente IS NULL OR c.id_paciente = :idPaciente) " +
            "AND (:idUsuario IS NULL OR c.id_usuario = :idUsuario) " +
            "AND (:idEspecialidade IS NULL OR de.id_especialidade = :idEspecialidade) " +
            "AND (CAST(:dataInicio AS TIMESTAMP) IS NULL OR c.data_inicio >= :dataInicio) " +
            "AND (CAST(:dataFim AS TIMESTAMP) IS NULL OR c.data_fim <= :dataFim)",
            nativeQuery = true)
    List<Consulta> findConsultasFiltradas(
            @Param("idPaciente") Integer idPaciente,
            @Param("idUsuario") Integer idUsuario,
            @Param("idEspecialidade") Integer idEspecialidade,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );

}
