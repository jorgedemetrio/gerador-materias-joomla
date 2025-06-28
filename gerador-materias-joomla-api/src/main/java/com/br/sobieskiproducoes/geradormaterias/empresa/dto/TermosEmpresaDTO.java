/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import com.br.sobieskiproducoes.geradormaterias.utils.AbstractObservabilidadeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
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
 * @since 27 de jun. de 2025 01:27:41
 * @version 1.0.27 de jun. de 2025
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TermosEmpresaDTO extends AbstractObservabilidadeEntity {

    private String id;

    @NotBlank
    @Size(min = 10, max = 250)
    private String nome;

    private EmpresaDTO empresa;

}
