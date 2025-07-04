/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.dto;

import java.time.LocalDateTime;

import com.br.sobieskiproducoes.geradormaterias.validation.CPF;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 21:21:19
 * @version 1.0.26 de jun. de 2025
 */
@Getter
@Setter
@ToString(exclude = { "senha" })
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioRequestDTO {
    private String id;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]{3,250}$")
    @NotBlank
    private String nome;

    @Size(min = 8, max = 200)
    @Pattern(regexp = "^[a-zA-Z0-1_\\-\\@]{8,200}$")
    @NotBlank
    private String usuario;

    @Size(min = 8, max = 20)
    @Pattern(regexp = "^[a-zA-Z\\d\\_\\-\\!\\@\\#\\$\\%\\&\\*\\?]{8,20}$")
    @NotBlank
    private String senha;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @NotBlank
    @Email
    private String email;

    private Boolean habilitado;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @FutureOrPresent
    private LocalDateTime expira;

    @NotBlank
    @CPF
    private String cpf;

}
