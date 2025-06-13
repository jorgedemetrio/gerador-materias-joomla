/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.assuntosquentes.client.response;

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
 * @since 13 de jun. de 2025 20:36:38
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SerperKnowledgeGraphDTO {
  private String title;
  private String type;
  private String website;
  private String imageUrl;
  private String description;
  private String descriptionSource;
  private String descriptionLink;
  private SerperAttributesDTO attributes;
}
