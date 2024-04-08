/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.model;

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
 * @since 7 de abr. de 2024 22:21:43
 * @version 1.0.0  *   
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_usuario")
public class UsuarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id-usuario", nullable=false, insertable=true, updatable=false)
	private Long idUsuario;
	
	@Column(name = "nome", insertable=true, length=50, nullable=false, updatable=true)
	private String nome;
	
	@Column(name ="usuario", insertable=true, length=20,nullable=false, updatable=true)
	private String usuario;
	
	@Column(name= "senha", insertable=true, nullable=false, length=8, updatable=true)
	private String senha;
	
	@Column(name="cpf", insertable=true, nullable=false, length=11, updatable=false)
	private String cpf;

}
