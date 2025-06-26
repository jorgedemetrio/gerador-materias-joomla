/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.mapaperguntas.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.controller.dto.RequisitaPerguntasDTO;
import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.service.GerarMapaPerguntasService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 12:43:03
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/mapaperguntas")
public class MapaPerguntaController {

    private final GerarMapaPerguntasService gerarService;

    @PostMapping(path = "gerar", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<List<MapaPerguntaDTO>> gerar(@NotNull @Validated @RequestBody final RequisitaPerguntasDTO request) throws Exception {
        log.info("Gerando perguntas para o site.");
        return null;// TODO: ARRUMAR AQUI ResponseEntity.ok(gerarService.gerarMapa(request));
    }
}
