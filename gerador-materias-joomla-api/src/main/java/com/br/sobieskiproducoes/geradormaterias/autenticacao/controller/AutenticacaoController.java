/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.autenticacao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.autenticacao.dto.LoginDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.TokenSessionDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 01:09:16
 * @version 1.0.26 de jun. de 2025
 */
@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("autenticacao")
public class AutenticacaoController {

    private final UsuarioService service;

    @PostMapping("/login")
    public ResponseEntity<TokenSessionDTO> login(@RequestBody @Valid final LoginDTO login) {

        return ResponseEntity.ok(service.login(login.username(), login.password()));
    }

}
