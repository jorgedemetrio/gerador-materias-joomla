/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:22:54
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_materia")
public class MateriaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "id_joomla", nullable = false, insertable = true, updatable = false, unique = true)
  private Long idJoomla;

  @Column(name = "tema_proposto", nullable = false, insertable = true, updatable = false, unique = false, length = 1000)
  private String tema;

  @Column(name = "titulo_selecionado", nullable = false, insertable = true, updatable = true, unique = false)
  private Integer tituloSelecionado;

  @Column(name = "titulo_1", nullable = false, insertable = true, updatable = true, unique = false, length = 200)
  private String titulo1;

  @Column(name = "titulo_2", nullable = false, insertable = true, updatable = true, unique = false, length = 200)
  private String titulo2;

  @Column(name = "titulo_3", nullable = false, insertable = true, updatable = true, unique = false, length = 200)
  private String titulo3;

  @Column(name = "meta_descircao", nullable = false, insertable = true, updatable = true, unique = false, length = 150)
  private String metaDescricao;

  @Column(name = "introducao", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
  private String introducao;

  @Column(name = "materia", nullable = false, insertable = true, updatable = true, unique = false, columnDefinition = "TEXT")
  private String materia;

  @Column(name = "alias", nullable = true, insertable = true, updatable = true, unique = false, length = 100)
  private String apelido;

  @Column(name = "data_publicar", nullable = true, insertable = true, updatable = true, unique = false)
  private LocalDateTime publicar;

  @ManyToOne
  @JoinColumn(name = "id_categoria")
  private CategoriaEntity categoria;

  @ManyToMany
  @JoinTable(joinColumns = { @JoinColumn(name = "id_categoria", table = "tbl_materia_tag") })
  private List<TagEntity> tags;

}
