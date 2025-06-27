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
public class DadosInvalidosException extends HttpStatusCodeException {

    /**
     *
     */
    private static final long serialVersionUID = -4349964879630457893L;

    /**
     * @param statusCode
     * @param statusText
     */
    public DadosInvalidosException(final String statusText) {
        super(HttpStatus.NOT_FOUND, statusText);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param statusCode
     */
    public DadosInvalidosException() {
        super(HttpStatus.NOT_FOUND);
    }

}
