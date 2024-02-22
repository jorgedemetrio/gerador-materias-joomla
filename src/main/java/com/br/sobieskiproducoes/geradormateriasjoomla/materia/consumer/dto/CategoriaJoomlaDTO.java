/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 18:37:38
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaJoomlaDTO {

  private String type;
  private String id;
  private AtributosCategoriaJoomlaDTO attributes;
}
