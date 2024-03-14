/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 11:46:04
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@Validated
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("configuracao")
public class ConfiguracoesProperties {
  private CargaDadosImagensProperties cargaDadosImagens;
  private ChatGPTConfigurationProperties chatgpt;
  private JoomlaConfigurationProperties joomla;
  private YoutubeConfigurationProperties youtube;
}
