/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.DoresAudienciaEmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.DoresAudienciaEmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.utils.StatusEnum;

/**
 *
 * @author JorgeDemetrioPC
 * @since 29 de jun. de 2025 14:44:46
 * @version 1.0.29 de jun. de 2025
 */
@Mapper(componentModel = "spring", imports = { StatusEnum.class })
public interface DoresAudienciaConvert {

    @Mapping(target = "empresa", ignore = true)
    DoresAudienciaEmpresaDTO to(DoresAudienciaEmpresaEntity source);

    @Mapping(target = "alterador", ignore = true)
    @Mapping(target = "criador", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    DoresAudienciaEmpresaEntity to(DoresAudienciaEmpresaDTO source);

    @Mapping(target = "alterador", ignore = true)
    @Mapping(target = "criador", ignore = true)
    @Mapping(target = "criado", ignore = true)
    @Mapping(target = "alterado", ignore = true)
    @Mapping(target = "empresa", source = "empresaSource")
    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "nome", source = "source.nome")
    @Mapping(target = "ipCriador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "ipAlterador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyAlterador", source = "source.ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    DoresAudienciaEmpresaEntity novo(DoresAudienciaEmpresaDTO source, EmpresaEntity empresaSource);

    List<DoresAudienciaEmpresaDTO> to(List<DoresAudienciaEmpresaEntity> source);

}
