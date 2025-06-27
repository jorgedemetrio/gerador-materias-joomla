/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
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
public class GrupoDTO {

    private String id;

    @NotBlank
    private String nome;
}
