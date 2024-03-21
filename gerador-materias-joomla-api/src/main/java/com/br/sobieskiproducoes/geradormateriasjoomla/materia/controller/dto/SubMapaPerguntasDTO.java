/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto;

import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.MapaPerguntaEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ane Batista
 * @since 15 de mar. de 2024 20:35:15
 * @version 1.0.0
 * 
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubMapaPerguntasDTO {
	  private Long id;
	  private String uuid;
	  private String pergunta;
	  private MapaPerguntaEntity peguntaPrincipal;
}
