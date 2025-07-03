/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.convert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.ConfiguracoesEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ConfiguracoesDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.EmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;
import com.br.sobieskiproducoes.geradormaterias.utils.StatusEnum;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 19:07:33
 * @version 1.0.26 de jun. de 2025
 */
@Mapper(componentModel = "spring", imports = { LocalDateTime.class, StatusEnum.class, ArrayList.class, Arrays.class })
public interface EmpresaConvert {

    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "criador", ignore = true)
    @Mapping(target = "alterador", ignore = true)
    @Mapping(target = "configuracao.materias", ignore = true)
    @Mapping(target = "configuracao.tags", ignore = true)
    @Mapping(target = "configuracao.categorias", ignore = true)
    @Mapping(target = "configuracao.criador", ignore = true)
    @Mapping(target = "configuracao.alterador", ignore = true)
    @Mapping(target = "configuracao.empresa", ignore = true)
    @Mapping(target = "configuracao.joomla.criador", ignore = true)
    @Mapping(target = "configuracao.joomla.alterador", ignore = true)
    @Mapping(target = "configuracao.joomla.configuracao", ignore = true)
    @Mapping(target = "configuracao.chatgpt.criador", ignore = true)
    @Mapping(target = "configuracao.chatgpt.alterador", ignore = true)
    @Mapping(target = "configuracao.chatgpt.configuracao", ignore = true)
    @Mapping(target = "configuracao.wordpress.criador", ignore = true)
    @Mapping(target = "configuracao.wordpress.alterador", ignore = true)
    @Mapping(target = "configuracao.wordpress.configuracao", ignore = true)

    @Mapping(target = "termos.criador", ignore = true)
    @Mapping(target = "termos.alterador", ignore = true)
    @Mapping(target = "termos.empresa", ignore = true)

    @Mapping(target = "audiencias.criador", ignore = true)
    @Mapping(target = "audiencias.alterador", ignore = true)
    @Mapping(target = "audiencias.empresa", ignore = true)
    EmpresaDTO to(EmpresaEntity source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "configuracao", ignore = true)
    @Mapping(target = "termos", ignore = true)
    @Mapping(target = "audiencias", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "target.configuracao", ignore = true)
    void to(EmpresaDTO source, @MappingTarget EmpresaEntity target);

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "configuracao", ignore = true)
    @Mapping(target = "termos", ignore = true)
    @Mapping(target = "audiencias", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "configuracao", ignore = true)
    @Mapping(target = "statusDado", ignore = true)
    @Mapping(target = "target.alterado", ignore = true)
    @Mapping(target = "target.alterador", ignore = true)
    @Mapping(target = "ipCriador", ignore = true)
    @Mapping(target = "ipProxyCriador", ignore = true)
    @Mapping(target = "nome", source = "source.nome")
    @Mapping(target = "alterado", expression = "java(LocalDateTime.now())")
    void alteracao(EmpresaDTO source, @MappingTarget EmpresaEntity target);

    ConfiguracoesDTO to(ConfiguracoesEntity source);

    EmpresaEntity to(EmpresaDTO source);

    @Mapping(target = "alterado", ignore = true)
    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "principal", constant = "true")
    @Mapping(target = "nome", source = "source.nome")
    @Mapping(target = "criado", expression = "java(LocalDateTime.now())")
    @Mapping(target = "ipCriador", source = "source.ipAlterador")
    @Mapping(target = "ipProxyCriador", source = "source.ipProxyAlterador")
    @Mapping(target = "statusDado", expression = "java(StatusEnum.NOVO)")
    @Mapping(target = "usuarios", expression = "java(Arrays.asList(usuario))")
    EmpresaEntity novo(EmpresaDTO source, UsuarioEntity usuario);

    ConfiguracoesEntity to(ConfiguracoesDTO source);
}
