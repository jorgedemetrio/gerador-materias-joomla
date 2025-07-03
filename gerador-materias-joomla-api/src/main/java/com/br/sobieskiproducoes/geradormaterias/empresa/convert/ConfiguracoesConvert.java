/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.convert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.ChatGPTConfigurationEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.ChatGPTPromptsEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.ConfiguracoesEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.JoomlaConfigurationEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.WordPressConfigurationEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ChatGPTConfigurationDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ChatGPTPromptsDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ConfiguracoesDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.JoomlaConfigurationDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.WordPressConfigurationDTO;
import com.br.sobieskiproducoes.geradormaterias.utils.StatusEnum;

/**
 *
 * @author JorgeDemetrioPC
 * @since 28 de jun. de 2025 01:13:23
 * @version 1.0.28 de jun. de 2025
 */
@Mapper(componentModel = "spring", imports = { LocalDateTime.class, StatusEnum.class, Objects.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConfiguracoesConvert {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    @Mapping(target = "materias", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "alterador", ignore = true)
    @Mapping(target = "criador", ignore = true)
    @Mapping(target = "chatgptPrompts", ignore = true)
    @Mapping(target = "chatgpt.alterador", ignore = true)
    @Mapping(target = "chatgpt.criador", ignore = true)
    @Mapping(target = "chatgpt.configuracao", ignore = true)
    @Mapping(target = "joomla.alterador", ignore = true)
    @Mapping(target = "joomla.criador", ignore = true)
    @Mapping(target = "joomla.configuracao", ignore = true)
    @Mapping(target = "wordpress.alterador", ignore = true)
    @Mapping(target = "wordpress.criador", ignore = true)
    @Mapping(target = "wordpress.configuracao", ignore = true)
    ConfiguracoesDTO to(ConfiguracoesEntity sources);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    @Mapping(target = "materias", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    ConfiguracoesEntity novo(ConfiguracoesDTO source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    ChatGPTPromptsEntity to(ChatGPTPromptsDTO source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    ChatGPTConfigurationEntity to(ChatGPTConfigurationDTO source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    JoomlaConfigurationEntity to(JoomlaConfigurationDTO source);

    @Mapping(target = "senha", source = "senha")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    WordPressConfigurationEntity to(WordPressConfigurationDTO source);

    @Mapping(target = "entity.id", ignore = true)
    @Mapping(target = "entity.tags", ignore = true)
    @Mapping(target = "entity.categorias", ignore = true)
    @Mapping(target = "entity.materias", ignore = true)
    @Mapping(target = "entity.criado", ignore = true)
    @Mapping(target = "entity.criador", ignore = true)
    @Mapping(target = "entity.empresa", ignore = true)
    @Mapping(target = "entity.alterado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "entity.ipCriador", source = "ipAlterador")
    @Mapping(target = "entity.ipProxyCriador", source = "ipProxyAlterador")
    @Mapping(target = "entity.statusDado", expression = "java(StatusEnum.NOVO)")

    @Mapping(target = "entity.joomla.id", ignore = true)
    @Mapping(target = "entity.joomla.criado", ignore = true)
    @Mapping(target = "entity.joomla.criador", ignore = true)
    @Mapping(target = "entity.joomla.url", source = "source.joomla.url")
    @Mapping(target = "entity.joomla.bearer", source = "source.joomla.bearer")
    @Mapping(target = "entity.joomla.idioma", source = "source.joomla.idioma")
    @Mapping(target = "entity.joomla.alterado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "entity.joomla.ipCriador", source = "source.ipAlterador")
    @Mapping(target = "entity.joomla.ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "entity.joomla.statusDado", expression = "java(StatusEnum.NOVO)")
    // @Mapping(target = "entity.joomla.configuracao", expression = "java(entity)")

    @Mapping(target = "entity.wordpress.id", ignore = true)
    @Mapping(target = "entity.wordpress.criado", ignore = true)
    @Mapping(target = "entity.wordpress.criador", ignore = true)
    @Mapping(target = "entity.wordpress.url", source = "source.wordpress.url")
    @Mapping(target = "entity.wordpress.idioma", source = "source.wordpress.idioma")
    @Mapping(target = "entity.wordpress.bearer", source = "source.wordpress.bearer")
    @Mapping(target = "entity.wordpress.senha", source = "source.wordpress.senha")
    @Mapping(target = "entity.wordpress.alterado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "entity.wordpress.ipCriador", source = "source.ipAlterador")
    @Mapping(target = "entity.wordpress.ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "entity.wordpress.statusDado", expression = "java(StatusEnum.NOVO)")
    @Mapping(target = "entity.wordpress.usuario", source = "source.wordpress.usuario")
    // @Mapping(target = "entity.wordpress.configuracao", expression =
    // "java(entity)")

    @Mapping(target = "entity.chatgpt.id", ignore = true)
    @Mapping(target = "entity.chatgpt.criado", ignore = true)
    @Mapping(target = "entity.chatgpt.criador", ignore = true)
    @Mapping(target = "entity.chatgpt.alterado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "entity.chatgpt.ipCriador", source = "source.ipAlterador")
    @Mapping(target = "entity.chatgpt.ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "entity.chatgpt.statusDado", expression = "java(StatusEnum.NOVO)")
    @Mapping(target = "entity.chatgpt.bearer", source = "source.chatgpt.bearer")
    @Mapping(target = "entity.chatgpt.assistente", source = "source.chatgpt.assistente")
    @Mapping(target = "entity.chatgpt.organization", source = "source.chatgpt.organization")
    @Mapping(target = "entity.chatgpt.model", source = "source.chatgpt.model")

    // @Mapping(target = "entity.chatgpt.configuracao", expression = "java(entity)")
    void atualizar(ConfiguracoesDTO source, @MappingTarget ConfiguracoesEntity entity);

    List<ConfiguracoesDTO> toConfiguracoesDTO(List<ConfiguracoesEntity> sources);

    List<ConfiguracoesEntity> toConfiguracoesEntity(List<ConfiguracoesDTO> sources);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alterado", ignore = true)
    @Mapping(target = "empresa", source = "source")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "ipAlterador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyAlterador", source = "source.ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    ConfiguracoesEntity novoConfiguracaoEntity(EmpresaEntity source);
}
