package br.com.vestaplan.api.controllers;

import br.com.vestaplan.api.dtos.ConsultaDTO;
import br.com.vestaplan.api.entity.Consulta;
import br.com.vestaplan.api.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        consultaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
