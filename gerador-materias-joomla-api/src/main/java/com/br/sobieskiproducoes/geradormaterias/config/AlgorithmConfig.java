package com.br.sobieskiproducoes.geradormaterias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.auth0.jwt.algorithms.Algorithm;
import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class AlgorithmConfig {

    private final ConfiguracoesProperties properties;

    @Bean
    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(properties.getChaveToken());
    }
}
