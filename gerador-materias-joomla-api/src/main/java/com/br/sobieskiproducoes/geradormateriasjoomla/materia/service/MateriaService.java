package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.MateriaConvert;

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


    public Boolean apagar(@NotNull final Long id) {
        final Optional<MateriaEntity> materiaEntityOpt = repository.findById(id);
        if (!materiaEntityOpt.isPresent()) {
            return Boolean.FALSE;
        }
        repository.delete(materiaEntityOpt.get());
        log.info("Apagado a Matéria {}");
        return Boolean.TRUE;
    }



    public PropostaMateriaDTO criar(final AtributosArtigoJoomlaDTO materia) {
    	MateriaEntity materiaEntity = null;
    	if(nonNull(materia.getId()) && materia.getId() > 0) {
    	  final Optional<MateriaEntity> materiaEntityOpt = repository.findById(materia.getId());
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
        return convert.convert(repository.save(materiaEntity));
    }

    public PropostaMateriaDTO getBuscarMateria(final Long id) {
    	final Optional<MateriaEntity> materiaEntityOpt = repository.findById(id);
    	if (!materiaEntityOpt.isPresent()) {
    	      return null;
    	    }
    	return convert.convert(materiaEntityOpt.get());
    }
    
    

}
