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
import com.br.sobieskiproducoes.geradormaterias.empresa.convert.AudienciaConvert;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.AudienciaEmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.AudienciaEmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.AudienciasEmpresaRepository;
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
public class AudienciaService {
    private final AudienciasEmpresaRepository repository;
    private final UsuarioAutenticadoComponente usuarioLogadoAwareComponent;
    private final AudienciaConvert convert;
    private EmpresaRepository empresaRepository;

    public List<AudienciaEmpresaDTO> consultar(final String nome, @NotBlank final String idEmpresa, @NotNull final Integer pagina,
            @NotNull final Integer itensPorPagina, @NotBlank final String campoOrdem) {
        return convert.to(repository.consulta(nome, idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId(),
                PageRequest.of(pagina, itensPorPagina, Sort.by(campoOrdem))));
    }

    @Transactional
    public AudienciaEmpresaDTO gravar(@NotNull @Valid @Validated final AudienciaEmpresaDTO empresa, @NotBlank final String idEmpresa) {

        if (!empresaRepository.empresaUsuario(idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId()).isPresent()) {
            throw new DadosInvalidosException("Sem permisso para gravar nesta empresa.");
        }
        return convert.to(repository.save(isNull(empresa) ? convert.novo(empresa) : convert.to(empresa)));
    }

    public AudienciaEmpresaDTO item(@NotBlank final String audienciaId, @NotBlank final String idEmpresa) {
        final Optional<AudienciaEmpresaEntity> item = repository.get(audienciaId, idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId());
        if (!item.isPresent()) {
            throw new NaoEncontradoException("Audiencia não encontrada.");
        }
        return convert.to(item.get());
    }

    @Transactional
    public void delete(@NotBlank final String audienciaId, @NotBlank final String idEmpresa) {
        final Optional<AudienciaEmpresaEntity> item = repository.get(audienciaId, idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId());
        if (!item.isPresent()) {
            throw new NaoEncontradoException("Audiencia não encontrada.");
        }
        final AudienciaEmpresaEntity itemDeletar = item.get();
        itemDeletar.setStatusDado(StatusEnum.REMOVIDO);
        repository.save(itemDeletar);
    }
}
