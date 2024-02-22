/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

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
public class MateriaDTO {

  private Long id;

  private Long idMateriaJoomla;
  private String tema;
  private Integer tituloSelecionado;
  private String titulo1;
  private String titulo2;
  private String titulo3;
  private String metaDescricao;
  private String introducao;
  private String materia;
  private String apelido;
  private LocalDateTime publicar;
  private CategoriaDTO categoria;
  private List<TagDTO> tags;

}
