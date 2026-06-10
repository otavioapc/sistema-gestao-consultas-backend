package br.com.vestaplan.api.controllers;

import br.com.vestaplan.api.dtos.PacienteDTO;
import br.com.vestaplan.api.entity.Paciente;
import br.com.vestaplan.api.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> getPacientes() {
        List<Paciente> lista = pacienteService.getPacientes();
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> findPacienteById(@PathVariable Integer id) {
        Paciente paciente = pacienteService.findById(id);
        return ResponseEntity.ok().body(paciente);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Paciente>> findPacienteByName(@RequestParam String nome) {
        List<Paciente> pacientes = pacienteService.findPacienteByName(nome);
        return ResponseEntity.ok().body(pacientes);
    }

    @PostMapping
    public ResponseEntity<Paciente> savePaciente(
            @Valid @RequestBody PacienteDTO dto) throws URISyntaxException {

        Paciente novoPaciente = pacienteService.save(dto);

        return ResponseEntity
                .created(new URI("/pacientes/" + novoPaciente.getId()))
                .body(novoPaciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Integer id, @RequestBody @Valid PacienteDTO dto) {
        Paciente pacienteAtualizado = pacienteService.update(id, dto);
        return ResponseEntity.ok(pacienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pacienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
