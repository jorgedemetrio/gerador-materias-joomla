package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.MateriaConstants;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.ArtigoJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.TagJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoJoomlaSalvarDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosTagJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.TagDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.TagEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.TagRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.MateriaConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.TagConvert;

import jakarta.validation.Valid;
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
    

    public AtributosArtigoJoomlaDTO criar(final AtributosArtigoJoomlaDTO materia) {
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
        List<MateriaEntity> itens = new ArrayList<>();
        GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>>> consulta = null;
        int offset = 0;
        Map<String, Integer> retorno = new HashMap<>();
        MateriaEntity materiaEntity = null;
        MateriaEntity materiaEntityOpt = null;

        // Busca as matérias
        do {
            if (consulta == null) {
                // Primeira página
                consulta = client.getMaterias(0, 20);
            } else {
                // Busca a próxima página
                consulta = client.getMaterias(offset * 20, 20);
            }
            offset++;

            if (consulta != null && consulta.getData() != null && !consulta.getData().isEmpty()) {

                for (GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO> materia : consulta.getData()) {
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
                        } catch (Exception ex) {
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
    
    public Long getBuscarMateria(final Long id) {
    	final Optional<MateriaEntity> materiaEntityOpt = repository.findById(id);
    	if (!materiaEntityOpt.isPresent()) {
    	      return null;
    	    }
    	return convert.convert(materiaEntityOpt.get());
    }

    
}
