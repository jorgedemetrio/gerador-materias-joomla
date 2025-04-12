/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.joomla;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 14:45:31
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImagesMateriaDTO {
  @JsonProperty("image_intro")
  private String imageIntro;

  @JsonProperty("image_intro_alt")
  private String imageIntroAlt;

  @JsonProperty("float_intro")
  private String floatIntro;

  @JsonProperty("image_intro_caption")
  private String imageIntroCaption;

  @JsonProperty("image_fulltext")
  private String imageFulltext;
  @JsonProperty("image_fulltext_alt")
  private String imageFulltextAlt;
  @JsonProperty("float_fulltext")
  private String floatFulltext;
  @JsonProperty("image_fulltext_caption")
  private String imageFulltextCaption;

}
