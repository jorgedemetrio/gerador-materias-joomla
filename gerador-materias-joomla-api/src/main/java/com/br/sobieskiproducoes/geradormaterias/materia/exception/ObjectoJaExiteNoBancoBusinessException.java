/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.exception;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 15:33:25
 * @version 1.0.0
 */
public class ObjectoJaExiteNoBancoBusinessException extends BusinessException {

  /**
   *
   */
  private static final long serialVersionUID = 6062108465898686245L;

  public ObjectoJaExiteNoBancoBusinessException() {
  }

  public ObjectoJaExiteNoBancoBusinessException(final String message) {
    super(message);
  }

  public ObjectoJaExiteNoBancoBusinessException(final String m, final Throwable t) {
    super(m, t);
  }
  public ObjectoJaExiteNoBancoBusinessException(final Throwable t) {
    super(t);
  }
}
