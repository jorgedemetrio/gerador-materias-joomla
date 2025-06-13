/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.fontedados.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 14 de mar. de 2024 22:16:18
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class YoutubeVideoDTO {

  private String titulo;
  private String descricao;
  private String legenda;
  private List<String> tags;
}
