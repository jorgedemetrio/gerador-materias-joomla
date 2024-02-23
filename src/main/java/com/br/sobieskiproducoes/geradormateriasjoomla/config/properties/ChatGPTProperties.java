/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.config.properties;

import java.util.List;

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
@ConfigurationProperties("chatgpt")
public class ChatGPTProperties {
  private ChatGPTPerguntasProperties perguntas;

  private String site;
  private List<String> redesSociais;
}
