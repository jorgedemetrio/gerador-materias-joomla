/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.convert;

import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ConfiguracoesDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.ConfiguracoesEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;
import com.br.sobieskiproducoes.geradormaterias.utils.StatusEnum;

/**
 *
 * @author JorgeDemetrioPC
 * @since 28 de jun. de 2025 01:13:23
 * @version 1.0.28 de jun. de 2025
 */
@Mapper(componentModel = "spring", imports = { LocalDateTime.class, StatusEnum.class })
public interface ConfiguracoesConvert {

    ConfiguracoesDTO to(ConfiguracoesEntity sources);

    ConfiguracoesEntity to(ConfiguracoesDTO sources);

    List<ConfiguracoesDTO> toConfiguracoesDTO(List<ConfiguracoesEntity> sources);

    List<ConfiguracoesEntity> toConfiguracoesEntity(List<ConfiguracoesDTO> sources);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alterado", ignore = true)
    @Mapping(target = "criador", source = "usuario")
    @Mapping(target = "empresa", source = "source")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "ipAlterador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyAlterador", source = "source.ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    ConfiguracoesEntity novoConfiguracaoEntity(EmpresaEntity source, UsuarioEntity usuario);
}
