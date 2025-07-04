/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.AudienciaEmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.AudienciaEmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.utils.StatusEnum;

/**
 *
 * @author JorgeDemetrioPC
 * @since 29 de jun. de 2025 14:44:46
 * @version 1.0.29 de jun. de 2025
 */
@Mapper(componentModel = "spring", imports = { StatusEnum.class })
public interface AudienciaConvert {

    @Mapping(target = "empresa", ignore = true)
    AudienciaEmpresaDTO to(AudienciaEmpresaEntity source);

    @Mapping(target = "alterador", ignore = true)
    @Mapping(target = "criador", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    AudienciaEmpresaEntity to(AudienciaEmpresaDTO source);

    @Mapping(target = "alterador", ignore = true)
    @Mapping(target = "criador", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "ipCriador", source = "ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    AudienciaEmpresaEntity novo(AudienciaEmpresaDTO source);

    List<AudienciaEmpresaDTO> to(List<AudienciaEmpresaEntity> source);

}
