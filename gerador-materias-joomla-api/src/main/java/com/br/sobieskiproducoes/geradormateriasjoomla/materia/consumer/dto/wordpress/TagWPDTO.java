/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.wordpress;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * @since 11 de abr. de 2025 22:00:13
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagWPDTO {
  private Long id;
  private Long count;
  private String description;
  private String link;
    private String name;
    private String slug;
    private String taxonomy;
    private List<MetaWPDTO> meta;
    
    @JsonProperty("_links")
    private LinksWPDTO links; 
}
