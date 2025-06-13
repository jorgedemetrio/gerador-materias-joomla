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
 * @since 11 de abr. de 2024 00:35:58
 * @version 1.0-11 de abr. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteThreadDTO {
  private String id;
  private TipoObjetoChatGPTEnum object;
  private Boolean deleted;
}
