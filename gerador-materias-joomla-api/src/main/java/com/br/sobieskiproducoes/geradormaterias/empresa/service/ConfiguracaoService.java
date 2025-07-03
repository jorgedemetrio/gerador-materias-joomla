/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.service;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.sobieskiproducoes.geradormaterias.autenticacao.componente.UsuarioAutenticadoComponente;
import com.br.sobieskiproducoes.geradormaterias.empresa.convert.ConfiguracoesConvert;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.ConfiguracoesEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ConfiguracoesDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.ChatGPTConfigurationRepository;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.ConfiguracoesRepository;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.EmpresaRepository;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.JoomlaConfigurationRepository;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.WordPressConfigurationRepository;
import com.br.sobieskiproducoes.geradormaterias.exception.DadosInvalidosException;
import com.br.sobieskiproducoes.geradormaterias.exception.NaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;

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
    private final EmpresaRepository empresaRepository;

    private final ChatGPTConfigurationRepository chatGPTConfigurationRepository;
    private final JoomlaConfigurationRepository joomlaConfigurationRepository;
    private final WordPressConfigurationRepository wordPressConfigurationRepository;

    private final UsuarioAutenticadoComponente usuarioLogadoAwareComponent;

    @Transactional
    public ConfiguracoesDTO salvar(final ConfiguracoesDTO configuracao) {

        final UsuarioEntity usuario = usuarioLogadoAwareComponent.getUsuarioLogado();

        final Optional<EmpresaEntity> empresa = empresaRepository.buscarPrincipalPorUsuario(usuario.getUsuario());
        if (!empresa.isPresent()) {
            throw new DadosInvalidosException("Empresa não encotrada, favorgravar uma dados de empresa primeiro.");
        }
        if (isNull(configuracao.getWordpress()) && isNull(configuracao.getJoomla())) {
            throw new DadosInvalidosException("Entre com a configuração do site, seja WordPress ou Joomla.");
        }

        ConfiguracoesEntity configuracoaEntity = null;

        final List<ConfiguracoesEntity> configuracaos = repository.buscaConfiguracoes(usuario.getUsuario(), PageRequest.of(0, 1));

        if (isNull(configuracaos) || configuracaos.isEmpty()) {
            configuracoaEntity = convert.novo(configuracao);
        } else {
            configuracoaEntity = configuracaos.get(0);
            convert.atualizar(configuracao, configuracoaEntity);
            if (isNull(configuracao.getJoomla().getBearer()) || configuracao.getJoomla().getBearer().isBlank()) {
                configuracoaEntity.setJoomla(null);
                configuracoaEntity.getWordpress().setConfiguracao(configuracoaEntity);
            } else {
                configuracoaEntity.setWordpress(null);
                configuracoaEntity.getJoomla().setConfiguracao(configuracoaEntity);
            }
            configuracoaEntity.getChatgpt().setConfiguracao(configuracoaEntity);
        }

        configuracoaEntity.setEmpresa(empresa.get());

        configuracoaEntity = repository.save(configuracoaEntity);

        configuracoaEntity.setChatgpt(chatGPTConfigurationRepository.save(configuracoaEntity.getChatgpt()));
        if (isNull(configuracao.getJoomla().getBearer()) || configuracao.getJoomla().getBearer().isBlank()) {
            configuracoaEntity.setWordpress(wordPressConfigurationRepository.save(configuracoaEntity.getWordpress()));
        } else {
            configuracoaEntity.setJoomla(joomlaConfigurationRepository.save(configuracoaEntity.getJoomla()));
        }

        configuracoaEntity = repository.save(configuracoaEntity);

        repository.flush();

        return convert.to(configuracoaEntity);
    }

    public ConfiguracoesDTO configuracaoDaEmpresaPrincipal() {
        final List<ConfiguracoesEntity> configuracao = repository.buscaConfiguracoes(usuarioLogadoAwareComponent.getUsuarioLogado().getUsuario(),
                PageRequest.of(0, 1));

        if (isNull(configuracao) || configuracao.isEmpty()) {
            throw new NaoEncontradoException("Configuração não encontrada.");
        }

        return convert.to(configuracao.get(0));
    }

}
