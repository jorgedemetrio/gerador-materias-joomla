/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.utils.AbstractObservabilidadeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
@ToString
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatGPTPromptsDTO extends AbstractObservabilidadeDTO {

    private Long id;

    private ConfiguracoesDTO configuracao;

    @NotNull
    @Valid
    private List<ChatGPTPerguntasDTO> prompts;

    @NotEmpty
    private List<String> audiencias;

    @NotNull
    @NotBlank
    @Column(name = "site", nullable = false, insertable = true, updatable = true, unique = false, length = 250)
    private String site;

    @NotNull
    @NotEmpty
    private List<String> redesSociais;

    @NotNull
    @NotEmpty
    private List<String> especialista;

    private List<String> falhas;

}
