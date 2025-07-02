/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.br.sobieskiproducoes.geradormaterias.dto.AbstractObservabilidadeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

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
@Validated
@ToString
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatGPTPerguntasDTO extends AbstractObservabilidadeDTO {

    private Long id;

    private ChatGPTPromptsDTO configuracao;

    @NotNull
    @NotBlank
    private String pedirMateria;

    @NotNull
    @NotBlank
    private String pedirDadosMateria;
    private String pedirDadosMateriaSeguinte;

    @NotNull
    @NotBlank
    private String pedirPerguntas;

    private String complementoFormatoPedirMateria;

    private List<String> falhas;
}
