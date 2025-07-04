/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.dto;

import java.time.LocalDateTime;

import org.springframework.validation.annotation.Validated;

import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSimplificadoDTO;
import com.br.sobieskiproducoes.geradormaterias.utils.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author JorgeDemetrioPC
 * @since 27 de jun. de 2025 01:49:41
 * @version 1.0.27 de jun. de 2025
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public abstract class AbstractObservabilidadeDTO {

    private UsuarioSimplificadoDTO criador;

    private UsuarioSimplificadoDTO alterador;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime criado;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime alterado;

    private String ipCriador;

    private String ipProxyCriador;

    private String ipAlterador;

    private String ipProxyAlterador;

    private StatusEnum statusDado;
}
