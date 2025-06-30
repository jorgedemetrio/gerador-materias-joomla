/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.materia.controller.dto.CategoriaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.controller.dto.MateriaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.controller.dto.TagDTO;
import com.br.sobieskiproducoes.geradormaterias.utils.AbstractObservabilidadeDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 11:46:04
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfiguracoesDTO extends AbstractObservabilidadeDTO {

    private Long id;

    private String site;

    private String istagram;

    private String youtube;

    @Valid
    private ChatGPTPromptsDTO chatgptPrompts;

    @NotNull
    @Valid
    private ChatGPTConfigurationDTO chatgpt;

    @Valid
    private JoomlaConfigurationDTO joomla;

    @Valid
    private WordPressConfigurationDTO wordpress;

    @Valid
    @JsonIgnore
    private EmpresaDTO empresa;

    @Valid
    private List<MateriaDTO> materias;

    @Valid
    private List<TagDTO> tags;

    @Valid
    private List<CategoriaDTO> categorias;

}
