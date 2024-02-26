/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto;

import java.util.List;

import com.br.sobieskiproducoes.geradormateriasjoomla.dto.MesesEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 12:43:40
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequisitaPerguntasDTO {

  @JsonProperty(defaultValue = "15")
  private Integer quantidade = 15;

  private MesesEnum mes;

  @NotEmpty(message = "Deve citar pelo menos um termo para criar matéria.")
  private List<String> termos;
  private List<String> audiencias;

  private List<Long> categorias;

}
