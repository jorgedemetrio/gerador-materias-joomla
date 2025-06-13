/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.fontedados.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 14 de mar. de 2024 21:07:53
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class YoutubeGerarMateriaRequestDTO {

  private String link;

  private Boolean publicarCMSVideo = Boolean.FALSE;

  private Boolean publicarCMSPlayList = Boolean.FALSE;

}
