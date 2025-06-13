/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config.properties;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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
 * @since 25 de fev. de 2024 07:46:02
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class DadosImagensMateriasProperties {

  @NotBlank
  private String pastaImagemMaterias;

  @NotBlank
  private String autor;

  @NotNull
  @Min(300)
  @Max(1200)
  private Integer tamanhoHorizontal;

  @Max(1200)
  private Integer tamanhoVerticalMinimo;

  @NotNull
  @DecimalMin("0.1")
  @DecimalMax("1.0")
  private Double qualidade;

  private String constante;
  private String url;

  @JsonProperty(value = "instagram", defaultValue = "true", required = false)
  private Boolean instagram = Boolean.TRUE;
}
