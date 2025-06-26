/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.cargaemmassa.controller;

import java.util.List;
import java.util.logging.Level;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormaterias.cargaemmassa.service.CargaMateriaEmMassaService;
import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.controller.dto.MapaPerguntaDTO;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 07:22:48
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/carga-materia-massa")
public class CargaMateriaEmMassaController {

    private final CargaMateriaEmMassaService service;

    @PostMapping(path = "processar", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<List<MapaPerguntaDTO>> processar(@NotNull @Validated @RequestBody final RequisicaoCaragMassaDTO request) {

        log.info("Inicio de processamento de em massa com dados: ".concat(request.toString()));

        try {
            return null;// TODO: ARRUMAR AQUI ResponseEntity.ok(service.processar(request));
        } catch (final Exception ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            return ResponseEntity.internalServerError().build();
        }

    }

}
