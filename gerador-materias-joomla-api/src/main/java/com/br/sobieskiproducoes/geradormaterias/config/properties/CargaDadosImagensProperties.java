/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config.properties;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 07:45:28
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class CargaDadosImagensProperties {

  @NotNull
  @Valid
  private DadosImagensMateriasProperties imagens;

  @NotNull
  @Valid
  private DadosImagensLogoProperties logo;
}
