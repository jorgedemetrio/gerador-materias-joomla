/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 02:50:59
 * @version 1.0.26 de jun. de 2025
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenSessionDTO(String token, Instant expire, Long expireSecond, List<String> empresasIds, String empresaPrincipalId) {

}
