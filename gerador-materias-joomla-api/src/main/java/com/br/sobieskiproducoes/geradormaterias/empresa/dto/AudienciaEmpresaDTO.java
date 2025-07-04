/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import com.br.sobieskiproducoes.geradormaterias.dto.AbstractObservabilidadeDTO;

import jakarta.validation.constraints.NotBlank;
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
public class AudienciaEmpresaDTO extends AbstractObservabilidadeDTO {

    private String id;

    @NotBlank
    private String nome;

    private EmpresaDTO empresa;

}
