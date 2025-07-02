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
public class SemPermissaoException extends HttpStatusCodeException {

    /**
     *
     */
    private static final long serialVersionUID = -3895689766694441150L;

    /**
     * @param statusCode
     * @param statusText
     */
    public SemPermissaoException(final String statusText) {
        super(HttpStatus.FORBIDDEN, statusText);
    }

    /**
     * @param statusCode
     */
    public SemPermissaoException() {
        super(HttpStatus.FORBIDDEN);
    }

}
