/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.model;

import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.empresa.model.ConfiguracoesEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
 * @since 21 de fev. de 2024 17:42:49
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_tag")
public class TagEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "uuid_requisicao", nullable = true, insertable = true, updatable = true, unique = false, length = 1000)
  private String uuid;

  @ManyToOne
  @JoinColumn(name = "id_configuracao", insertable = true, updatable = true, nullable = false, unique = false)
  private ConfiguracoesEntity configuracao;

  @Column(name = "id_wordpress", nullable = true, insertable = true, updatable = true)
  private Long idWprdPress;

  @Column(name = "id_joomla", nullable = true, insertable = true, updatable = true, unique = false)
  private Long idJoomla;

  @Column(name = "titulo", nullable = false, insertable = true, updatable = true, unique = false, length = 1000)
  private String titulo;

  @Column(name = "alias", nullable = true, insertable = true, updatable = true, unique = false, length = 500)
  private String apelido;

  @Column(name = "idioma", nullable = true, insertable = true, updatable = true, unique = false, length = 500)
  private String language;

  @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "tags")
  private List<MateriaEntity> materias;

}
