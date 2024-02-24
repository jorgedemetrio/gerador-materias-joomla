/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 21:03:32
 * @version 1.0.0
 */
@Configuration
public class ObjectsConfig {

  @Bean
  public ObjectMapper getObjectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
    objectMapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
    return objectMapper;
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
