/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;

import feign.Logger;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 23:28:48
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
@Log
public class FeignJoomlaConfig {
  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer ";


  private final ConfiguracoesProperties priperties;

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public RequestInterceptor requestJoomlaInterceptor() {
    log.info("Realizando autenticação token beader com serviços internos para chamada via Feign Client");

    return requestTemplate -> {
      requestTemplate.header(AUTHORIZATION, BEARER.concat(priperties.getJoomla().getBearer()));
    };
  }
}
