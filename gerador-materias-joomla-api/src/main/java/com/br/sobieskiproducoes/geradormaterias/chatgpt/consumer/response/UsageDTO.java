/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 14:00:26
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsageDTO {
  @JsonProperty("prompt_tokens")
  private Long promptTokens;

  @JsonProperty("completion_tokens")
  private Long completionTokens;

  @JsonProperty("total_tokens")
  private Long totalTokens;
}
