/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.convert;

import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ChatGPTConfigurationDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ChatGPTPromptsDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ConfiguracoesDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.JoomlaConfigurationDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.WordPressConfigurationDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.ChatGPTConfigurationEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.ChatGPTPromptsEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.ConfiguracoesEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.JoomlaConfigurationEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.WordPressConfigurationEntity;
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

    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    @Mapping(target = "materias", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    ConfiguracoesDTO to(ConfiguracoesEntity sources);

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    ChatGPTPromptsEntity to(ChatGPTPromptsDTO source, UsuarioEntity usuario);

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    ChatGPTConfigurationEntity to(ChatGPTConfigurationDTO source, UsuarioEntity usuario);

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    JoomlaConfigurationEntity to(JoomlaConfigurationDTO source, UsuarioEntity usuario);

    @Mapping(target = "usuario", source = "source.usuario")
    @Mapping(target = "senha", source = "source.senha")
    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    WordPressConfigurationEntity to(WordPressConfigurationDTO source, UsuarioEntity usuario);

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    @Mapping(target = "materias", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "criador", source = "usuario")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    ConfiguracoesEntity novo(ConfiguracoesDTO source, UsuarioEntity usuario);

    @Mapping(target = "entity.id", ignore = true)
    @Mapping(target = "entity.tags", ignore = true)
    @Mapping(target = "entity.categorias", ignore = true)
    @Mapping(target = "entity.materias", ignore = true)
    @Mapping(target = "entity.empresa", ignore = true)
    @Mapping(target = "entity.alterador", source = "usuario")
    @Mapping(target = "entity.alterado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "entity.ipCriador", source = "source.ipAlterador")
    @Mapping(target = "entity.ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "entity.statusDado", expression = "java(StatusEnum.NOVO)")
    void atualizar(ConfiguracoesDTO source, UsuarioEntity usuario, @MappingTarget ConfiguracoesEntity entity);

    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    @Mapping(target = "materias", ignore = true)
    @Mapping(target = "empresa", ignore = true)
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
