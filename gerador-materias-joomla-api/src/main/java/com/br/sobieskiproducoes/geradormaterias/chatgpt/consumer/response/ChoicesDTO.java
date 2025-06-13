/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response;

import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.request.MessageChatGPTDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 14:03:39
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChoicesDTO {
  private Integer index;
  private MessageChatGPTDTO message;
  private String logprobs;
  @JsonProperty("finish_reason")
  private String finishReason;
}
