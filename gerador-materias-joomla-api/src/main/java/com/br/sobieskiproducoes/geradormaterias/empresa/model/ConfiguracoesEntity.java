/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.model;

import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.materia.model.MateriaEntity;

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
 * @since 25 de fev. de 2024 11:46:04
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_configuracao")
public class ConfiguracoesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @OneToMany(mappedBy = "configuracao")
  private ChatGPTPromptsEntity chatgptPrompts;

  @OneToMany(mappedBy = "configuracao")
  private ChatGPTConfigurationEntity chatgpt;

  @OneToMany(mappedBy = "configuracao")
  private JoomlaConfigurationEntity joomla;

  @OneToMany(mappedBy = "configuracao")
  private WordPressConfigurationEntity wordpress;

  @ManyToOne
  @JoinColumn(name = "id_empresa", insertable = true, updatable = true, nullable = false, unique = false)
  private EmpresaEntity empresa;

  @OneToMany(mappedBy = "configuracao")
  private List<MateriaEntity> materias;
}
