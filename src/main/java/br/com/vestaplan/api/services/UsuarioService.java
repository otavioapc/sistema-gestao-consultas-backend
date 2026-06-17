package br.com.vestaplan.api.services;

import br.com.vestaplan.api.dtos.UsuarioDTO;
import br.com.vestaplan.api.entity.Usuario;
import br.com.vestaplan.api.enums.PerfilUsuario;
import br.com.vestaplan.api.exceptions.EntidadeNaoEncontradaException;
import br.com.vestaplan.api.exceptions.NegocioException;
import br.com.vestaplan.api.mappers.UsuarioMapper;
import br.com.vestaplan.api.repositories.UsuarioRepository;
import br.com.vestaplan.api.utils.TextoUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario save(UsuarioDTO dto){

        String nomeLimpo = TextoUtils.higienizarNome(dto.nome());
        String cpfLimpo = TextoUtils.limparMascaras(dto.cpf());
        String emailLimpo = dto.email().trim().toLowerCase();

        if(usuarioRepository.existsByEmail(dto.email())){
            throw new NegocioException("Este email já está cadastrado!");
        }

        if(usuarioRepository.existsByCpf(dto.cpf())){
            throw  new NegocioException("Este CPF já está cadastrado");
        }

        Usuario usuarioEntity = usuarioMapper.toEntity(dto);

        usuarioEntity.setNome(nomeLimpo);
        usuarioEntity.setCpf(cpfLimpo);
        usuarioEntity.setEmail(emailLimpo);

        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        usuarioEntity.setSenha(senhaCriptografada);

        return usuarioRepository.save(usuarioEntity);
    }

    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário com o ID " + id + " não foi encontrado!"));
    }

    public List<Usuario> findUsuarioByName(String name){
        return usuarioRepository.findByName(TextoUtils.higienizarNome(name));
    }

    public List<Usuario> findUsuarioByPerfil(PerfilUsuario perfil){
        return usuarioRepository.findByPerfil(perfil);
    }

    @Transactional
    public Usuario update(Integer id, UsuarioDTO dto){

        Usuario atual = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário com o ID " + id + " não foi encontrado!"));

        atual.setNome(TextoUtils.higienizarNome(dto.nome()));
        atual.setCpf(TextoUtils.limparMascaras(dto.cpf()));
        atual.setEmail(dto.email().trim().toLowerCase());
        atual.setSenha(passwordEncoder.encode(dto.senha()));
        atual.setPerfilUsuario(dto.perfil());

        return usuarioRepository.save(atual);

    }

    @Transactional
    public void deleteById(Integer id) {

        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Usuário não encontrado para exclusão!");
        }
        usuarioRepository.deleteById(id);
    }

}
