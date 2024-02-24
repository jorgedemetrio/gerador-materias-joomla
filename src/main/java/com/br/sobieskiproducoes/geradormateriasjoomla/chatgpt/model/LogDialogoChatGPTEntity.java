/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.model;

import java.time.LocalDateTime;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 23 de fev. de 2024 19:03:57
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "tbl_dialogo_chatgpt")
public class LogDialogoChatGPTEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "dt_cracao", nullable = false, insertable = true, updatable = false, unique = false)
  private LocalDateTime data;

  @NotNull
  @Column(name = "dt_inicio_processamento", nullable = false, insertable = true, updatable = false, unique = false)
  private LocalDateTime inicio;

  @NotNull
  @Column(name = "pergunta", nullable = false, insertable = true, updatable = false, unique = false, length = 5000)
  private String pergunta;

  @NotNull
  @Column(name = "resposta", nullable = false, insertable = true, updatable = false, unique = false, length = 5000)
  private String resposta;

  @ManyToOne
  @JoinColumn(name = "id_materia", nullable = true, insertable = true, updatable = true, unique = false)
  private MateriaEntity materia;
}
