/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 11 de abr. de 2024 00:09:17
 * @version 1.0-11 de abr. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NextStepResponseDTO {
  private TipoObjetoChatGPTEnum object;
  private List<NextStepDataResponseDTO> data;

  @JsonProperty("first_id")
  private String firstId;

  @JsonProperty("last_id")
  private String lastId;

  @JsonProperty("has_more")
  private Boolean hasMore;

  private RunnerStatuEnum status;
}
