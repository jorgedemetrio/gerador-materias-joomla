/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.domain;

import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.domain.AbstractObservabilidadeEntity;
import com.br.sobieskiproducoes.geradormaterias.materia.model.CategoriaEntity;
import com.br.sobieskiproducoes.geradormaterias.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormaterias.materia.model.TagEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "tbl_configuracao")
public class ConfiguracoesEntity extends AbstractObservabilidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "site", insertable = true, updatable = true, nullable = true, unique = false, length = 250)
    private String site;

    @Column(name = "instagram", insertable = true, updatable = true, nullable = true, unique = false, length = 250)
    private String istagram;

    @Column(name = "youtube", insertable = true, updatable = true, nullable = true, unique = false, length = 250)
    private String youtube;

    @OneToOne(mappedBy = "configuracao", cascade = CascadeType.ALL)
    private ChatGPTPromptsEntity chatgptPrompts;

    @OneToOne(mappedBy = "configuracao", cascade = CascadeType.ALL)
    private ChatGPTConfigurationEntity chatgpt;

    @OneToOne(mappedBy = "configuracao", cascade = CascadeType.ALL)
    private JoomlaConfigurationEntity joomla;

    @OneToOne(mappedBy = "configuracao", cascade = CascadeType.ALL)
    private WordPressConfigurationEntity wordpress;

    @OneToOne
    @JoinColumn(name = "id_empresa", insertable = true, updatable = true, nullable = false, unique = false)
    private EmpresaEntity empresa;

    @OneToMany(mappedBy = "configuracao", cascade = CascadeType.ALL)
    private List<MateriaEntity> materias;

    @OneToMany(mappedBy = "configuracao", cascade = CascadeType.ALL)
    private List<TagEntity> tags;

    @OneToMany(mappedBy = "configuracao", cascade = CascadeType.ALL)
    private List<CategoriaEntity> categorias;

}
