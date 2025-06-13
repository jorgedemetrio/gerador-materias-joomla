/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.model;

import com.br.sobieskiproducoes.geradormaterias.empresa.model.ConfiguracoesEntity;

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
@Table(name = "tbl_faq")
public class FAQEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_configuracao", insertable = true, updatable = true, nullable = false, unique = false)
  private ConfiguracoesEntity configuracao;

  @Column(name = "uuid_requisicao", nullable = true, insertable = true, updatable = true, unique = false, length = 1000)
  private String uuid;

  @Column(name = "pergunta", nullable = false, insertable = true, updatable = true, unique = false, length = 1000)
  private String pergunta;

  @Column(name = "resposta", nullable = false, insertable = true, updatable = true, unique = false, length = 1000)
  private String resposta;

  @ManyToOne
  @JoinColumn(name = "id_materia")
  private MateriaEntity materia;

}
