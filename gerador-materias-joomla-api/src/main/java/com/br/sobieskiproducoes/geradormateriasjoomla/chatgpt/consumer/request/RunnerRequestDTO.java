/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 10 de abr. de 2024 19:47:47
 * @version 1.0-10 de abr. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RunnerRequestDTO {

  @JsonProperty("assistant_id")
  private String assistantId;


}
