/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

/**
 *
 * @author JorgeDemetrioPC
 * @since 27 de jun. de 2025 02:26:29
 * @version 1.0.27 de jun. de 2025
 */
public class NaoEncontradoException extends HttpStatusCodeException {

    /**
     *
     */
    private static final long serialVersionUID = -168152520077944572L;

    /**
     * @param statusCode
     * @param statusText
     */
    public NaoEncontradoException(final String statusText) {
        super(HttpStatus.NOT_FOUND, statusText);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param statusCode
     */
    public NaoEncontradoException() {
        super(HttpStatus.NOT_FOUND);
    }

}
