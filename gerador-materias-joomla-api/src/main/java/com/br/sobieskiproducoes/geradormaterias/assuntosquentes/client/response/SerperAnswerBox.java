/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.assuntosquentes.client.response;

import java.util.List;

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
 * @since 13 de jun. de 2025 20:33:53
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SerperAnswerBox {
  private String snippet;
  private List<String> snippetHighlighted;
  private String title;
  private String link;
  private String date;
}
