/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.model;

import java.time.LocalDateTime;

import com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO.TipoEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String nome;
	
	@Column(name="cnpj", nullable=true, insertable=true, length=14)
	private String cnpj;
	
	@Column(name="url", updatable=true, insertable=true, nullable=false)
	private String url;
	
	@Column(name="tipo", updatable=true, insertable=true, nullable=false)
	private TipoEnum tipo;

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
