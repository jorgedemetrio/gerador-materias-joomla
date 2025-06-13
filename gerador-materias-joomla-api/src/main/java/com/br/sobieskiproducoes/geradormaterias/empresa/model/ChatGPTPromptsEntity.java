/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 *
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_chatgpt_treinamento")
public class ChatGPTPromptsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_configuracao", insertable = true, updatable = true, nullable = false, unique = false)
  private ConfiguracoesEntity configuracao;

  @NotNull
  @Valid
  @OneToMany(mappedBy = "configuracao")
  private ChatGPTPerguntasEntity prompts;

  @NotEmpty
  private List<String> audiencias;

  @NotNull
  @NotBlank
  @Column(name = "site", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
  private String site;

  @NotNull
  @NotEmpty
  private List<String> redesSociais;

  @NotNull
  @NotEmpty
  private List<String> especialista;

  private List<String> falhas;

}
