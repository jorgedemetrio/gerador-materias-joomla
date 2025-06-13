/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.mapaperguntas.controller.dto;

import java.time.LocalDate;
import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.materia.controller.dto.CategoriaDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 12:54:57
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MapaPerguntaDTO {

  private Long id;

  private String uuid;

  private String pergunta;

  private List<String> perguntasAlternativas;

  private List<String> termos;

  private Integer ordemRelevancia;

  private String motivoSugestao;

  private String perfilEnquadra;

  private CategoriaDTO categoria;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate dataSugestaoPublicacao;
}
