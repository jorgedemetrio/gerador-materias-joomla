/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
 * @since 14 de jun. de 2025 02:45:52
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "username")
public class UsuarioSistemaDTO implements UserDetails {
    /**
     *
     */
    private static final long serialVersionUID = 7246234048956347098L;
    private String id;
    private String idEmpresaPricipal;
    private String name;
    private String username;
    private String password;
    private Boolean enabled;
    private LocalDateTime expira;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return Objects.isNull(expira) || LocalDateTime.now().isBefore(expira);
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Objects.isNull(expira) || LocalDateTime.now().isBefore(expira);
    }

    @Override
    public boolean isEnabled() {
        return Objects.nonNull(enabled) && enabled.booleanValue();
    }
}
