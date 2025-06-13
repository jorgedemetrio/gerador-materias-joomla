/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config.properties;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 07:46:11
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class DadosImagensLogoProperties {

  @Size(min = 5)
  @NotNull
  @NotBlank
  private String path;

  @NotNull
  private PosicaoEnum alinhadoHorizontal;

  @NotNull
  private PosicaoEnum alinhadoVertical;

  @Min(1)
  @Max(500)
  @NotNull
  private Integer distanciaMargemHorizontal;

  @Min(1)
  @Max(500)
  @NotNull
  private Integer distanciaMargemVertial;

  @DecimalMin("0.1")
  @DecimalMax("1")
  @NotNull
  private Double transparencia;

  @Min(1)
  @Max(500)
  @NotNull
  private Integer tamanhoHorizontal;

  @DecimalMin("0.3")
  @DecimalMax("1")
  @NotNull
  private Double qualidade;
}
