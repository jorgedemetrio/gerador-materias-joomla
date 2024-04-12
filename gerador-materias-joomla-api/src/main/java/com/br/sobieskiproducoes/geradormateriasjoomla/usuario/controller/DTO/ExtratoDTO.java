/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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
public class ExtratoDTO {
	
	private Float valor;
    private OrigemGastosDTO origemGastos;
    
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")    
    private LocalDateTime data;
    
    private UsuarioDTO usuario;
    
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonProperty("data-de-criacao")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataDeCriacao;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonProperty("data-alteracao")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataAlteracao;
	
	@JsonProperty("id-usuario-alterador")
	private UsuarioDTO idUsuarioAlterador;
	
	@JsonProperty("id-usuario-criador")
	private UsuarioDTO idUsuarioCriador;
	
	@JsonProperty("ip-criador")
	private UsuarioDTO ipCriador;
	
	@JsonProperty("ip-proxi-criada")
	private UsuarioDTO ipProxiCriada;
	
	@JsonProperty("ip-alterador")
	private UsuarioDTO ipAlterador;
	
	@JsonProperty("ip-proxi-alterador")
	private UsuarioDTO ipProxiAlterador;
}
