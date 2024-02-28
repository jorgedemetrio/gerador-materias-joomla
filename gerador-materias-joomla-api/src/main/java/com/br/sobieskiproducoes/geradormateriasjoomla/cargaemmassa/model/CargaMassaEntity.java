/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.model;

import java.time.LocalDateTime;

import com.br.sobieskiproducoes.geradormateriasjoomla.dto.StatusProcessamentoEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 26 de fev. de 2024 19:38:02
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_carga_promover")
public class CargaMassaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "uuid_requisicao", nullable = true, insertable = true, updatable = true, unique = false, length = 1000)
  private String uuid;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = true, insertable = true, updatable = true, unique = false)
  private StatusProcessamentoEnum status;

  @Column(name = "data_inicio_processo", nullable = true, insertable = true, updatable = true, unique = false)
  private LocalDateTime dataInicioProcesso;

  @Column(name = "data_fim_processo", nullable = true, insertable = true, updatable = true, unique = false)
  private LocalDateTime dataFimProcesso;

  @Column(name = "data_inicio_carga", nullable = true, insertable = true, updatable = true, unique = false)
  private LocalDateTime executadoInicio;

  @Column(name = "data_fim_carga", nullable = true, insertable = true, updatable = true, unique = false)
  private LocalDateTime executadoFim;

  @Column(name = "nota", nullable = true, insertable = true, updatable = true, unique = false, length = 3000)
  private String nota;

  @Column(name = "requisicao", nullable = true, insertable = true, updatable = true, unique = false, columnDefinition = "text")
  private String requisicao;
}
