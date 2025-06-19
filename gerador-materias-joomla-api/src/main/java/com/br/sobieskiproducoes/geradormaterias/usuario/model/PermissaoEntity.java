/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 14 de jun. de 2025 02:28:55
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_permissao")
public class PermissaoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "nome", nullable = false, insertable = true, updatable = false, unique = true, length = 255)
  private String nome;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "tbl_usuario_grupo", inverseJoinColumns = { @JoinColumn(name = "id_permissao", table = "tbl_grupo_permissao") }, joinColumns = {
      @JoinColumn(name = "id_grupo", table = "tbl_grupo_permissao") })
  private List<GrupoEntity> grupos;

}
