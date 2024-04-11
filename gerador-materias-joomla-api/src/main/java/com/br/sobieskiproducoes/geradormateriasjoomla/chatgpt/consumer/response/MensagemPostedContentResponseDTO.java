/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 10 de abr. de 2024 23:47:26
 * @version 1.0-10 de abr. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MensagemPostedContentResponseDTO {
  private String type;
  private MensagemPostedContentTextResponseDTO text;


}
