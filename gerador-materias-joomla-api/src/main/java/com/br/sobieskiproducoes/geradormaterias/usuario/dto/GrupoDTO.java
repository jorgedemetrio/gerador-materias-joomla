/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.dto;

import com.br.sobieskiproducoes.geradormaterias.dto.AbstractObservabilidadeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 22:06:08
 * @version 1.0.26 de jun. de 2025
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(of = { "id" })
public class GrupoDTO extends AbstractObservabilidadeDTO {

    private String id;

    @NotBlank
    private String nome;
}
