/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:42:49
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {

  private Long id;
  private Long idJoomla;
  private String uuid;
  private String titulo;
  private String apelido;

}
