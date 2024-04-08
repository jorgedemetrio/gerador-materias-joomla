/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ane Batista
 * @since 7 de abr. de 2024 13:42:44
 * @version 1.0.0
 * 
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjetoDTO {
	private Long id;
	private String nome;
	private char cnpj;
	private String url;
	private String tipo; //talvez seja boolean ou enum
	

	@JsonProperty("id-usuario")
	private Long idUsuario;
	
	@JsonProperty("data-de-criação")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataDeCriação;
	
	@JsonProperty("data-alteração")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataAlteração;
	
	@JsonProperty("id-usuario-alterador")
	private String idUsuarioAlterador;
	
	@JsonProperty("id-usuario-criador")
	private String idUsuarioCriador;
	
	@JsonProperty("ip-criador")
	private String ipCriador;
	
	@JsonProperty("ip-proxi-criada")
	private String ipProxiCriada;
	
	@JsonProperty("ip-alterador")
	private String ipAlterador;
	
	@JsonProperty("ip-proxi-alterador")
	private String ipProxiAlterador;
}
