/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ConfiguracoesDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.EmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.ConfiguracoesEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.EmpresaEntity;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 19:07:33
 * @version 1.0.26 de jun. de 2025
 */
@Mapper(componentModel = "spring")
public interface EmpresaConvert {

    EmpresaDTO to(EmpresaEntity source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "configuracao", ignore = true)
    @Mapping(target = "termos", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    void to(EmpresaDTO source, @MappingTarget EmpresaEntity target);

    ConfiguracoesDTO to(ConfiguracoesEntity source);

    EmpresaEntity to(EmpresaDTO source);

    ConfiguracoesEntity to(ConfiguracoesDTO source);
}
