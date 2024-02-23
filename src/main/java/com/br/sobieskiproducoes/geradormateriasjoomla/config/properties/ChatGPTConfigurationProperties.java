/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 *
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("configuracao.chatgpt")
public class ChatGPTConfigurationProperties {

  private String url;

  private String bearer;

  private String model;
  private Double temperature;
  private String role;

}
