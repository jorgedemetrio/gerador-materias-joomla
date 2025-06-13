/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
@ConfigurationProperties("chatgpt")
public class ChatGPTProperties {

  @NotNull
  @Valid
  private ChatGPTPerguntasProperties prompts;

  @NotEmpty
  private List<String> audiencias;

  @NotNull
  @NotBlank
  private String site;

  @NotNull
  @NotEmpty
  private List<String> redesSociais;

  @NotNull
  @NotEmpty
  private List<String> especialista;

  private List<String> falhas;

}
