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
import com.br.sobieskiproducoes.geradormaterias.empresa.convert.TermosEmpresaConvert;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.TermosEmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.TermosEmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.EmpresaRepository;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.TermosEmpresaRepository;
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
 * @since 3 de jul. de 2025 23:57:38
 * @version 1.0.3 de jul. de 2025
 */
@Log
@RequiredArgsConstructor
@Service
public class TermosEmpresaService {

    private TermosEmpresaRepository repository;
    private EmpresaRepository empresaRepository;
    private final UsuarioAutenticadoComponente usuarioLogadoAwareComponent;
    private TermosEmpresaConvert convert;

    public List<TermosEmpresaDTO> consultar(final String nome, @NotBlank final String idEmpresa, @NotNull final Integer pagina,
            @NotNull final Integer itensPorPagina, @NotBlank final String campoOrdem) {
        return convert.to(repository.consulta(nome, idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId(),
                PageRequest.of(pagina, itensPorPagina, Sort.by(campoOrdem))));
    }

    @Transactional
    public TermosEmpresaDTO gravar(@Valid @Validated final TermosEmpresaDTO termo, @NotBlank final String idEmpresa) {

        if (!empresaRepository.empresaUsuario(idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId()).isPresent()) {
            throw new DadosInvalidosException("Sem permisso para gravar nesta empresa.");
        }

        return convert.to(repository.save(isNull(termo) ? convert.novo(termo) : convert.to(termo)));
    }

    @Transactional
    public void delete(@NotBlank final String idTermo, @NotBlank final String idEmpresa) {
        final Optional<TermosEmpresaEntity> item = repository.get(idTermo, idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId());
        if (!item.isPresent()) {
            throw new NaoEncontradoException("Termo não encontrado.");
        }
        final TermosEmpresaEntity itemDeletar = item.get();
        itemDeletar.setStatusDado(StatusEnum.REMOVIDO);
        repository.save(itemDeletar);
    }

    public TermosEmpresaDTO item(@NotBlank final String idTermo, @NotBlank final String idEmpresa) {
        final Optional<TermosEmpresaEntity> item = repository.get(idTermo, idEmpresa, usuarioLogadoAwareComponent.getUsuarioLogado().getId());
        if (!item.isPresent()) {
            throw new NaoEncontradoException("Termo não encontrado.");
        }
        return convert.to(item.get());
    }

}
