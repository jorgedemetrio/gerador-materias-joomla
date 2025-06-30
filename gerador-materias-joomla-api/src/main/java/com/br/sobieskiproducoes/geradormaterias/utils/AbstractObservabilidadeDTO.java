/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.utils;

import java.time.LocalDateTime;

import org.springframework.validation.annotation.Validated;

import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @Valid
    @NotNull
    @JsonIgnore
    private UsuarioDTO criador;

    @Valid
    @JsonIgnore
    private UsuarioDTO alterador;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @PastOrPresent
    private LocalDateTime criado;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @PastOrPresent
    private LocalDateTime alterado;

    @NotBlank
    private String ipCriador;

    @NotBlank
    private String ipProxyCriador;

    @NotBlank
    private String ipAlterador;

    @NotBlank
    private String ipProxyAlterador;

    private StatusEnum statusDado;
}
