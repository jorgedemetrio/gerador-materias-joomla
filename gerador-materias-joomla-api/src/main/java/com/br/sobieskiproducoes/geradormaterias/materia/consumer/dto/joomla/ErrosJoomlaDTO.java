/**
 * 
 */
package com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 1 de ago. de 2024 01:37:36
 * @version 1.0-1 de ago. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrosJoomlaDTO {
  private List<ItemErroJoomlaDTO> errors;
}
