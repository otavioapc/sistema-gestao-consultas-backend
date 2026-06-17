package br.com.vestaplan.api.controllers;

import br.com.vestaplan.api.dtos.UsuarioDTO;
import br.com.vestaplan.api.entity.Usuario;
import br.com.vestaplan.api.enums.PerfilUsuario;
import br.com.vestaplan.api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        List<Usuario> lista = usuarioService.getUsuarios();

        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findUsuarioById(@PathVariable Integer id){
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok().body(usuario);

    }

    @GetMapping("/nome")
    public ResponseEntity<List<Usuario>> findUsuarioByName(@RequestParam String nome){
        List <Usuario> usuarios = usuarioService.findUsuarioByName(nome);
        return ResponseEntity.ok().body(usuarios);
    }

    @GetMapping("/perfil")
    public ResponseEntity<List<Usuario>> findUsuarioByPerfil(@RequestParam PerfilUsuario perfil){
        List <Usuario> usuarios = usuarioService.findUsuarioByPerfil(perfil);
        return ResponseEntity.ok().body(usuarios);
    }

    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(
            @Valid @RequestBody UsuarioDTO dto) throws URISyntaxException {

        Usuario novoUsuario = usuarioService.save(dto);

        return ResponseEntity
                .created(new URI("/usuarios/" + novoUsuario.getId()))
                .body(novoUsuario);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id, @RequestBody @Valid UsuarioDTO dto) {
        Usuario usuarioAtualizado = usuarioService.update(id, dto);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
