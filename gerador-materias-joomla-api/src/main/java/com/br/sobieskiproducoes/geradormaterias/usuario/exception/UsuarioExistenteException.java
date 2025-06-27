/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 22:14:49
 * @version 1.0.26 de jun. de 2025
 */
public class UsuarioExistenteException extends HttpStatusCodeException {

    /**
     * @param statusCode
     * @param statusText
     */
    public UsuarioExistenteException(final String statusText) {
        super(HttpStatus.BAD_REQUEST, statusText);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param statusCode
     */
    public UsuarioExistenteException() {
        super(HttpStatus.BAD_REQUEST);
    }

    /**
     *
     */
    private static final long serialVersionUID = -2422519743544553906L;

}
