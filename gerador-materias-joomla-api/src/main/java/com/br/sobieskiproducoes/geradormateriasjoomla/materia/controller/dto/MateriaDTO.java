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
 * @author Ane Batista
 * @since 15 de mar. de 2024 19:58:18
 * @version 1.0.0
 * 
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MateriaDTO {
	private Long id;
	private Long idJoomla;
	private String tema;
	private String uuid;
	private String titulo1;
	private String titulo2;
	private String titulo3;
	private String metaDescricao;
	private String keywords;
	private String materia;
	private String apelido;
	  
	  
	  
}
