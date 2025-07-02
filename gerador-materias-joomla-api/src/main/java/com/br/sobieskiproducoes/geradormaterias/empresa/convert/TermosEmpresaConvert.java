/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.convert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.TermosEmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.TermosEmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.utils.StatusEnum;

/**
 *
 * @author JorgeDemetrioPC
 * @since 29 de jun. de 2025 14:44:46
 * @version 1.0.29 de jun. de 2025
 */
@Mapper(componentModel = "spring", imports = { LocalDateTime.class, StatusEnum.class, ArrayList.class, Arrays.class })
public interface TermosEmpresaConvert {

    TermosEmpresaDTO to(TermosEmpresaEntity source);

    @Mapping(target = "alterado", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    TermosEmpresaEntity novo(TermosEmpresaDTO source);

    List<TermosEmpresaDTO> to(List<TermosEmpresaEntity> source);

}
