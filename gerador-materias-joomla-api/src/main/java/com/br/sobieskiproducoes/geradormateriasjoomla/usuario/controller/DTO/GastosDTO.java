/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ane Batista
 * @since 7 de abr. de 2024 21:44:26
 * @version 1.0.0
 * 
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GastosDTO {
	
	private String valor;
    private String origemGastos;
    
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")    
    private LocalDateTime data;
    
    private String usuario;
}
