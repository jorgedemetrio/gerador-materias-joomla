/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:27:09
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaDTO {

  private Long id;

  private Long idJoomla;

  private String titulo;

  private String apelido;

  private CategoriaDTO pai;

}
