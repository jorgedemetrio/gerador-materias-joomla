/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 15:00:10
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlDTO {
  private String urla;
  private String urlatext;
  private String targeta;
  private String urlb;
  private String urlbtext;
  private String targetb;
  private String urlc;
  private String urlctext;
  private String targetc;
}
