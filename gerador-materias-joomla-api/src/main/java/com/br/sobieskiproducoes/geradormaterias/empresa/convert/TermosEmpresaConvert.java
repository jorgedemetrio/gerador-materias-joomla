/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.TermosEmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.TermosEmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.utils.StatusEnum;

/**
 *
 * @author JorgeDemetrioPC
 * @since 29 de jun. de 2025 14:44:46
 * @version 1.0.29 de jun. de 2025
 */
@Mapper(componentModel = "spring", imports = { StatusEnum.class })
public interface TermosEmpresaConvert {

    @Mapping(target = "empresa", ignore = true)
    TermosEmpresaDTO to(TermosEmpresaEntity source);

    @Mapping(target = "alterador", ignore = true)
    @Mapping(target = "criador", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    TermosEmpresaEntity to(TermosEmpresaDTO source);

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
    TermosEmpresaEntity novo(TermosEmpresaDTO source, EmpresaEntity empresaSource);

    List<TermosEmpresaDTO> to(List<TermosEmpresaEntity> source);

}
