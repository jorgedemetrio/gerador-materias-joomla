/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 28 de jun. de 2025 00:04:24
 * @version 1.0.28 de jun. de 2025
 */
@Log
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<Object> handleTokenExpiredException(final TokenExpiredException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Sessão Expirada.")).build();
    }

    @ExceptionHandler(value = JWTDecodeException.class)
    public ResponseEntity<Object> handleJWTDecodeException(final JWTDecodeException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getLocalizedMessage())).build();
    }

    @ExceptionHandler(value = { AuthenticationCredentialsNotFoundException.class, BadCredentialsException.class })
    public ResponseEntity<Object> handleNotFoundException(final Exception ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos.")).build();
    }

    @ExceptionHandler(value = { AccountExpiredException.class, CredentialsExpiredException.class })
    public ResponseEntity<Object> handletExpiredException(final Exception ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuário expirado.")).build();
    }

    @ExceptionHandler(value = { DisabledException.class })
    public ResponseEntity<Object> handletDisabledException(final Exception ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuário desabilitado.")).build();
    }

    @ExceptionHandler(value = { LockedException.class })
    public ResponseEntity<Object> handletLockedException(final Exception ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuário bloqueado")).build();
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handletGenericoException(final Exception ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage())).build();
    }

}
