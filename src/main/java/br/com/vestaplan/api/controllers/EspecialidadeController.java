package br.com.vestaplan.api.controllers;

import br.com.vestaplan.api.dtos.EspecialidadeDTO;
import br.com.vestaplan.api.entity.Especialidade;
import br.com.vestaplan.api.entity.Usuario;
import br.com.vestaplan.api.services.EspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    public EspecialidadeController(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }

    @GetMapping
    public ResponseEntity<List<Especialidade>> getEspecialidades() {
        List<Especialidade> lista = especialidadeService.getEspecialidades();
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especialidade> findById(@PathVariable Integer id) {
        Especialidade especialidade = especialidadeService.findById(id);
        return ResponseEntity.ok().body(especialidade);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Especialidade>> findByName(@RequestParam String nome) {
        List<Especialidade> especialidades = especialidadeService.findByName(nome);
        return ResponseEntity.ok().body(especialidades);
    }

    @PostMapping
    public ResponseEntity<Especialidade> save(@Valid @RequestBody EspecialidadeDTO dto) throws URISyntaxException {
        Especialidade novaEspecialidade = especialidadeService.save(dto);
        return ResponseEntity.created(new URI("/especialidades/" + novaEspecialidade.getId())).body(novaEspecialidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Especialidade> atualizar(@PathVariable Integer id, @RequestBody @Valid EspecialidadeDTO dto) {
        return ResponseEntity.ok(especialidadeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        especialidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
