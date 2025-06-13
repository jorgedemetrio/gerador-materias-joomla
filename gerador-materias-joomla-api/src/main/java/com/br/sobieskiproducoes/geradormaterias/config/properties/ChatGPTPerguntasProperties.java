/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config.properties;

import java.util.List;

import org.springframework.validation.annotation.Validated;

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
@Validated
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTPerguntasProperties {

  @NotNull
  @NotBlank
  private String pedirMateria;

  @NotNull
  @NotBlank
  private String pedirDadosMateria;
  private String pedirDadosMateriaSeguinte;

  @NotNull
  @NotBlank
  private String pedirPerguntas;

  private String complementoFormatoPedirMateria;

  private List<String> falhas;
}
