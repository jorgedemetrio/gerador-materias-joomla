package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.wordpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
public class HrefWPDTO {
  public String taxonomy;
  public Long count;
  public Long id;
  public Boolean embeddable;
  public String href;
  public String name;
  public Boolean templated;
}
