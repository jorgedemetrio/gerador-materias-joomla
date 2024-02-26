/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 26 de fev. de 2024 02:22:41
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HorarioRequisiscaoDTO {
  @NotEmpty(message = "O campo \"horario\" deve ser preenchido.")
  @Size(min = 5, max = 5, message = "Hora devem estar no formato HH:mm .")
  @Pattern(regexp = "[0-9]{2}:[0-9]{2}", message = "Hora devem estar no formato HH:mm .")
  private String horaio;

}
