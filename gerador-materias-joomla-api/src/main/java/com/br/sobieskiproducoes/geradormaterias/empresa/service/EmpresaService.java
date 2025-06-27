/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.service;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormaterias.empresa.convert.EmpresaConvert;
import com.br.sobieskiproducoes.geradormaterias.empresa.dto.EmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.empresa.repository.EmpresaRepository;
import com.br.sobieskiproducoes.geradormaterias.exception.DadosInvalidosException;
import com.br.sobieskiproducoes.geradormaterias.exception.NaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.exception.UsuarioNaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSistemaDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.repository.UsuarioRepository;

import jakarta.validation.Valid;
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
    private final UsuarioRepository usuarioRepository;

    private final EmpresaConvert convert;

    public EmpresaDTO getEmpresa(final UsuarioSistemaDTO usuarioLogado) throws Exception {

        final Optional<EmpresaEntity> empresa = repository.buscarPrincipalPorUsuario(usuarioLogado.getUsername());

        if (!empresa.isPresent()) {
            throw new NaoEncontradoException("Não encontrado!");
        }

        return convert.to(empresa.get());
    }

    public EmpresaDTO salvar(@Valid final EmpresaDTO empresa, final UsuarioSistemaDTO usuarioLogado) throws Exception {
        final Optional<EmpresaEntity> empresaEntityOpt = repository.buscarPrincipalPorUsuario(usuarioLogado.getUsername());
        EmpresaEntity empresaEntity;

        final Optional<UsuarioEntity> usuario = usuarioRepository.findByUsuarioIgnoreCase(usuarioLogado.getUsername());

        if (usuario.isPresent()) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado.");
        }

        if (isNull(empresa) || isNull(empresa.getNome()) || empresa.getNome().isBlank() || empresa.getNome().length() < 5) {
            throw new DadosInvalidosException("Nome da empresa é obrigatório deve ter no minimo 5 caracteres.");
        }

        if (empresaEntityOpt.isPresent()) {
            empresaEntity = empresaEntityOpt.get();
            convert.to(empresa, empresaEntity);
            empresaEntity.setAlterador(usuario.get());
            empresaEntity.setAlterado(LocalDateTime.now());

        } else {
            empresaEntity = convert.to(empresa);
            empresaEntity.setCriador(usuario.get());
            empresaEntity.setCriado(LocalDateTime.now());
        }

        // TODO: por enquanto só permitirá uma empresa por usuário.
        empresaEntity.setPrincipal(true);

        return convert.to(repository.save(empresaEntity));

    }
}
