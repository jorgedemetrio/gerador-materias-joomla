/**
 * 
 */
package com.br.sobieskiproducoes.geradormaterias.materia.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.dto.StatusProcessamentoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ane Batista
 * @since 15 de mar. de 2024 19:58:18
 * @version 1.0.0  *   
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MateriaDTO {
  private Long id;
  private Long idJoomla;
  private String tema;
  private String uuid;
  private String titulo1;
  private String titulo2;
  private String titulo3;
  private String metaDescricao;
  private String keywords;
  private String materia;
  private String apelido;

  private Integer tituloSelecionado;
  private String primeiroParagrafo;
  private String postInstagram;
  private LocalDateTime publicar;
  private String roteiro;
  private LocalDateTime criadoJoomla;
  private LocalDateTime criado = LocalDateTime.now();
  private LocalDateTime exportado;
  private CategoriaDTO categoria;
  private List<TagDTO> tags;
  private List<FaqDTO> faqs;
  private MapaPerguntaDTO peguntaPrincipal;
  private StatusProcessamentoEnum status;

}
