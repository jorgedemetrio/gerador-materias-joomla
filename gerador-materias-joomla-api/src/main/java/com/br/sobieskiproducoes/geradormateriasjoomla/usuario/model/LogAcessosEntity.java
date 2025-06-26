/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.model;

import java.time.LocalDateTime;

import com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO.UsuarioDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ane Batista
 * @since 8 de abr. de 2024 12:36:41
 * @version 1.0.0
 * 
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_logAcessos")
public class LogAcessosEntity {
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name="id-usuario",  nullable = false, insertable = true, updatable = false, unique = true)
	private UsuarioDTO idUsuario;
	
	@Column(name="data-de-criacao", nullable = false, insertable = true, updatable = false)
	private LocalDateTime dataDeCriacao;
	
	@Column(name="data-alteracao", updatable=false, insertable=true, nullable=false, unique=true)
	private LocalDateTime dataAlteracao;

	@Column(name="id-usuario-alterador", updatable=false, insertable=true, nullable=false, unique=true)
	private UsuarioDTO idUsuarioAlterador;

	@Column(name="id-usuario-criador", updatable=false, insertable=true, nullable=false, unique=true)
	private UsuarioDTO idUsuarioCriador;

	@Column(name="ip-criador", updatable=false, insertable=true, nullable=false, unique=true)
	private UsuarioDTO ipCriador;

	@Column(name="ip-proxi-criada", updatable=false, insertable=true, nullable=false, unique=true)
	private UsuarioDTO ipProxiCriada;

	@Column(name="ip-alterador", updatable=false, insertable=true, nullable=false, unique=true)
	private UsuarioDTO ipAlterador;

	@Column(name="ip-proxi-alterador", updatable=false, insertable=true, nullable=false, unique=true)
	private UsuarioDTO ipProxiAlterador;
}
