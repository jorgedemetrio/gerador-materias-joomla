/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.model;

import java.time.LocalDateTime;
import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.empresa.model.EmpresaEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
 * @since 14 de jun. de 2025 02:00:54
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "tbl_usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "nome", nullable = false, insertable = true, updatable = false, unique = false, length = 255)
    private String nome;

    @Column(name = "usuario", nullable = false, insertable = true, updatable = false, unique = true, length = 255)
    private String usuario;

    @Column(name = "senha", nullable = false, insertable = true, updatable = false, unique = false, length = 255)
    private String senha;

    @Email
    @Column(name = "email", nullable = true, insertable = true, updatable = true, unique = false, length = 255)
    private String email;

    @Column(name = "habilitado", nullable = false, insertable = true, updatable = true, unique = false)
    private Boolean habilitado;

    @Column(name = "expira", nullable = true, insertable = true, updatable = true, unique = false)
    private LocalDateTime expira;

    @OneToMany(mappedBy = "usuario")
    private List<EmpresaEntity> empresas;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = true, insertable = true, updatable = true, unique = false, length = 1)
    private NivelUsuarioEnum nivel;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_usuario_grupo", inverseJoinColumns = { @JoinColumn(name = "id_grupo", table = "tbl_usuario_grupo") }, joinColumns = {
            @JoinColumn(name = "id_usuario", table = "tbl_usuario_grupo") })
    private List<GrupoEntity> grupos;
}
