/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto;

import java.time.LocalDate;
import java.util.List;

import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.SubMapaPerguntasEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.TermosMapaPerguntaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * @author Ane Batista
 * @since 15 de mar. de 2024 20:19:08
 * @version 1.0.0
 * 
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
	  private List<TermosMapaPerguntaEntity> termos;
	  private List<SubMapaPerguntasEntity> perguntasAlternativas;
	  private Integer ordemRelevancia;
	  private String motivoSugestao;
	  private String perfilEnquadra;
	  private CategoriaEntity categoria;
	  private List<MateriaEntity> materias;
	  @JsonSerialize(using = LocalDateTimeSerializer.class)
	  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	  private LocalDate dataSugestaoPublicacao;
	  
}
