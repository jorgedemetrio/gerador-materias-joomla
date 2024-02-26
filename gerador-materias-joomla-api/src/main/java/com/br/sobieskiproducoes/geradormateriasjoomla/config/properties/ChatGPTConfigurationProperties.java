/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.config.properties;

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

  @NotNull
  @NotBlank
  private String url;

  @NotNull
  @NotBlank
  private String bearer;

  @NotNull
  @NotBlank
  private String model;

  @NotNull
  @Min(0)
  @Max(1)
  private Double temperature;

  @NotNull
  @NotBlank
  private String role;

}
