/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 21:29:34
 * @version 1.0.26 de jun. de 2025
 */
@Getter
@RequiredArgsConstructor
@Mapper(componentModel = "spring")
public abstract class UsuarioSenhaConvert {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "senha", expression = "java(passwordEncoder.encode(source.getSenha()))")
    @Mapping(target = "id", ignore = true)
    public abstract UsuarioEntity novo(UsuarioDTO source);

    @Mappings({ @Mapping(target = "senha", ignore = true) })
    public abstract UsuarioDTO to(UsuarioEntity source);

}
