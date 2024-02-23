/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 21:03:32
 * @version 1.0.0
 */
@Configuration
public class ObjectMapperConfig {

  @Bean
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper();
  }

}
