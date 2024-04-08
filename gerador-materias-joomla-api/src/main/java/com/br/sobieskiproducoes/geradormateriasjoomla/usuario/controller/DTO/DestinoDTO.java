/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO;

import java.time.LocalDateTime;
import java.util.List;

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
 * @since 7 de abr. de 2024 13:52:38
 * @version 1.0.0  *   
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DestinoDTO {
	private String id;
	private String url;
	private List<UsuarioDTO> usuario;
	private String senha;
	private String token;
	private String tipo;

	@JsonProperty("id-usuario")
	private Long idUsuario;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonProperty("data-de-criação")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataDeCriação;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonProperty("data-alteraao")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataAlteracao;

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
