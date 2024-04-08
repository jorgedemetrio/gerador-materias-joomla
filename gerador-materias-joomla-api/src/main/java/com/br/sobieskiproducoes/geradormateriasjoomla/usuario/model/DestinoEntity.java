/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.model;

import com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO.TipoEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * @since 7 de abr. de 2024 23:09:04
 * @version 1.0.0  *   
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_destino")
public class DestinoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, insertable = true, updatable = false)
	private String id;

	@Column(name = "url", nullable = false, insertable = true, updatable = false)
	private String url;

	@Column(name = "usuario", insertable = true, length = 20, nullable = false, updatable = true)
	private String usuario;

	@Column(name = "senha", insertable = true, nullable = false, length = 8, updatable = true)
	private String senha;

	@Column(name = "token", insertable = true, nullable = false, length = 2000, updatable = false)
	private String token;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", insertable = true, nullable = false)
	private TipoEnum tipo;
}
