/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ane Batista
 * @since 7 de abr. de 2024 13:34:24
 * @version 1.0.0
 * 
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO {
	private Long idUsuario;
	private String nome;
	private String usuario;
	private String senha;
	private String cpf;
	
}
