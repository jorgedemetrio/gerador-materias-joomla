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
 * @since 25 de abr. de 2024 11:39:41
 * @version 1.0-25 de abr. de 2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class YoutubeGerarMateriaRoteiroRequestDTO {
  private String link;

  private String roteiro;

  private Boolean publicarCMSVideo = Boolean.FALSE;

  private Boolean publicarCMSPlayList = Boolean.FALSE;
}
