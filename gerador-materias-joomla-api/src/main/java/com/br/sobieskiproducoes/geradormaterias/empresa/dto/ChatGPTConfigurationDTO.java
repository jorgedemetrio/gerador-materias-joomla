/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import com.br.sobieskiproducoes.geradormaterias.utils.AbstractObservabilidadeDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 *
 */
@Getter
@Setter
@ToString(exclude = { "bearer", "assistente", "organization" })
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class ChatGPTConfigurationDTO extends AbstractObservabilidadeDTO {

    private Long id;

    private ConfiguracoesDTO configuracao;

    @NotNull
    @NotBlank
    private String bearer;

    private String assistente;

    @NotNull
    @NotBlank
    private String organization;

    @NotNull
    @NotBlank
    private String model;

    @NotNull
    @Min(0)
    @Max(1)
    private Double temperature;

    @NotNull
    @NotBlank
    private String roleUser;

    @NotNull
    @NotBlank
    private String roleSystem;

    @NotNull
    @NotBlank
    private String roleAssistant;

    @NotNull
    @NotBlank
    private String maxTokens;

}
