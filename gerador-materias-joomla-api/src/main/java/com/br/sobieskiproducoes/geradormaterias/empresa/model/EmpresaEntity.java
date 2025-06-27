/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.model;

import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 13 de jun. de 2025 19:43:13
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_empresa")
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "nome", insertable = true, updatable = true, nullable = true, unique = false, length = 250)
    private String nome;

    @Column(name = "principal", insertable = true, updatable = true, nullable = true, unique = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean principal;

    @OneToOne(mappedBy = "empresa", fetch = FetchType.LAZY)
    private ConfiguracoesEntity configuracao;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tbl_usuario_empresa", inverseJoinColumns = { @JoinColumn(name = "id_usuario", table = "tbl_usuario_empresa") }, joinColumns = {
            @JoinColumn(name = "id_empresa", table = "tbl_usuario_empresa") })
    private List<UsuarioEntity> usuarios;

}
