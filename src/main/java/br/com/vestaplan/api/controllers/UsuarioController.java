package br.com.vestaplan.api.controllers;

import br.com.vestaplan.api.dtos.UsuarioDTO;
import br.com.vestaplan.api.entity.Usuario;
import br.com.vestaplan.api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
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

    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(
            @Valid @RequestBody UsuarioDTO dto) throws URISyntaxException {

        Usuario novoUsuario = usuarioService.save(dto);

        return ResponseEntity
                .created(new URI("/usuarios/" + novoUsuario.getId()))
                .body(novoUsuario);

    }

}
