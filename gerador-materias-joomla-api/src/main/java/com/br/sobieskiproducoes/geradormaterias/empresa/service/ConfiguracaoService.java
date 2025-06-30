/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.service;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormaterias.empresa.convert.ConfiguracoesConvert;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ConfiguracoesDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.ConfiguracoesEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.ConfiguracoesRepository;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.EmpresaRepository;
import com.br.sobieskiproducoes.geradormaterias.exception.DadosInvalidosException;
import com.br.sobieskiproducoes.geradormaterias.exception.NaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.exception.UsuarioNaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSistemaDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 27 de jun. de 2025 23:49:02
 * @version 1.0.27 de jun. de 2025
 */
@Log
@RequiredArgsConstructor
@Service
public class ConfiguracaoService {

    private final ConfiguracoesRepository repository;
    private final ConfiguracoesConvert convert;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;

    public ConfiguracoesDTO salvar(final ConfiguracoesDTO configuracao, final UsuarioSistemaDTO usuarioLogado) {

        final Optional<EmpresaEntity> empresa = empresaRepository.buscarPrincipalPorUsuario(usuarioLogado.getUsername());
        if (!empresa.isPresent()) {
            throw new DadosInvalidosException("Empresa não encotrada, favorgravar uma dados de empresa primeiro.");
        }
        if (isNull(configuracao.getWordpress()) && isNull(configuracao.getJoomla())) {
            throw new DadosInvalidosException("Entre com a configuração do site, seja WordPress ou Joomla.");
        }

        final Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByUsuarioIgnoreCase(usuarioLogado.getUsername());

        if (!usuarioOpt.isPresent()) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado.");
        }

        final UsuarioEntity usuario = usuarioOpt.get();

        ConfiguracoesEntity configuracoaEntity = null;

        final List<ConfiguracoesEntity> configuracaos = repository.buscaConfiguracoes(usuarioLogado.getUsername(), PageRequest.of(0, 1));

        if (isNull(configuracaos) || configuracaos.isEmpty()) {
        }
        configuracoaEntity = convert.novo(configuracao, usuario);
        configuracoaEntity.setEmpresa(empresa.get());

        return convert.to(repository.save(configuracoaEntity));
    }

    public ConfiguracoesDTO configuracaoDaEmpresaPrincipal(final UsuarioSistemaDTO usuarioLogado) {
        final List<ConfiguracoesEntity> configuracao = repository.buscaConfiguracoes(usuarioLogado.getUsername(), PageRequest.of(0, 1));

        if (isNull(configuracao) || configuracao.isEmpty()) {
            throw new NaoEncontradoException("Configuração não encontrada.");
        }

        return convert.to(configuracao.get(0));
    }

}
