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
import jakarta.persistence.OneToMany;
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
 * @since 13 de jun. de 2025 19:43:13
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_configuracao_joomla")
public class EmpresaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @Column(name = "nome", insertable = true, updatable = true, nullable = true, unique = false, length = 250)
  private String nome;

  @Column(name = "site", insertable = true, updatable = true, nullable = true, unique = false, length = 250)
  private String site;

  @Column(name = "instagram", insertable = true, updatable = true, nullable = true, unique = false, length = 250)
  private String istagram;

  @OneToMany(mappedBy = "empresa")
  private List<ConfiguracoesEntity> configuracoes;

}
