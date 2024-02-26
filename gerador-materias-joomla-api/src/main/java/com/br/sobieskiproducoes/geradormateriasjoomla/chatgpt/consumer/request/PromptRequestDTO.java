/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 13:18:16
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PromptRequestDTO {
  private String model;
  private List<MessageChatGPTDTO> messages;
  private Double temperature;
}
