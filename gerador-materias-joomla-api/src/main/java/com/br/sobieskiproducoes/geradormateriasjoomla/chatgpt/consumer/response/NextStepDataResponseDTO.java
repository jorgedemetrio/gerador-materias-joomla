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
 * @since 11 de abr. de 2024 00:11:39
 * @version 1.0-11 de abr. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NextStepDataResponseDTO {
  private String id;
  private TipoObjetoChatGPTEnum object;

  @JsonProperty("created_at")
  private String createdAt;

  @JsonProperty("run_id")
  private String runId;

  @JsonProperty("assistant_id")
  private String assistantId;

  @JsonProperty("thread_id")
  private String threadId;

  private String type;
  private RunnerStatuEnum status;

  @JsonProperty("cancelled_at")
  private String cancelledAt;

  @JsonProperty("completed_at")
  private String completedAt;

  @JsonProperty("expires_at")
  private String expiresAt;

  @JsonProperty("failed_at")
  private String failedAt;

  @JsonProperty("last_error")
  private String lastError;

  @JsonProperty("step_details")
  private NextStepDataStepDetailsResponseDTO stepDetails;

  private UsageDTO usage;

}
