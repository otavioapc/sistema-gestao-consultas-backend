package br.com.vestaplan.api.controllers;

import br.com.vestaplan.api.dtos.ConsultaCancelamentoDTO;
import br.com.vestaplan.api.dtos.ConsultaDTO;
import br.com.vestaplan.api.entity.Consulta;
import br.com.vestaplan.api.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> getConsultas() {
        return ResponseEntity.ok(consultaService.getConsultas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(consultaService.findById(id));
    }

    @GetMapping("/relatorio")
    public ResponseEntity<List<Consulta> > getRelatorio(
            @RequestParam(required = false) Integer idPaciente,
            @RequestParam(required = false) Integer idUsuario,
            @RequestParam(required = false) Integer idEspecialidade,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim) {

        LocalDateTime inicio = (dataInicio != null) ? LocalDateTime.parse(dataInicio) : null;
        LocalDateTime fim = (dataFim != null) ? LocalDateTime.parse(dataFim) : null;

        List<Consulta> relatorio = consultaService.findConsultasFiltradas(idPaciente, idUsuario, idEspecialidade, inicio, fim);
        return ResponseEntity.ok(relatorio);
    }

    @PostMapping
    public ResponseEntity<Consulta> save(
            @Valid @RequestBody ConsultaDTO dto) throws URISyntaxException {
        Consulta novaConsulta = consultaService.save(dto);
        return ResponseEntity
                .created(new URI("/consultas/" + novaConsulta.getId()))
                .body(novaConsulta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consulta> atualizar(@PathVariable Integer id, @RequestBody @Valid ConsultaDTO dto) {
        Consulta consultaAtualizado = consultaService.update(id, dto);

        return ResponseEntity.ok(consultaAtualizado);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Consulta> cancelar(@PathVariable Integer id, @RequestBody @Valid ConsultaCancelamentoDTO dto) {
        Consulta consultaCancelada = consultaService.cancelarConsulta(id, dto);
        return ResponseEntity.ok(consultaCancelada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        consultaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
