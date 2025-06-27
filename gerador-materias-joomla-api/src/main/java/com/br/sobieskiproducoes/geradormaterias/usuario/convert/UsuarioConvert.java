/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.br.sobieskiproducoes.geradormaterias.usuario.dto.GrupoDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.GrupoEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 21:29:34
 * @version 1.0.26 de jun. de 2025
 */
@Mapper(componentModel = "spring")
public interface UsuarioConvert {

    UsuarioDTO to(UsuarioEntity source);

    GrupoDTO to(GrupoEntity source);

    @Mappings({ @Mapping(target = "usuarios", ignore = true), @Mapping(target = "permissoes", ignore = true) })
    GrupoEntity to(GrupoDTO source);
}
