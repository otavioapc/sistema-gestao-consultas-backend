package br.com.vestaplan.api.services;

import br.com.vestaplan.api.dtos.UsuarioDTO;
import br.com.vestaplan.api.entity.Usuario;
import br.com.vestaplan.api.enums.PerfilUsuario;
import br.com.vestaplan.api.mappers.UsuarioMapper;
import br.com.vestaplan.api.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public Usuario save(UsuarioDTO dto){

        if(usuarioRepository.existsByEmail(dto.email())){
            throw new RuntimeException("Este email já está cadastrado!");
        }

        if(usuarioRepository.existsByCpf(dto.cpf())){
            throw  new RuntimeException("Este CPF já está cadastrado");
        }

        Usuario usuarioEntity = usuarioMapper.toEntity(dto);

        return usuarioRepository.save(usuarioEntity);
    }

    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com o ID " + id + " não foi encontrado!"));
    }

    public List<Usuario> findUsuarioByName(String name){
        return usuarioRepository.findUsuarioByNomeContainingIgnoreCase(name);
    }

    public List<Usuario> findUsuarioByPerfil(PerfilUsuario perfil){
        return usuarioRepository.findByPerfil(perfil);
    }

}
