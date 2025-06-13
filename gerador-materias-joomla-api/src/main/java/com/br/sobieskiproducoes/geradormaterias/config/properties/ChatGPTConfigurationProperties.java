/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config.properties;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 *
 */
@Getter
@Setter
@ToString
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTConfigurationProperties {

  private String urlCriarThrend;
  private String urlPostarMensagem;
  private String urlIniciarRun;
  private String urlLerRunner;
  private String urlAvancarMessage;
  private String urlLerMensagem;
  private String urlApagarThrend;

  private String thread;

  @NotNull
  @NotBlank
  private String url;

  @NotNull
  @NotBlank
  private String bearer;



  private String assistente;

  @NotNull
  @NotBlank
  private String organization;

  @NotNull
  @NotBlank
  private String model;

  @NotNull
  @Min(0)
  @Max(1)
  private Double temperature;

  @NotNull
  @NotBlank
  private String roleUser;

  @NotNull
  @NotBlank
  private String roleSystem;

  @NotNull
  @NotBlank
  private String roleAssistant;

  @NotNull
  @NotBlank
  private String maxTokens;

}
