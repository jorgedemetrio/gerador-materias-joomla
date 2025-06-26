package com.br.sobieskiproducoes.geradormaterias.usuario.service;

import java.time.Instant;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.TokenSessionDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@Log
@RequiredArgsConstructor
public class TokenSerice {

    private final ConfiguracoesProperties properties;

    private final Algorithm algorithm;

    @Value("${app.name}")
    private String APP_NAME;

    public TokenSessionDTO gerarToken(@NotEmpty final UserDetails usuario) {
        try {
//            final Algorithm algorithm = Algorithm.HMAC256(properties.getChaveToken());

            final Instant instante = getInstant();

            return new TokenSessionDTO(JWT.create().withIssuer(APP_NAME).withSubject(usuario.getUsername()).withExpiresAt(instante).sign(algorithm), instante,
                    properties.getTimeOutToken());

        } catch (final Exception ex) {
            log.log(Level.SEVERE, "[ERROR] TokenSerice#gerarToken " + ex.getLocalizedMessage(), ex.getCause());
            throw new RuntimeException("Erro ao gerar a chave.", ex);
        }
    }

    /**
     * Retorna o usuário.
     *
     * @param token
     * @return
     */
    public String validToken(@NotEmpty final String token) {
        try {

            return JWT.require(algorithm).withIssuer(APP_NAME).build().verify(token).getSubject();

        } catch (final Exception ex) {
            log.log(Level.SEVERE, "[ERROR] TokenSerice#gerarToken " + ex.getLocalizedMessage(), ex.getCause());
            throw new RuntimeException("Erro ao gerar a chave.", ex);
        }
    }

    private Instant getInstant() {
        return Instant.now().plusSeconds(properties.getTimeOutToken() * 60);
    }
}
