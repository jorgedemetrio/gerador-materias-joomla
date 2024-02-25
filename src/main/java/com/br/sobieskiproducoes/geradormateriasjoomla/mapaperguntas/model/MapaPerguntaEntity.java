/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model;

import java.time.LocalDate;
import java.util.List;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
 * @since 24 de fev. de 2024 12:54:57
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_mapa_perguntas")
public class MapaPerguntaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "uuid_requisicao", nullable = true, insertable = true, updatable = true, unique = false, length = 1000)
  private String uuid;

  @Column(name = "pergunta", nullable = false, insertable = true, updatable = false, unique = false, length = 3000)
  private String pergunta;

  @OneToMany(mappedBy = "peguntaPrincipal")
  private List<SubMapaPerguntasEntity> perguntasAlternativas;

  @Column(name = "ordem_relevancia", nullable = true, insertable = true, updatable = true, unique = false)
  private Integer ordemRelevancia;

  @Column(name = "motivo_sugestao", nullable = true, insertable = true, updatable = true, unique = false)
  private String motivoSugestao;

  @Column(name = "perfil_enquadra", nullable = true, insertable = true, updatable = true, unique = false)
  private String perfilEnquadra;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_categoria", nullable = false, insertable = true, updatable = true, unique = false)
  private CategoriaEntity categoria;

  @Column(name = "data_sugestao_publicacao", nullable = true, insertable = true, updatable = true, unique = false)
  private LocalDate dataSugestaoPublicacao;
}
