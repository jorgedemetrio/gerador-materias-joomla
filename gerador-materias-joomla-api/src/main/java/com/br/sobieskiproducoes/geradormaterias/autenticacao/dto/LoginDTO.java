/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.autenticacao.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 01:10:15
 * @version 1.0.26 de jun. de 2025
 */

public record LoginDTO(@Size(min = 3, max = 20) @NotEmpty String username, @Size(min = 3, max = 20) @NotEmpty String password) {

}
