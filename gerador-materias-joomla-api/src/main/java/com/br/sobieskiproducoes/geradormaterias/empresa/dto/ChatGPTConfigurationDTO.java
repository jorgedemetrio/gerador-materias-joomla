/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import com.br.sobieskiproducoes.geradormaterias.dto.AbstractObservabilidadeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatGPTConfigurationDTO extends AbstractObservabilidadeDTO {

    private Long id;

    private ConfiguracoesDTO configuracao;

    @NotNull
    @NotBlank
    private String bearer;

    private String assistente;

    private String thread;

    @NotNull
    @NotBlank
    private String organization;

    @NotNull
    @NotBlank
    private String model;

    @Min(0)
    @Max(1)
    private Double temperature;

    private String roleUser;

    private String roleSystem;

    private String roleAssistant;

    private String maxTokens;

}
