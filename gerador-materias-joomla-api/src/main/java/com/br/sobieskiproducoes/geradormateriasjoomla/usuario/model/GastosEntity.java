/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.model;

import java.time.LocalDateTime;
import java.util.List;

import com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO.UsuarioDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ane Batista
 * @since 8 de abr. de 2024 15:19:02
 * @version 1.0.0
 * 
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_gastos")
public class GastosEntity {
	
	@Column(name="valor", updatable=true, insertable=true)
	private Float valor;
	
    private String origemGastos;
    
    private List<UsuarioDTO> usuario;
    
	@Column(name="id-usuario", updatable=false, insertable=true, nullable=false, unique=true)
	private Long idUsuario;

	@Column(name="data-criacao", updatable=false, insertable=true, nullable=false, unique=true)
	private LocalDateTime dataDeCriacao;

	@Column(name="data-alteracao", updatable=false, insertable=true, nullable=false, unique=true)
	private LocalDateTime dataAlteracao;

	@Column(name="id-usuario-alterador", updatable=false, insertable=true, nullable=false, unique=true)
	private String idUsuarioAlterador;

	@Column(name="id-usuario-criador", updatable=false, insertable=true, nullable=false, unique=true)
	private String idUsuarioCriador;

	@Column(name="ip-criador", updatable=false, insertable=true, nullable=false, unique=true)
	private String ipCriador;

	@Column(name="ip-proxi-criada", updatable=false, insertable=true, nullable=false, unique=true)
	private String ipProxiCriada;

	@Column(name="ip-alterador", updatable=false, insertable=true, nullable=false, unique=true)
	private String ipAlterador;

	@Column(name="ip-proxi-alterador", updatable=false, insertable=true, nullable=false, unique=true)
	private String ipProxiAlterador;
}
