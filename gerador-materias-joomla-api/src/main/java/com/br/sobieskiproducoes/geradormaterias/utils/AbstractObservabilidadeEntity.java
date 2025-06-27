/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.utils;

import java.time.LocalDateTime;

import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author JorgeDemetrioPC
 * @since 27 de jun. de 2025 01:49:41
 * @version 1.0.27 de jun. de 2025
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractObservabilidadeEntity {
    @ManyToOne
    @JoinColumn(name = "id_usuario_criador", nullable = false, insertable = true, updatable = false, unique = false)
    private UsuarioEntity criador;

    @ManyToOne
    @JoinColumn(name = "id_usuario_alterado", nullable = true, insertable = true, updatable = true, unique = false)
    private UsuarioEntity alterador;

    @Column(name = "criado", nullable = false, insertable = true, updatable = false, unique = false)
    private LocalDateTime criado = LocalDateTime.now();

    @Column(name = "alterado", nullable = true, insertable = true, updatable = true, unique = false)
    private LocalDateTime alterado;

    @NotBlank
    @Column(name = "ip_criador", nullable = false, insertable = true, updatable = true, unique = false, length = 100)
    private String ipCriador;

    @Column(name = "ip_proxy_criador", nullable = true, insertable = true, updatable = true, unique = false, length = 100)
    private String ipProxyCriador;

    @Column(name = "ip_criador", nullable = true, insertable = true, updatable = true, unique = false, length = 100)
    private String ipAlterador;

    @Column(name = "ip_proxy_criador", nullable = true, insertable = true, updatable = true, unique = false, length = 100)
    private String ipProxyAlterador;

    @Enumerated(EnumType.STRING)
    @Column(name = "ip_proxy_criador", nullable = true, insertable = true, updatable = true, unique = false)
    private StatusEnum status = StatusEnum.NOVO;
}
