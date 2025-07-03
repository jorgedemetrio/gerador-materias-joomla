/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.model;

import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.domain.AbstractObservabilidadeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 14 de jun. de 2025 02:22:01
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_grupo_usuario")
public class GrupoEntity extends AbstractObservabilidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 40)
    private String id;

    @Column(name = "nome", nullable = false, insertable = true, updatable = false, unique = true, length = 255)
    private String nome;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "grupos", fetch = FetchType.LAZY)
    private List<UsuarioEntity> usuarios;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "grupos", fetch = FetchType.LAZY)
    private List<PermissaoEntity> permissoes;
}
