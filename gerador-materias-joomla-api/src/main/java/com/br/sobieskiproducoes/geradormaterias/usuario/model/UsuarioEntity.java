/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.model;

import java.time.LocalDateTime;
import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.validation.CPF;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@ToString(exclude = { "usuario" })
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

    @NotBlank
    @CPF
    @Column(name = "cpf", nullable = false, insertable = true, updatable = false, unique = false, length = 14)
    private String cpf;

    @Column(name = "senha", nullable = false, insertable = true, updatable = false, unique = false, length = 255)
    private String senha;

    @Email
    @Column(name = "email", nullable = true, insertable = true, updatable = true, unique = true, length = 255)
    private String email;

    @Column(name = "habilitado", nullable = false, insertable = true, updatable = true, unique = false)
    private Boolean habilitado;

    @Column(name = "expira", nullable = true, insertable = true, updatable = true, unique = false)
    private LocalDateTime expira;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    private List<EmpresaEntity> empresas;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = true, insertable = true, updatable = true, unique = false, length = 1)
    private NivelUsuarioEnum nivel;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tbl_usuario_grupo", inverseJoinColumns = { @JoinColumn(name = "id_grupo", table = "tbl_usuario_grupo") }, joinColumns = {
            @JoinColumn(name = "id_usuario", table = "tbl_usuario_grupo") })
    private List<GrupoEntity> grupos;
}
