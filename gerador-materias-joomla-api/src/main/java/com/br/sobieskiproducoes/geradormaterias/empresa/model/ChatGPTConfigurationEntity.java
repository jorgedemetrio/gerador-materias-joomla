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
@Table(name = "tbl_chtgpt_configuracao")
@EqualsAndHashCode(of = { "id" })
public class ChatGPTConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_configuracao", insertable = true, updatable = true, nullable = false, unique = false)
    private ConfiguracoesEntity configuracao;

//  @Column(name = "id_thrend", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
//  private String thread;

    @NotNull
    @NotBlank
    @Convert(converter = AttributeEncryptor.class)
    @Column(name = "bearer", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
    private String bearer;

    @Convert(converter = AttributeEncryptor.class)
    @Column(name = "id_assistente", nullable = true, insertable = true, updatable = true, unique = false, length = 2000)
    private String assistente;

    @NotNull
    @NotBlank
    @Convert(converter = AttributeEncryptor.class)
    @Column(name = "id_organization", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
    private String organization;

    @NotNull
    @NotBlank
    @Column(name = "model", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
    private String model;

    @NotNull
    @Min(0)
    @Max(1)
    @Column(name = "temperature", nullable = false, insertable = true, updatable = true, unique = false)
    private Double temperature;

    @NotNull
    @NotBlank
    @Column(name = "role_user", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
    private String roleUser;

    @NotNull
    @NotBlank
    @Column(name = "role_system", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
    private String roleSystem;

    @NotNull
    @NotBlank
    @Column(name = "role_assistant", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
    private String roleAssistant;

    @NotNull
    @NotBlank
    @Column(name = "max_tokens", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
    private String maxTokens;

}
