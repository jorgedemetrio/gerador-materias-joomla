/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.assuntosquentes.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 13 de jun. de 2025 20:38:01
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SerperAttributesDTO {
  @JsonProperty("Headquarters")
  private String headquarters;

  @JsonProperty("CEO")
  private String ceo;

  @JsonProperty("Founded")
  private String founded;

  @JsonProperty("Sales")
  private String sales;

  @JsonProperty("Products")
  private String products;

  @JsonProperty("Founders")
  private String founders;

  @JsonProperty("Subsidiaries")
  private String subsidiaries;

  @JsonProperty("Industry")
  private String industry;

}
