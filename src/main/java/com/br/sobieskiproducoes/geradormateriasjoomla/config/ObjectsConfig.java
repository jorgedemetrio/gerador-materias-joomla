/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 21:03:32
 * @version 1.0.0
 */
@Configuration
public class ObjectsConfig {

  @Bean
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  /*
   * @Bean
   * 
   * @ConditionalOnMissingBean
   * 
   * @ConditionalOnProperty(name = { "springdoc.use-management-port" },
   * havingValue = "false", matchIfMissing = true) SwaggerWelcomeWebMvc
   * swaggerWelcome(final SwaggerUiConfigProperties swaggerUiConfig, final
   * SpringDocConfigProperties springDocConfigProperties, final
   * SwaggerUiConfigParameters swaggerUiConfigParameters, final SpringWebProvider
   * springWebProvider) { return new SwaggerWelcomeWebMvc(swaggerUiConfig,
   * springDocConfigProperties, swaggerUiConfigParameters, springWebProvider); }
   */
}
