/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config.properties;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 13 de mar. de 2024 20:38:58
 * @version 1.0.0
 *
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class YoutubeConfigurationProperties {
  private String url;
  private String clientId;
  private String secrets;
  private String chaveApi;
  private String appId;
  private String arquivoSecrets;
  private String callback;
}
