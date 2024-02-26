/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 13:56:58
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageChatGPTDTO {

  private String role;
  private String content;
}
