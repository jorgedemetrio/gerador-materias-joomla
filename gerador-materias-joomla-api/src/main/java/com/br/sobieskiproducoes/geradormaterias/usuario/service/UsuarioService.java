package com.br.sobieskiproducoes.geradormaterias.usuario.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormaterias.usuario.dto.TokenSessionDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@Log
@RequiredArgsConstructor
public class UsuarioService {

    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository repository;

    private final TokenSerice tokenSerice;

    public TokenSessionDTO login(final String login, final String senha) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login, senha);

        authenticationManager.authenticate(authenticationToken);

        return tokenSerice.gerarToken(login);

    }

}
