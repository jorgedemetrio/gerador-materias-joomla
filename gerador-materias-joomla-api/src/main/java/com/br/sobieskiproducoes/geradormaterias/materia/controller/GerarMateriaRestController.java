/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.controller;

import java.util.List;
import java.util.logging.Level;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosArtigoSalvoJoomlaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.joomla.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.joomla.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.controller.dto.PublicarDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.service.GerarMateriaPorMataService;
import com.br.sobieskiproducoes.geradormaterias.materia.service.MateriaJoomlaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/gerar-materia")
public class GerarMateriaRestController {

    private final GerarMateriaPorMataService gerarMateriaService;
    private final MateriaJoomlaService service;

    @PostMapping(path = "{id}/publicar", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>>> publicar(@PathVariable("id") final Long id,
            @RequestBody final PublicarDTO dto) {
        log.info("Gerando materia sobre %d ".formatted(id));
        try {
            return null;// TODO: ARRUMAR AQUI
                        // ResponseEntity.status(HttpStatus.CREATED).body(service.publicarMateriaJoomla(id));
//    } catch (final ObjectoJaExiteNoBancoBusinessException e) {
//      log.log(Level.SEVERE, "Objecto que tentou salvar já existe", e);
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (final Exception e) {
            log.log(Level.SEVERE, "Erro interno", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping(path = "sugerir", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<PropostaMateriaDTO>> sugerirMateria(@RequestBody final SugerirMateriaDTO request) throws Exception {
        log.info("Gerando materia sobre %s ".formatted(request.getTema()));
        return null;// TODO: ARRUMAR AQUI
                    // ResponseEntity.status(HttpStatus.CREATED).body(gerarMateriaService.gerarSugestaoMateria(request));
    }

}
