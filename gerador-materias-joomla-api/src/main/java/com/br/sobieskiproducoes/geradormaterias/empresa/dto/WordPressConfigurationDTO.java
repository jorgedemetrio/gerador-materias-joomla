/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import com.br.sobieskiproducoes.geradormaterias.utils.AbstractObservabilidadeDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@ToString(exclude = { "bearer", "usuario", "senha" })
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_configuracao_joomla")
public class WordPressConfigurationDTO extends AbstractObservabilidadeDTO {

    private Long id;

    private ConfiguracoesDTO configuracao;

    @NotNull
    @NotBlank
    private String url;

    private String bearer;

    private String usuario;

    private String senha;

    private String idioma;

}
