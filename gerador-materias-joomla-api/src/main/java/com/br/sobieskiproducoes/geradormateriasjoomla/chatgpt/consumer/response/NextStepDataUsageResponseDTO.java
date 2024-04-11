/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 11 de abr. de 2024 00:19:08
 * @version 1.0-11 de abr. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NextStepDataUsageResponseDTO {

  @JsonProperty("prompt_tokens")
  private Long promptTokens;

  @JsonProperty("completion_tokens")
  private Long completionTokens;

  @JsonProperty("total_tokens")
  private Long totalTokens;
}
