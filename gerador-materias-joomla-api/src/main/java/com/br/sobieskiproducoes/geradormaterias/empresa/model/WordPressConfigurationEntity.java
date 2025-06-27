/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.model;

import com.br.sobieskiproducoes.geradormaterias.utils.AttributeEncryptor;

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
@ToString(exclude = { "bearer", "usuario", "senha" })
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_configuracao_joomla")
public class WordPressConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_configuracao", insertable = true, updatable = true, nullable = false, unique = false)
    private ConfiguracoesEntity configuracao;

    @NotNull
    @NotBlank
    @Column(name = "url", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
    private String url;

    @Column(name = "bearer", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
    private String bearer;

    @Convert(converter = AttributeEncryptor.class)
    @Column(name = "usuario", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
    private String usuario;

    @Convert(converter = AttributeEncryptor.class)
    @Column(name = "senha", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
    private String senha;

    @Column(name = "idioma", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
    private String idioma;

}
