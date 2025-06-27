/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.model;

import com.br.sobieskiproducoes.geradormaterias.empresa.model.ConfiguracoesEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:27:09
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_categoria")
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_configuracao", insertable = true, updatable = true, nullable = false, unique = false)
    private ConfiguracoesEntity configuracao;

    @Column(name = "id_joomla", nullable = false, insertable = true, updatable = true, unique = true)
    private Long idJoomla;

    @Column(name = "id_wordpress", nullable = false, insertable = true, updatable = true, unique = true)
    private Long idWordPress;

    @Column(name = "titulo", nullable = false, insertable = true, updatable = true, unique = false, length = 1000)
    private String titulo;

    @Column(name = "alias", nullable = false, insertable = true, updatable = true, unique = false, length = 1000)
    private String apelido;

    @Column(name = "usar_prompts", nullable = true, insertable = true, updatable = true, unique = false, columnDefinition = " tinyint(1) DEFAULT 1 ")
    private Boolean usarEmPrompts = Boolean.TRUE;

    @ManyToOne
    @JoinColumn(name = "id_pai")
    private CategoriaEntity pai;

}
