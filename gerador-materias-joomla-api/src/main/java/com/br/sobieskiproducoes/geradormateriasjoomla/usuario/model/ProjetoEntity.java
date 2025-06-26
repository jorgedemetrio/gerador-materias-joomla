/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.model;

import java.time.LocalDateTime;

import com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO.TipoEnum;
import com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO.UsuarioDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * @since 8 de abr. de 2024 12:02:54
 * @version 1.0.0  *   
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_projeto")
public class ProjetoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome", nullable = false, insertable=true, updatable=true)
	private UsuarioDTO nome;
	
	@Column(name="cnpj", nullable=true, insertable=true, length=14)
	private UsuarioDTO cnpj;
	
	@Column(name="url", updatable=true, insertable=true, nullable=false)
	private UsuarioDTO url;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo", updatable=true, insertable=true, nullable=false)
	private TipoEnum tipo;

	@Column(name="data-criacao", updatable=false, insertable=true, nullable=false, unique=true)
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
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id-usuario", updatable=false, insertable=true, nullable=false, unique=true)
	private UsuarioDTO idUsuario;
	
	
}
