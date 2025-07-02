/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.br.sobieskiproducoes.geradormaterias.dto.AbstractObservabilidadeDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSistemaDTO;
import com.br.sobieskiproducoes.geradormaterias.validation.CNPJ;
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
 * @author Jorge Demetrio
 * @version 1.0
 * @since 13 de jun. de 2025 19:43:13
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpresaDTO extends AbstractObservabilidadeDTO {

    private String id;

    @NotBlank
    @Size(min = 5, max = 200)
    private String nome;

    @NotBlank
    @CNPJ
    private String cnpj;

    private ConfiguracoesDTO configuracao;

    private List<UsuarioSistemaDTO> usuarios;

    private List<TermosEmpresaDTO> termos;

}
