package com.br.sobieskiproducoes.geradormaterias.usuario.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.EmpresaRepository;
import com.br.sobieskiproducoes.geradormaterias.usuario.convert.UsuarioSenhaConvert;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.TokenSessionDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSistemaDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.exception.UsuarioExistenteException;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.repository.UsuarioRepository;
import com.br.sobieskiproducoes.geradormaterias.utils.SpringSecurityAuditorAwareComponent;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@Log
@RequiredArgsConstructor
public class UsuarioService {

    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository repository;

    private final TokenSerice tokenSerice;

    private final EmpresaRepository empresaRepository;

    private final UsuarioSenhaConvert usuarioSenhaConvert;

    private final SpringSecurityAuditorAwareComponent usuarioLogadoAwareComponent;

    public UsuarioDTO salvar(@Valid final UsuarioDTO usuario) throws Exception {
        log.info(String.format("Salvando o usuário %s", usuario));
        if (repository.findByEmailIgnoreCaseOrUsuarioIgnoreCase(usuario.getEmail(), usuario.getUsuario()).isPresent()) {
            throw new UsuarioExistenteException("Usuário e/ou e-mail já cadastrado!");
        }

        final UsuarioEntity entity = usuarioSenhaConvert.novo(usuario);

        final Optional<EmpresaEntity> empresa = empresaRepository.buscarPrincipalPorUsuario(usuarioLogadoAwareComponent.getUsuarioLogado().getUsuario());
        if (empresa.isPresent()) {
            entity.setEmpresas(Arrays.asList(empresa.get()));
        }

        return usuarioSenhaConvert.to(repository.save(entity));

    }

    public TokenSessionDTO login(final String login, final String senha) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login, senha);

        final Authentication auth = authenticationManager.authenticate(authenticationToken);

        return tokenSerice.gerarToken((UsuarioSistemaDTO) auth.getPrincipal());

    }

}
