/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import com.br.sobieskiproducoes.geradormaterias.utils.AbstractObservabilidadeDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
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
@ToString(exclude = { "bearer" })
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JoomlaConfigurationDTO extends AbstractObservabilidadeDTO {

    private Long id;

    @OneToOne
    private ConfiguracoesDTO configuracao;

    @NotNull
    @NotBlank
    private String url;

    @NotNull
    @NotBlank
    @JsonIgnore
    private String bearer;

    @Column(name = "idioma", nullable = true, insertable = true, updatable = true, unique = false, length = 100)
    private String idioma;

}
