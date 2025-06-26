/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.model;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_chatgpt_perguntas")
public class ChatGPTPerguntasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_chatgpt_configuracao", insertable = true, updatable = true, nullable = false, unique = false)
    private ChatGPTPromptsEntity configuracao;

    @NotNull
    @NotBlank
    @Column(name = "id_organization", nullable = false, insertable = true, updatable = true, unique = false, length = 2000)
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
