/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config.properties;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 13 de jun. de 2025 20:27:40
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class SerperProperties {

  private String bearer;
  private String search;

}
