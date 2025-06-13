/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response;

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
 * @since 22 de fev. de 2024 13:58:33
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepostaResponseDTO {
  private String id;
  private TipoObjetoChatGPTEnum object;
  private Long created;
  private String model;
  private List<ChoicesDTO> choices;
  private UsageDTO usage;
  @JsonProperty("system_fingerprint")
  private String systemFingerprint;
}
