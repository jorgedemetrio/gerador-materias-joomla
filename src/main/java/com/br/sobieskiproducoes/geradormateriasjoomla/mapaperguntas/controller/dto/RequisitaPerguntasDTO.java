/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

  @NotNull
  @NotEmpty
  private String mes;

  @NotEmpty
  private List<String> termos;
  private List<String> audiencias;

}
