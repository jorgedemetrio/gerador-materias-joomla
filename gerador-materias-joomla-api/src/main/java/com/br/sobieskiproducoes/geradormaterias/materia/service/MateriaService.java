package com.br.sobieskiproducoes.geradormaterias.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormaterias.config.MateriaConstants;
import com.br.sobieskiproducoes.geradormaterias.dto.RetornoBusinessDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosArtigoJoomlaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.controller.dto.MateriaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormaterias.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormaterias.materia.service.convert.MateriaConvert;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Ane Batista
 * @since 04 de Março de 2024 14:19:04
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Service
public class MateriaService {
    private final MateriaRepository repository;
    private final MateriaConvert convert;

    public Boolean apagar(@NotNull final Long id, final UserDetails usuario) {
        final var materiaEntityOpt = repository.findById(id);
        if (!materiaEntityOpt.isPresent()) {
            return Boolean.FALSE;
        }
        repository.delete(materiaEntityOpt.get());
        log.info("Apagado a Matéria {}");
        return Boolean.TRUE;
    }

    public MateriaDTO criar(final AtributosArtigoJoomlaDTO materia, final UserDetails usuario) {
        MateriaEntity materiaEntity = null;
        if (nonNull(materia.getId()) && materia.getId() > 0) {
            final var materiaEntityOpt = repository.findById(materia.getId());
            if (materiaEntityOpt.isPresent()) {
                materiaEntity = materiaEntityOpt.get();
                log.info("Fez merge da Materia id: ".concat(materia.getId().toString()));
                convert.merge(materia, materiaEntity);
            }
            if (isNull(materiaEntity)) {
                log.info("Novo registro");
                materiaEntity = convert.convert(materia);
            }
        }
        return convert.toDTO(repository.save(materiaEntity));
    }

    public RetornoBusinessDTO<MateriaDTO> buscarMateria(final UserDetails usuario, final String titulo, final Integer pagina, final Integer itensPorPagina,
            final String ordenar) {

        final Page<MateriaEntity> itens = repository.buscarMateria(titulo, usuario.getUsername(),
                PageRequest.of(nonNull(pagina) ? pagina : 0,
                        nonNull(itensPorPagina) && itensPorPagina > 0 ? itensPorPagina : MateriaConstants.MAX_INTENS_PER_PAGE,
                        Sort.by(nonNull(ordenar) && !ordenar.isBlank() ? ordenar : "criado")));

        return new RetornoBusinessDTO<>(itens.getTotalElements(), pagina, itens.stream().map(convert::toDTO).toList());
    }

}
