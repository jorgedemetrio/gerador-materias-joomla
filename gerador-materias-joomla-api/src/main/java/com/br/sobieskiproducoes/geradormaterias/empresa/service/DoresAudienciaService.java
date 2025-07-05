/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.service;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.br.sobieskiproducoes.geradormaterias.autenticacao.componente.UsuarioAutenticadoComponente;
import com.br.sobieskiproducoes.geradormaterias.empresa.convert.DoresAudienciaConvert;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.DoresAudienciaEmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.DoresAudienciaEmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.DoresAudienciasEmpresaRepository;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.EmpresaRepository;
import com.br.sobieskiproducoes.geradormaterias.exception.DadosInvalidosException;
import com.br.sobieskiproducoes.geradormaterias.exception.NaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.utils.StatusEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 3 de jul. de 2025 23:57:52
 * @version 1.0.3 de jul. de 2025
 */
@Log
@RequiredArgsConstructor
@Service
public class DoresAudienciaService {
    private final DoresAudienciasEmpresaRepository repository;
    private final UsuarioAutenticadoComponente usuarioLogadoAwareComponent;
    private final DoresAudienciaConvert convert;
    private final EmpresaRepository empresaRepository;

    public List<DoresAudienciaEmpresaDTO> consultar(final String nome, @NotBlank final String idEmpresa, @NotNull final Integer pagina,
            @NotNull final Integer itensPorPagina, @NotBlank final String campoOrdem) {
        return convert.to(repository.consulta(nome, idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId(),
                PageRequest.of(pagina, itensPorPagina, Sort.by(campoOrdem))));
    }

    @Transactional
    public DoresAudienciaEmpresaDTO gravar(@NotNull @Valid @Validated final DoresAudienciaEmpresaDTO dorAudiencia, @NotBlank final String idEmpresa) {

        final Optional<EmpresaEntity> empresa = empresaRepository.empresaUsuario(idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId());

        if (!empresa.isPresent()) {
            throw new DadosInvalidosException("Sem permisso para gravar nesta empresa.");
        }

        if (repository.consultaPorNomeEmpresaUsuario(dorAudiencia.getNome(), idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId()).isPresent()) {
            throw new DadosInvalidosException("Já existe esse item.");
        }
        return convert.to(repository.save(isNull(dorAudiencia) ? convert.novo(dorAudiencia, empresa.get()) : convert.to(dorAudiencia)));
    }

    public DoresAudienciaEmpresaDTO item(@NotBlank final String audienciaId, @NotBlank final String idEmpresa) {
        final Optional<DoresAudienciaEmpresaEntity> item = repository.get(audienciaId, idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId());
        if (!item.isPresent()) {
            throw new NaoEncontradoException("Dor da Audiencia não encontrada.");
        }
        return convert.to(item.get());
    }

    @Transactional
    public void delete(@NotBlank final String audienciaId, @NotBlank final String idEmpresa) {
        final Optional<DoresAudienciaEmpresaEntity> item = repository.get(audienciaId, idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId());
        if (!item.isPresent()) {
            throw new NaoEncontradoException("Dor da Audiencia não encontrada.");
        }
        final DoresAudienciaEmpresaEntity itemDeletar = item.get();
        itemDeletar.setStatusDado(StatusEnum.REMOVIDO);
        repository.save(itemDeletar);
    }
}
