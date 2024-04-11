/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 10 de abr. de 2024 19:49:04
 * @version 1.0-10 de abr. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RunnerResponseDTO {
  private String id;
  private TipoObjetoChatGPTEnum object;

  @JsonProperty("created_at")
  private String createdAt;

  @JsonProperty("assistant_id")
  private String assistantId;

  @JsonProperty("thread_id")
  private String threadId;


  private RunnerStatuEnum status;

  @JsonProperty("started_at")
  private String startedAt;

  @JsonProperty("expires_at")
  private String expiresAt;

  @JsonProperty("cancelled_at")
  private String cancelledAt;

  @JsonProperty("failed_at")
  private String failedAt;

  @JsonProperty("completed_at")
  private String completedAt;

  @JsonProperty("last_error")
  private ErroResponseDTO lastError;

  private String model;

  private String instructions;

  private List<ToolsRequestDTO> tools;

  @JsonProperty("file_ids")
  private List<String> fileIds;

  private UsageDTO usage;

  private Long temperature;

  private HashMap<String, String> metadata;
}
