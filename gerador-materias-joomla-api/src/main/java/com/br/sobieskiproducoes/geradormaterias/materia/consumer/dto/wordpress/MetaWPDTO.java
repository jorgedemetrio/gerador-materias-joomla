package com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.wordpress;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaWPDTO {

  @JsonProperty("pagelayer_contact_templates")
  public ArrayList<String> pagelayerContactTemplates;

  @JsonProperty("_pagelayer_content")
  public String pagelayerContent;
  public String footnotes;
}
