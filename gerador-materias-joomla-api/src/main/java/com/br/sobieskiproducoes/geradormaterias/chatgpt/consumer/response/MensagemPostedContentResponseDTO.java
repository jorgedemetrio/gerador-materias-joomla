/**
 * 
 */
package com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MensagemPostedContentResponseDTO {
  private String type;
  private MensagemPostedContentTextResponseDTO text;


}
