/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 02:50:59
 * @version 1.0.26 de jun. de 2025
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenSessionDTO(String token, Instant expire, @JsonProperty("expire-second") Long expireSecond,
        @JsonProperty("empresa-ids") List<String> empresasIds, @JsonProperty("empresa-principal-id") String empresaPrincipalId) {

}
