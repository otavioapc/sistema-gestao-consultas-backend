package br.com.vestaplan.api.controllers;

import br.com.vestaplan.api.entity.Usuario;
import br.com.vestaplan.api.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios(){
        List<Usuario> lista = usuarioService.getUsuarios();

        return ResponseEntity.ok().body(lista);
    }

}
