/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:22:54
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropostaMateriaDTO {


  private Long id;

  private Long idJoomla;
  private String tema;
  private Integer tituloSelecionado;
  private List<String> titulos;

  @JsonProperty("primeiro-paragrafo")
  private String primeiroParagrafo;


  private String materia;

  @JsonProperty("meta-descricao")
  private String metaDescricao;

  private String keywords;

  @JsonProperty("url-proposta")
  private String apelido;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime publicar;
  private CategoriaDTO categoria;
  private List<String> tags;
  private List<FaqDTO> faqs;

  private String roteiro;

  private String uuid;

  @JsonProperty("post-instagram")
  private String postInstagram;

}
