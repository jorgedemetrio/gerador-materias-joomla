/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.controller;

import java.util.logging.Level;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.exception.UsuarioExistenteException;
import com.br.sobieskiproducoes.geradormaterias.usuario.service.UsuarioService;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 21:01:11
 * @version 1.0.26 de jun. de 2025
 */
@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioAPIController {

    private final UsuarioService service;

    @PostMapping({ "" })
    public ResponseEntity<UsuarioDTO> salvar(@RequestBody final UsuarioDTO usuario,
            @NotBlank @RequestHeader(name = "X-Tracking-ID", required = true) final String trakingId,
            @NotBlank @RequestHeader(name = "X-Session-ID", required = true) final String sessionId) throws Exception {
        try {
            return ResponseEntity.ok(service.salvar(usuario));
        } catch (final UsuarioExistenteException ex) {
            log.log(Level.SEVERE, String.format("[  ] UsuarioAPIController#get usuario: ", usuario.getUsuario()));
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage())).build();
        }
    }

}
