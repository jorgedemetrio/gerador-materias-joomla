/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 11:46:04
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ConfiguracoesDTO {

    private Long id;

    private String site;

    private String istagram;

    private String youtube;

}
