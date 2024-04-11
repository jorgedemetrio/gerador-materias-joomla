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
 * @since 10 de abr. de 2024 20:00:11
 * @version 1.0-10 de abr. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ThreadRequestRunDTO {
  private List<MessageChatGPTDTO> messages;
}
