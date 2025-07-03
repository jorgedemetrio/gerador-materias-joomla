/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.domain;

import com.br.sobieskiproducoes.geradormaterias.config.AttributeEncryptorConfig;
import com.br.sobieskiproducoes.geradormaterias.domain.AbstractObservabilidadeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@ToString(exclude = { "bearer" })
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_joomla_configuracao")
public class JoomlaConfigurationEntity extends AbstractObservabilidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 40)
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_configuracao", insertable = true, updatable = true, nullable = false, unique = false)
    private ConfiguracoesEntity configuracao;

    @NotNull
    @NotBlank
    @Column(name = "url", nullable = false, insertable = true, updatable = true, unique = false, length = 250)
    private String url;

    @NotNull
    @NotBlank
    @Column(name = "bearer", nullable = false, insertable = true, updatable = true, unique = false, length = 250)
    @Convert(converter = AttributeEncryptorConfig.class)
    private String bearer;

    @Column(name = "idioma", nullable = true, insertable = true, updatable = true, unique = false, length = 100)
    private String idioma;

}
