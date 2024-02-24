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


  @Column(name = "id_chatgpt", nullable = true, insertable = true, updatable = true, unique = false, length = 200)
  private String idChatGpt;

  @Column(name = "objeto", nullable = true, insertable = true, updatable = true, unique = false, length = 200)
  private String object;

  @Column(name = "created", nullable = true, insertable = true, updatable = true, unique = false)
  private Long created;

  @Column(name = "model", nullable = true, insertable = true, updatable = true, unique = false, length = 200)
  private String model;



  @Column(name = "index", nullable = true, insertable = true, updatable = true, unique = false)
  private Integer index;

  @Column(name = "role", nullable = true, insertable = true, updatable = true, unique = false, length = 200)
  private String role;



  @Column(name = "logprobs", nullable = true, insertable = true, updatable = true, unique = false, length = 200)
  private String logprobs;

  @Column(name = "finish_reason", nullable = true, insertable = true, updatable = true, unique = false, length = 200)
  private String finishReason;

  @Column(name = "prompt_tokens", nullable = true, insertable = true, updatable = true, unique = false)
  private Long promptTokens;

  @Column(name = "completion_tokens", nullable = true, insertable = true, updatable = true, unique = false)
  private Long completionTokens;

  @Column(name = "total_tokens", nullable = true, insertable = true, updatable = true, unique = false)
  private Long totalTokens;

  @Column(name = "system_fingerprint", nullable = true, insertable = true, updatable = true, unique = false, length = 200)
  private String systemFingerprint;


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
  @Column(name = "pergunta", nullable = false, insertable = true, updatable = false, unique = false, columnDefinition = "text")
  private String pergunta;

  @NotNull
  @Column(name = "resposta", nullable = false, insertable = true, updatable = false, unique = false, columnDefinition = "text")
  private String content;

  @NotNull
  @Column(name = "uuid", nullable = false, insertable = true, updatable = false, unique = true, length = 200)
  private String uuid;

  @ManyToOne
  @JoinColumn(name = "id_materia", nullable = true, insertable = true, updatable = true, unique = false)
  private MateriaEntity materia;



}
