package com.br.sobieskiproducoes.geradormaterias.materia.controller;

import static java.util.Objects.isNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.dto.RetornoBusinessDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.controller.dto.MateriaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.service.MateriaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/materia")
public class MateriaApiController {

    private final MateriaService service;

    @GetMapping(path = { "", "/" }, produces = { MimeTypeUtils.APPLICATION_JSON_VALUE })
    public ResponseEntity<RetornoBusinessDTO<MateriaDTO>> get(@RequestParam(name = "q", required = false) final String busca,
            @RequestParam(name = "p", required = false) final Integer pagina, @RequestParam(name = "s", required = false) final Integer itensPorPagina,
            @RequestParam(name = "o", required = false) final String ordernar, final Authentication authentication) {

        if (isNull(authentication) || isNull(authentication.getPrincipal())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(service.buscarMateria((UserDetails) authentication.getPrincipal(), busca, pagina, itensPorPagina, ordernar));
    }

}
