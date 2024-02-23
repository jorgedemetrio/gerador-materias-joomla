/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 19:55:23
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FaqDTO {
  private Long id;
  private String pergunta;
  private String resposta;
}
