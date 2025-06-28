/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormaterias.empresa.convert.ConfiguracoesConvert;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ConfiguracoesDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.ConfiguracoesRepository;

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

    public ConfiguracoesDTO salvar(final ConfiguracoesDTO configuracao) {
        return convert.to(repository.save(convert.to(configuracao)));
    }

    public List<ConfiguracoesDTO> lista(final String username) {
        return convert.toConfiguracoesDTO(repository.buscaConfiguracoes(username));
    }

}
