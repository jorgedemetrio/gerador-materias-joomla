/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.model;

import java.time.LocalDateTime;

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
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 9 de jan. de 2025 01:24:37
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_materia_log_processo")
public class ProcessamentoLogMateriaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_configuracao", insertable = true, updatable = true, nullable = false, unique = false)
  private ConfiguracoesEntity configuracao;

  @Column(name = "stream_materia", nullable = true, insertable = true, updatable = true, unique = false, columnDefinition = "TEXT")
  private String textoMateria;

  @Column(name = "info_materia", nullable = true, insertable = true, updatable = true, unique = false, columnDefinition = "TEXT")
  private String infoGeracaoMateria;

  @Column(name = "stream_dados_materia", nullable = false, insertable = true, updatable = true, unique = false, columnDefinition = "TEXT")
  private String dados;

  @Column(name = "gerado_materia", nullable = false, insertable = true, updatable = true, unique = false)
  private LocalDateTime geradoMateria;

  @Column(name = "gerado_dados", nullable = false, insertable = true, updatable = true, unique = false)
  private LocalDateTime geradoDados;

  @ManyToOne
  @JoinColumn(name = "id_materia", nullable = true, insertable = true, updatable = true, unique = false)
  private MateriaEntity materia;

}
