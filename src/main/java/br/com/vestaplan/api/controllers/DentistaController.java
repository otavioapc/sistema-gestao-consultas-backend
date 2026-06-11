package br.com.vestaplan.api.controllers;

import br.com.vestaplan.api.dtos.DentistaDTO;
import br.com.vestaplan.api.entity.Dentista;
import br.com.vestaplan.api.services.DentistaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/dentistas")
public class DentistaController {

    private final DentistaService dentistaService;

    public DentistaController(DentistaService dentistaService) {
        this.dentistaService = dentistaService;
    }

    @GetMapping
    public ResponseEntity<List<Dentista>> getDentistas() {
        List<Dentista> lista = dentistaService.getDentistas();
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dentista> findDentistaById(@PathVariable Integer id) {
        Dentista dentista = dentistaService.findById(id);
        return ResponseEntity.ok().body(dentista);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Dentista>> findDentistaByName(@RequestParam String nome) {
        List<Dentista> dentistas = dentistaService.findDentistaByName(nome);
        return ResponseEntity.ok().body(dentistas);
    }

    @PostMapping
    public ResponseEntity<Dentista> saveDentista(
            @Valid @RequestBody DentistaDTO dto) throws URISyntaxException {

        Dentista novoDentista = dentistaService.save(dto);

        return ResponseEntity
                .created(new URI("/dentistas/" + novoDentista.getId()))
                .body(novoDentista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dentista> atualizar(@PathVariable Integer id, @RequestBody @Valid DentistaDTO dto) {
        Dentista dentistaAtualizado = dentistaService.update(id, dto);
        return ResponseEntity.ok(dentistaAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        dentistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
