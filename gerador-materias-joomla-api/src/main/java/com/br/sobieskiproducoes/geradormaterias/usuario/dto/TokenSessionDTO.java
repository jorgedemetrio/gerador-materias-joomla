/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.dto;

import java.time.Instant;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 02:50:59
 * @version 1.0.26 de jun. de 2025
 */
public record TokenSessionDTO(String token, Instant expire, Long expireSecond) {

}
