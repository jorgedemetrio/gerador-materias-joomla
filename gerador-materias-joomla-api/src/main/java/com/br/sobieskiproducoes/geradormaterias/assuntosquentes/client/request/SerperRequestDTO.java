/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.assuntosquentes.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 14 de jun. de 2025 01:24:28
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SerperRequestDTO {
  private String q;
  private String location = "Brazil";
  private String gl = "br";
  private String hl = "pt-br";
}
