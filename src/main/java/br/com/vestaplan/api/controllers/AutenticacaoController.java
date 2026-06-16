package br.com.vestaplan.api.controllers;

import br.com.vestaplan.api.dtos.AutenticacaoDTO;
import br.com.vestaplan.api.entity.Usuario;
import br.com.vestaplan.api.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> efetuarLogin(@RequestBody @Valid AutenticacaoDTO dto) {
        UsernamePasswordAuthenticationToken dadosLogin =
                new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());

        Authentication authentication = authenticationManager.authenticate(dadosLogin);

        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        String tokenJwt = tokenService.gerarToken(usuarioLogado);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("token", tokenJwt);

        return ResponseEntity.ok(resposta);
    }
}