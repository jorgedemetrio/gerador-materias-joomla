/**
 * 
 */
package com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 10 de abr. de 2024 19:22:44
 * @version 1.0-10 de abr. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepostaThrendsRunnerDTO {
  private String id;
  private TipoObjetoChatGPTEnum object;
  
  @JsonProperty("created_at")
  private Long createdAt;

  private HashMap<String, String> metadata;

}
