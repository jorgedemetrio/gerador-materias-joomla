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
@Entity
@Table(name = "tbl_chatgpt_configuracao")
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class ChatGPTConfigurationEntity extends AbstractObservabilidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 40)
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_configuracao", insertable = true, updatable = true, nullable = false, unique = false)
    private ConfiguracoesEntity configuracao;

    @Column(name = "id_thrend", nullable = true, insertable = true, updatable = true, unique = false, length = 200)
    private String thread;

    @NotNull
    @NotBlank
    @Convert(converter = AttributeEncryptorConfig.class)
    @Column(name = "bearer", nullable = false, insertable = true, updatable = true, unique = false, length = 250)
    private String bearer;

    @Convert(converter = AttributeEncryptorConfig.class)
    @Column(name = "id_assistente", nullable = true, insertable = true, updatable = true, unique = false, length = 250)
    private String assistente;

    @NotNull
    @NotBlank
    @Convert(converter = AttributeEncryptorConfig.class)
    @Column(name = "id_organization", nullable = false, insertable = true, updatable = true, unique = false, length = 250)
    private String organization;

    @NotNull
    @NotBlank
    @Column(name = "model", nullable = false, insertable = true, updatable = true, unique = false, length = 250)
    private String model;

    @Min(0)
    @Max(1)
    @Column(name = "temperature", nullable = true, insertable = true, updatable = true, unique = false)
    private Double temperature;

    @Column(name = "role_user", nullable = true, insertable = true, updatable = true, unique = false, length = 250)
    private String roleUser;

    @Column(name = "role_system", nullable = true, insertable = true, updatable = true, unique = false, length = 250)
    private String roleSystem;

    @Column(name = "role_assistant", nullable = true, insertable = true, updatable = true, unique = false, length = 250)
    private String roleAssistant;

    @Column(name = "max_tokens", nullable = true, insertable = true, updatable = true, unique = false, length = 250)
    private String maxTokens;

}
