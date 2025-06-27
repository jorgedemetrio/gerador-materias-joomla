package com.br.sobieskiproducoes.geradormaterias.usuario.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormaterias.usuario.convert.UsuarioConvert;
import com.br.sobieskiproducoes.geradormaterias.usuario.convert.UsuarioSenhaConvert;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.TokenSessionDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSistemaDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.exception.UsuarioExistenteException;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.repository.UsuarioRepository;

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

    private final UsuarioConvert convert;
    private final UsuarioSenhaConvert usuarioSenhaConvert;

    public UsuarioDTO salvar(@Valid final UsuarioDTO usuario) throws Exception {

        if (repository.findByEmailIgnoreCaseOrUsuarioIgnoreCase(usuario.getEmail(), usuario.getUsuario()).isPresent()) {
            throw new UsuarioExistenteException("Usuário e/ou e-mail já cadastrado!");
        }

        final UsuarioEntity entity = usuarioSenhaConvert.to(usuario);

        return usuarioSenhaConvert.to(repository.save(entity));

    }

    public TokenSessionDTO login(final String login, final String senha) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login, senha);

        final Authentication auth = authenticationManager.authenticate(authenticationToken);

        return tokenSerice.gerarToken((UsuarioSistemaDTO) auth.getPrincipal());

    }

}
