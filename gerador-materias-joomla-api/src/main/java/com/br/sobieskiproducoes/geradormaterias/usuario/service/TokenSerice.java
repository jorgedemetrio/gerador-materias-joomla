package com.br.sobieskiproducoes.geradormaterias.usuario.service;

import java.time.Instant;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.TokenSessionDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSistemaDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@Log
@RequiredArgsConstructor
public class TokenSerice {

    private final ConfiguracoesProperties properties;

    private final Algorithm algorithm;

    @Value("${application.name}")
    private String APP_NAME;

    public final String EMPRESA_ID = "EMPRESA_ID";
    public final String USUARIO_ID = "USARIO_ID";

    public TokenSessionDTO gerarToken(@NotEmpty final UsuarioSistemaDTO usuario) {
        try {

            final Instant instante = getInstant();

            return new TokenSessionDTO(
                    JWT.create().withIssuer(APP_NAME).withSubject(usuario.getUsername()).withClaim(EMPRESA_ID, usuario.getIdEmpresaPricipal())
                            .withClaim(USUARIO_ID, usuario.getId()).withExpiresAt(instante).sign(algorithm),
                    instante, properties.getTimeOutToken(), usuario.getEmpresasIds(), usuario.getIdEmpresaPricipal());
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
            throw ex;
        }
    }

    private Instant getInstant() {
        return Instant.now().plusSeconds(properties.getTimeOutToken() * 60);
    }
}
