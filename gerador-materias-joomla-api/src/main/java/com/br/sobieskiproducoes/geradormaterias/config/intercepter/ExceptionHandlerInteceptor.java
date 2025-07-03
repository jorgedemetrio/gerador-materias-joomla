/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config.intercepter;

import java.util.logging.Level;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.br.sobieskiproducoes.geradormaterias.exception.DadosInvalidosException;
import com.br.sobieskiproducoes.geradormaterias.exception.NaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.exception.SemPermissaoException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 28 de jun. de 2025 00:04:24
 * @version 1.0.28 de jun. de 2025
 */
@Log
@RestControllerAdvice
public class ExceptionHandlerInteceptor {

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<Object> handle(final TokenExpiredException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Sessão Expirada.")).build();
    }

    @ExceptionHandler(value = JWTDecodeException.class)
    public ResponseEntity<Object> handle(final JWTDecodeException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getLocalizedMessage())).build();
    }

    @ExceptionHandler(value = SemPermissaoException.class)
    public ResponseEntity<Object> handle(final SemPermissaoException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getLocalizedMessage())).build();
    }

    @ExceptionHandler(value = { AuthenticationCredentialsNotFoundException.class, BadCredentialsException.class })
    public ResponseEntity<Object> handle(final AuthenticationCredentialsNotFoundException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos.")).build();
    }

    @ExceptionHandler(value = { AccountExpiredException.class, CredentialsExpiredException.class })
    public ResponseEntity<Object> handlet(final AccountExpiredException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuário expirado.")).build();
    }

    @ExceptionHandler(value = { DisabledException.class })
    public ResponseEntity<Object> handlet(final DisabledException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuário desabilitado.")).build();
    }

    @ExceptionHandler(value = { LockedException.class })
    public ResponseEntity<Object> handlet(final LockedException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuário bloqueado")).build();
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handlet(final Exception ex) {
        log.log(Level.SEVERE, ex.getLocalizedMessage(), ex.getCause());
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage())).build();
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<Object> handlet(final ConstraintViolationException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage())).build();
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handlet(final MethodArgumentNotValidException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage())).build();
    }

    @ExceptionHandler(value = { NaoEncontradoException.class })
    public ResponseEntity<Object> handlet(final NaoEncontradoException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage())).build();
    }

    @ExceptionHandler(value = { DadosInvalidosException.class })
    public ResponseEntity<Object> handlet(final DadosInvalidosException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage())).build();
    }

}
