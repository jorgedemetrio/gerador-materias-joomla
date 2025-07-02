/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.service;

import static java.util.Objects.isNull;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.br.sobieskiproducoes.geradormaterias.empresa.convert.ConfiguracoesConvert;
import com.br.sobieskiproducoes.geradormaterias.empresa.convert.EmpresaConvert;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.EmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.ConfiguracoesRepository;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.EmpresaRepository;
import com.br.sobieskiproducoes.geradormaterias.exception.DadosInvalidosException;
import com.br.sobieskiproducoes.geradormaterias.exception.NaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;
import com.br.sobieskiproducoes.geradormaterias.utils.SpringSecurityAuditorAwareComponent;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 27 de jun. de 2025 02:04:49
 * @version 1.0.27 de jun. de 2025
 */
@Log
@RequiredArgsConstructor
@Service
public class EmpresaService {
    private final EmpresaRepository repository;
    private final ConfiguracoesRepository configuracoesRepository;
    private final EmpresaConvert convert;
    private final ConfiguracoesConvert configuracoesConvert;
    private final SpringSecurityAuditorAwareComponent usuarioLogadoAwareComponent;

    public EmpresaDTO getEmpresa() throws Exception {

        final Optional<EmpresaEntity> empresa = repository.buscarPrincipalPorUsuario(usuarioLogadoAwareComponent.getUsuarioLogado().getUsuario());

        if (!empresa.isPresent()) {
            throw new NaoEncontradoException("Não encontrado!");
        }

        return convert.to(empresa.get());
    }

    public EmpresaDTO salvar(@Validated final EmpresaDTO empresa) throws Exception {

        final UsuarioEntity usuario = usuarioLogadoAwareComponent.getUsuarioLogado();

        final Optional<EmpresaEntity> empresaEntityOpt = repository.buscarPrincipalPorUsuario(usuario.getUsuario());
        EmpresaEntity empresaEntity;

        if (isNull(empresa) || isNull(empresa.getNome()) || empresa.getNome().isBlank() || empresa.getNome().length() < 5) {
            throw new DadosInvalidosException("Nome da empresa é obrigatório deve ter no minimo 5 caracteres.");
        }

        // Alteração
        if (empresaEntityOpt.isPresent()) {
            empresaEntity = empresaEntityOpt.get();
            convert.alteracao(empresa, empresaEntity);

            if (!empresaEntity.getUsuarios().stream().filter(n -> usuario.getId().equals(n.getUsuario())).findFirst().isPresent()) {
                empresaEntity.getUsuarios().add(usuario);
            }

        } else {// Nova empresa

            empresaEntity = convert.novo(empresa, usuario);

        }

        empresaEntity = repository.save(empresaEntity);

        if (isNull(empresaEntity.getConfiguracao())) {
            empresaEntity.setConfiguracao(configuracoesRepository.save(configuracoesConvert.novoConfiguracaoEntity(empresaEntity)));
        }

        return convert.to(empresaEntity);

    }

}
