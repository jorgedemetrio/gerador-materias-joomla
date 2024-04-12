/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response;

import java.util.HashMap;
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
 * @since 10 de abr. de 2024 23:23:41
 * @version 1.0-10 de abr. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MensagemPostedResponseDTO {
  private String id;
  private TipoObjetoChatGPTEnum object;

  @JsonProperty("created_at")
  private String createdAt;

  @JsonProperty("assistant_id")
  private String assistantId;

  @JsonProperty("thread_id")
  private String threadId;

  @JsonProperty("run_id")
  private String runId;

  private String role;

  @JsonProperty("file_ids")
  private List<String> fileIds;

  private HashMap<String, String> metadata;

  private List<MensagemPostedContentResponseDTO> content;


}
