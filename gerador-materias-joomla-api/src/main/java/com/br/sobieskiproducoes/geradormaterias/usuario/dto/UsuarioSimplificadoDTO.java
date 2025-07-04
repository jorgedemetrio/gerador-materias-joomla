/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioSimplificadoDTO {
    private String id;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]{0,250}$")
    @NotBlank
    private String nome;

    @Size(min = 8, max = 200)
    @Pattern(regexp = "^[a-zA-Z0-1_\\-\\@]{8,200}$")
    @NotBlank
    private String usuario;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @NotBlank
    @Email
    private String email;

}
