/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.config.properties;

import org.springframework.validation.annotation.Validated;

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
@Validated
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTPerguntasProperties {

  @NotNull
  @NotBlank
  private String pedirMateria;
}
