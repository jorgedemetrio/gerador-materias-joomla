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
@Mapper(componentModel = "spring", imports = { LocalDateTime.class, StatusEnum.class, Objects.class })
public interface ConfiguracoesConvert {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    @Mapping(target = "materias", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "alterador", ignore = true)
    @Mapping(target = "criador", ignore = true)
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

    @Mapping(target = "entity.joomla.url", source = "source.joomla.url")
    @Mapping(target = "entity.joomla.bearer", source = "source.joomla.bearer")
    @Mapping(target = "entity.joomla.idioma", source = "source.joomla.idioma")
    @Mapping(target = "entity.joomla.alterador", source = "usuario")
    @Mapping(target = "entity.joomla.alterado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "entity.joomla.ipCriador", source = "source.ipAlterador")
    @Mapping(target = "entity.joomla.ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "entity.joomla.statusDado", expression = "java(StatusEnum.NOVO)")

    @Mapping(target = "entity.wordpress.url", source = "source.wordpress.url")
    @Mapping(target = "entity.wordpress.idioma", source = "source.wordpress.idioma")
    @Mapping(target = "entity.wordpress.bearer", source = "source.wordpress.bearer")
    @Mapping(target = "entity.wordpress.senha", source = "source.wordpress.senha")
    @Mapping(target = "entity.wordpress.usuario", source = "source.wordpress.usuario")
    @Mapping(target = "entity.wordpress.alterador", source = "usuario")
    @Mapping(target = "entity.wordpress.alterado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "entity.wordpress.ipCriador", source = "source.ipAlterador")
    @Mapping(target = "entity.wordpress.ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "entity.wordpress.statusDado", expression = "java(StatusEnum.NOVO)")

    @Mapping(target = "entity.chatgpt.alterador", source = "usuario")
    @Mapping(target = "entity.chatgpt.alterado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "entity.chatgpt.ipCriador", source = "source.ipAlterador")
    @Mapping(target = "entity.chatgpt.ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "entity.chatgpt.statusDado", expression = "java(StatusEnum.NOVO)")
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
