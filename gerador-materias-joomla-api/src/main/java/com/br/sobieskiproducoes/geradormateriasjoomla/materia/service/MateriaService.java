package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.MateriaConstants;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.ArtigoJoomlaClient;
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
    private final ArtigoJoomlaClient client;
    private final ConfiguracoesProperties properties;


    public Boolean apagar(@NotNull final Long id) {
        final Optional<MateriaEntity> materiaEntityOpt = repository.findById(id);
        if (!materiaEntityOpt.isPresent()) {
            return Boolean.FALSE;
        }
        repository.delete(materiaEntityOpt.get());
        log.info("Apagado a Matéria {}");
        return Boolean.TRUE;
    }


    public Map<String, Integer> atualizar() {
        final List<MateriaEntity> itens = new ArrayList<>();
        GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>>> consulta = null;
        int offset = 0;
        final Map<String, Integer> retorno = new HashMap<>();
        MateriaEntity materiaEntity = null;
        Optional<MateriaEntity> materiaEntityOpt = null;

        // Busca as matérias
        do {
            if (consulta == null) {
                // Primeira página
                consulta = client.get();
            } else {
                // Busca a próxima página
                consulta = client.get(String.valueOf(offset * 20), "20");
            }
            offset++;

            if (consulta != null && consulta.getData() != null && !consulta.getData().isEmpty()) {

                for (final GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO> materia : consulta.getData()) {
                    // Salva apenas a matéria no banco que pertence ao idioma padrão do site
                    if (materia.getAttributes() == null
                            || materia.getAttributes().getLanguage().equals(properties.getJoomla().getIdioma())
                            || MateriaConstants.SEM_IDIOMA.equals(materia.getAttributes().getLanguage())) {
                        materiaEntityOpt = repository.findByIdJoomla(materia.getAttributes().getId());
                        if (materiaEntityOpt.isPresent()) {
                            materiaEntity = materiaEntityOpt.get();
                            convert.merge(materia.getAttributes(), materiaEntity);
                        } else {
                            materiaEntity = convert.convertJoomla(materia.getAttributes());
                        }

                        try {
                            repository.save(materiaEntity);
                        } catch (final Exception ex) {
                            log.log(Level.SEVERE, ex.getLocalizedMessage(), ex.getCause());
                        }
                        itens.add(materiaEntity);
                    }
                }

            }

        } while (consulta != null && consulta.getLinks() != null && consulta.getLinks().getNext() != null
                && consulta.getData() != null && !consulta.getLinks().getNext().isBlank());

        retorno.put("total", itens.size());

        log.info("Gravando lista de %d de matérias.".formatted(itens.size()));
        retorno.put("processados", itens.size());
        return retorno;
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
