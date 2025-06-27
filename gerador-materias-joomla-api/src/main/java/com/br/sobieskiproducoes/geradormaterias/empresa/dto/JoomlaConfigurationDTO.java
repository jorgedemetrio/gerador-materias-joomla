/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import com.br.sobieskiproducoes.geradormaterias.utils.AbstractObservabilidadeDTO;

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
public class JoomlaConfigurationDTO extends AbstractObservabilidadeDTO {

    private Long id;

    @OneToOne
    private ConfiguracoesDTO configuracao;

    @NotNull
    @NotBlank
    private String url;

    @NotNull
    @NotBlank
    private String bearer;

    @Column(name = "idioma", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
    private String idioma;

}
