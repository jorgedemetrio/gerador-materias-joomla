/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 1 de ago. de 2024 02:25:58
 * @version 1.0-1 de ago. de 2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionsDTO {
  private List<MapaPerguntaRetornoChatGPTDTO> questions;
}
