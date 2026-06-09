package br.com.vestaplan.api.mappers;

import br.com.vestaplan.api.dtos.UsuarioDTO;
import br.com.vestaplan.api.entity.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMapper {

    public Usuario toEntity(UsuarioDTO dto) {

        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setPerfilUsuario(dto.perfil());

        return usuario;
    }

}
