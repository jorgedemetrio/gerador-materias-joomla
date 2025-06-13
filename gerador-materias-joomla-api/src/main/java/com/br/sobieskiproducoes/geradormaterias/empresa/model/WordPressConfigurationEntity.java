/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "tbl_configuracao_joomla")
public class WordPressConfigurationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_configuracao", insertable = true, updatable = true, nullable = false, unique = false)
  private ConfiguracoesEntity configuracao;

  @NotNull
  @NotBlank
  @Column(name = "url", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
  private String url;

  @NotNull
  @NotBlank
  @Column(name = "bearer", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
  private String bearer;

  @Column(name = "idioma", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
  private String idioma;

}
