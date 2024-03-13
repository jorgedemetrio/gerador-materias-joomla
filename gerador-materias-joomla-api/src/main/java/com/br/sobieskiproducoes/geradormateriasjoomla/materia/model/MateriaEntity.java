/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.model;

import java.time.LocalDateTime;
import java.util.List;

import com.br.sobieskiproducoes.geradormateriasjoomla.dto.StatusProcessamentoEnum;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.MapaPerguntaEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

  @Column(name = "id_joomla", nullable = true, insertable = true, updatable = false)
  private Long idJoomla;

  @Column(name = "tema_proposto", nullable = true, insertable = true, updatable = true, unique = false, length = 1000)
  private String tema;

  @Column(name = "uuid_requisicao", nullable = true, insertable = true, updatable = true, unique = false, length = 1000)
  private String uuid;

  @Column(name = "titulo_selecionado", nullable = true, insertable = true, updatable = true, unique = false)
  private Integer tituloSelecionado;

  @Column(name = "titulo_1", nullable = true, insertable = true, updatable = true, unique = false, length = 200)
  private String titulo1;

  @Column(name = "titulo_2", nullable = true, insertable = true, updatable = true, unique = false, length = 200)
  private String titulo2;

  @Column(name = "titulo_3", nullable = true, insertable = true, updatable = true, unique = false, length = 200)
  private String titulo3;

  @Column(name = "meta_descircao", nullable = true, insertable = true, updatable = true, unique = false, length = 250)
  private String metaDescricao;

  @Column(name = "keywords", nullable = true, insertable = true, updatable = true, unique = false, length = 1000)
  private String keywords;

  @Column(name = "introducao", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
  private String primeiroParagrafo;

  @Column(name = "materia", nullable = false, insertable = true, updatable = true, unique = false, columnDefinition = "TEXT")
  private String materia;

  @Column(name = "alias", nullable = true, insertable = true, updatable = true, unique = true, length = 500)
  private String apelido;

  @Column(name = "post_instagram", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
  private String postInstagram;

  @Column(name = "data_publicar", nullable = true, insertable = true, updatable = true, unique = false)
  private LocalDateTime publicar;

  @Column(name = "roteiro", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
  private String roteiro;

  @Column(name = "cricao_na_plataforma", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
  private LocalDateTime criadoJoomla;

  @Column(name = "cricao_banco", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
  private LocalDateTime criado = LocalDateTime.now();

  @Column(name = "exportado_para_plataforma", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
  private LocalDateTime exportado;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_categoria")
  private CategoriaEntity categoria;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(joinColumns = { @JoinColumn(name = "id_categoria", table = "tbl_materia_tag") })
  private List<TagEntity> tags;

  @OneToMany(cascade = CascadeType.ALL)
  private List<FAQEntity> faqs;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_mapa_pergunta", nullable = true, insertable = true, updatable = true, unique = false)
  private MapaPerguntaEntity peguntaPrincipal;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = true, insertable = true, updatable = true, unique = false)
  private StatusProcessamentoEnum status;

}
